package com.letskuf.service;

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

    /* 팀 등록 */
    @Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
    public void registerTeam(TeamDTO teamDTO) throws Exception {
        // 파일 첨부가 필수가 아니므로, 파일이 없을 때와 파일이 있을 때로 나눔
        // 파일이 없을 때
        if (teamDTO.getTeamFile().get(0).isEmpty()) {
            // fileAttached 값 0으로 설정하고 저장
            teamDTO.setFileAttached(0);
            teamRepository.save(teamDTO);
            log.info("팀 정보 저장(파일x): {}", teamDTO);
        }
        // 파일이 있을 때
        else {
            // fileAttached 값 1로 설정하고 저장
            teamDTO.setFileAttached(1);
            TeamDTO savedTeam = teamRepository.save(teamDTO);
            log.info("팀 정보 저장(파일o): {}", teamDTO);
            // 파일만 따로 가져오기
            for (MultipartFile teamFile: teamDTO.getTeamFile()) {
                // 파일 이름 가져오기
                String originalFileName = teamFile.getOriginalFilename();
                // 저장용 이름 만들기
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                // TeamFileDTO 세팅 (originalFileName, storedFileName, teamId)
                TeamFileDTO teamFileDTO = new TeamFileDTO();
                teamFileDTO.setOriginalFileName(originalFileName);
                teamFileDTO.setStoredFileName(storedFileName);
                teamFileDTO.setTeamId(savedTeam.getTeamId());
                // 파일 저장용 폴더에 파일 저장 처리
                String savePath = "/Users/selimjo/Desktop/letskuf_pic/team/";
                String storedFilePath = savePath + storedFileName;

                // 파일 저장용 폴더 없는 경우 폴더 생성
                Path directoryPath = Paths.get(savePath);
                if (!Files.exists(directoryPath)) {
                    try {
                        Files.createDirectories(directoryPath);
                        log.info("폴더 생성 완료: " + savePath);
                    } catch (IOException e) {
                        log.error("파일 저장 폴더 생성 실패: " + savePath, e);
                        throw new IOException("파일 저장 폴더 생성에 실패하였습니다.", e);
                    }
                }

                teamFile.transferTo(new File(storedFilePath));
                // team_file 테이블에 저장
                teamRepository.saveFile(teamFileDTO);
            }
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
        String savePath = "/Users/selimjo/Desktop/letskuf_pic/team/";

        // 파일 변경 있을 경우
        if (newFiles != null && !newFiles.isEmpty() && !newFiles.get(0).isEmpty()) {
            // 기존 파일이 있다면 삭제 처리
            List<TeamFileDTO> existingFiles = teamRepository.selectTeamFileListByTeamId(teamDTO.getTeamId());

            for (TeamFileDTO file : existingFiles) {
                File storedFile = new File(savePath + file.getStoredFileName());
                if (storedFile.exists()) {
                    storedFile.delete();
                }
                log.info("파일 변경있을 경우 삭제 되어야함");
                teamRepository.deleteFile(file.getFileId());
            }

            // 새 파일 저장
            teamDTO.setFileAttached(1);
            for (MultipartFile teamFile: newFiles) {
                String originalFileName = teamFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                String storedFilePath = savePath + storedFileName;

                // 저장 폴더 없으면 생성
                Path directoryPath = Paths.get(savePath);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                teamFile.transferTo(new File(storedFilePath));

                TeamFileDTO newFileDTO = new TeamFileDTO();
                newFileDTO.setOriginalFileName(originalFileName);
                newFileDTO.setStoredFileName(storedFileName);
                newFileDTO.setTeamId(teamDTO.getTeamId());

                teamRepository.saveFile(newFileDTO);
            }

        // 파일 변경 없을 경우
        } else {
            teamDTO.setFileAttached(existingTeam.getFileAttached());
        }
        // 팀 정보 수정
        teamRepository.update(teamDTO);
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

        // 지도자 및 임원 정보
        List<CoachDTO> coach = coachRepository.selectCoachByTeamId(teamId);

        // 선수 정보
        List<PlayerDTO> player = playerRepository.selectPlayerByTeamId(teamId);

        map.put("team", team);
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

        map.put("team", team);
        map.put("teamFile", teamFile);

        return map;
    }
}
