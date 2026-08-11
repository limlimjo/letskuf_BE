package com.letskuf.service;

import com.letskuf.common.S3Uploader;
import com.letskuf.dto.CoachDTO;
import com.letskuf.dto.PlayerDTO;
import com.letskuf.dto.TeamFileDTO;
import com.letskuf.repository.CoachRepository;
import com.letskuf.repository.PlayerRepository;
import com.letskuf.repository.TeamRepository;
import com.letskuf.dto.TeamDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor
//Lombok으로 스프링에서 DI의 방법 중에 생성자 주입을 임의의 코드 없이 자동으로 설정해주는 어노테이션
//@Autowired를 사용하지 않고 의존성 주입
//@Autowired와 @RequiredArgsConstructor의 차이는 노션에 정리하기
//일단 간략하게 정리하자면 @Autowired는 Bean 주입시마다 달아줘야 하지만,
//@RequiredArgsConstructor는 해당 클래스에 달고 private final로 Bean으르 주입함
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    /* 사용할 서비스 주입 */
    private final TeamRepository teamRepository;
    private final CoachRepository coachRepository;
    private final PlayerRepository playerRepository;
    private final S3Uploader s3Uploader;

    /* 팀 등록 */
    @Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
    public void registerTeam(TeamDTO teamDTO) throws Exception {

        List<MultipartFile> teamFiles = teamDTO.getTeamFile();

        // 파일이 없는 경우
        if (teamFiles == null || teamFiles.isEmpty() || teamFiles.get(0).isEmpty()) {

            teamDTO.setFileAttached(0);
            teamRepository.save(teamDTO);

            log.info("팀 정보 저장(파일x): {}", teamDTO);

            return;
        }

        // 파일이 있는 경우
        teamDTO.setFileAttached(1);

        TeamDTO savedTeam = teamRepository.save(teamDTO);

        MultipartFile teamFile = teamFiles.get(0);

        String storedFileName = null;

        try {
            // S3 업로드
            storedFileName = s3Uploader.uploadFileToS3(teamFile, "team");

            // team_file 저장
            TeamFileDTO teamFileDTO = new TeamFileDTO();

            teamFileDTO.setOriginalFileName(teamFile.getOriginalFilename());
            teamFileDTO.setStoredFileName(storedFileName);
            teamFileDTO.setTeamId(savedTeam.getTeamId());
            teamRepository.saveFile(teamFileDTO);

            log.info("팀 파일 저장 완료: {}", storedFileName);

        } catch (Exception e) {

            // S3 업로드는 성공헀지만 DB 저장이 실패한 경우
            if (storedFileName != null) {
                s3Uploader.deleteS3(storedFileName);
            }

            log.error("팀 등록 실패", e);

            throw e;
        }
    }

    /* 팀 수정 */
    @Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
    public void updateTeam(TeamDTO teamDTO) throws Exception {

        // 기존 팀 정보 존재 확인
        TeamDTO existingTeam = teamRepository.selectTeamById(teamDTO.getTeamId());
        if (existingTeam == null) {
            throw new IllegalArgumentException("존재하지 않는 팀입니다.: " + teamDTO.getTeamId());
        }

        // 파일이 새로 업로드 되었는지 확인
        List<MultipartFile> newFiles = teamDTO.getTeamFile();

        // 새 파일이 있는 경우
        if (newFiles != null && !newFiles.isEmpty() && !newFiles.get(0).isEmpty()) {

            MultipartFile newFile = newFiles.get(0);

            // 기존 파일 조회
            List<TeamFileDTO> existingFiles = teamRepository.selectTeamFileListByTeamId(teamDTO.getTeamId());

            // 새 S3 파일
            String newStoredFileName = null;

            try {
                // S3 업로드
                newStoredFileName = s3Uploader.uploadFileToS3(newFile, "team");

                log.info("새 팀 이미지 S3 업로드 완료: {}", newStoredFileName);

                // team_file 저장
                TeamFileDTO newFileDTO = new TeamFileDTO();

                newFileDTO.setOriginalFileName(newFile.getOriginalFilename());
                newFileDTO.setStoredFileName(newStoredFileName);
                newFileDTO.setTeamId(teamDTO.getTeamId());
                teamRepository.saveFile(newFileDTO);

                // 기존 파일 DB 정보 삭제
                for (TeamFileDTO existingFile : existingFiles) {
                    teamRepository.deleteFile(existingFile.getFileId());
                }

                // 팀 정보 수정
                teamDTO.setFileAttached(1);
                teamRepository.update(teamDTO);

                // 기존 S3 파일 삭제
                for (TeamFileDTO existingFile : existingFiles) {
                    s3Uploader.deleteS3(existingFile.getStoredFileName());
                }

                log.info("팀 이미지 변경 완료: teamId={}", teamDTO.getTeamId());
            } catch (Exception e) {
                // 새 파일이 S3에 올라간 상태에서 DB 작업 등이 실패했다면 새 S3 파일을 삭제함
                if (newStoredFileName != null) {
                    s3Uploader.deleteS3(newStoredFileName);
                }

                log.error("팀 수정 실패: teamId={}", teamDTO.getTeamId(), e);

                throw e;
            }
        } else {
            teamDTO.setFileAttached(existingTeam.getFileAttached());
            teamRepository.update(teamDTO);
        }
    }

    /* 팀 삭제 */
    @Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
    public void deleteTeam(int teamId) throws Exception {

        // 기존 팀 정보 존재 확인
        TeamDTO existingTeam = teamRepository.selectTeamById(teamId);
        if (existingTeam == null) {
            throw new IllegalArgumentException("존재하지 않는 팀입니다.: " + teamId);
        }

        // 파일이 있을 경우 파일도 같이 삭제
        List<TeamFileDTO> existingTeamFile = teamRepository.selectTeamFileListByTeamId(teamId);

        if (existingTeamFile != null && !existingTeamFile.isEmpty()) {
            teamRepository.deleteFile(existingTeamFile.get(0).getFileId());
        }

        teamRepository.delete(teamId);
    }

    /* 팀 전체 조회 */
    public Map<String, Object> retrieveTeams(TeamDTO teamDTO) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        List<TeamDTO> list = teamRepository.selectTeams(teamDTO);
        int cnt = teamRepository.selectTeamsCnt(teamDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /* 팀 목록 조회 */
    public Map<String, Object> retrieveTeamsList(TeamDTO teamDTO) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        List<TeamDTO> list = teamRepository.selectTeamsList(teamDTO);

        map.put("resultList", list);

        return map;
    }

    /* 팀 상세 조회 */
    public Map<String, Object> retrieveTeamById(int teamId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 팀 정보
        TeamDTO team = teamRepository.selectTeamById(teamId);

        // 팀 사진 정보
        TeamFileDTO teamFile = teamRepository.selectUpdateTeamFileById(teamId);

        if (teamFile != null) {
            String fileUrl = s3Uploader.getFileUrl(teamFile.getStoredFileName());
            teamFile.setStoredFileName(fileUrl);
        }

        // 지도자 및 임원 정보
        List<CoachDTO> coach = coachRepository.selectCoachByTeamId(teamId);

        // 선수 정보
        List<PlayerDTO> player = playerRepository.selectPlayerByTeamId(teamId);

        map.put("team", team);
        map.put("teamFile", teamFile);
        map.put("coaches", coach);
        map.put("players", player);

        return map;
    }

    /* 팀 상세 조회 (수정용) */
    public Map<String, Object> retrieveUpdateTeamById(int teamId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 팀 정보
        TeamDTO team = teamRepository.selectUpdateTeamById(teamId);

        // 팀 사진 정보
        TeamFileDTO teamFile = teamRepository.selectUpdateTeamFileById(teamId);

        if (teamFile != null) {
            String fileUrl = s3Uploader.getFileUrl(teamFile.getStoredFileName());
            teamFile.setStoredFileName(fileUrl);
        }

        map.put("team", team);
        map.put("teamFile", teamFile);

        return map;
    }
}
