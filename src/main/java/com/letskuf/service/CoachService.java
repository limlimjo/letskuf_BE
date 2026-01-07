package com.letskuf.service;

import com.letskuf.dto.CoachDTO;
import com.letskuf.dto.CoachFileDTO;
import com.letskuf.dto.PlayerDTO;
import com.letskuf.dto.PlayerFileDTO;
import com.letskuf.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoachService {
    /* 사용할 서비스 주입 */
    private final CoachRepository coachRepository;

    /* 코칭스태프/임원 등록 */
    @Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
    public void registerCoach(CoachDTO coachDTO) throws IOException {
        // 파일이 없을 때
        if (coachDTO.getFile().get(0).isEmpty()) {
            coachDTO.setFileAttached(0);
            coachRepository.save(coachDTO);
            log.info("지도자 정보 저장(파일x): {}", coachDTO);
        }
        // 파일이 있을 때
        else {
            coachDTO.setFileAttached(1);
            CoachDTO savedCoach = coachRepository.save(coachDTO);
            log.info("지도자 정보 저장(파일o): {}", coachDTO);

            for (MultipartFile coachFile: coachDTO.getFile()) {
                String originalFileName = coachFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;

                CoachFileDTO coachFileDTO = new CoachFileDTO();
                coachFileDTO.setOriginalFileName(originalFileName);
                coachFileDTO.setStoredFileName(storedFileName);
                coachFileDTO.setCoachId(savedCoach.getCoachId());
                // 파일 저장용 폴더에 파일 저장 처리
                String savePath = "/Users/selimjo/Desktop/letskuf_pic/coach/";
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

                coachFile.transferTo(new File(storedFilePath));

                // coach_file 테이블에 저장
                coachRepository.saveFile(coachFileDTO);
            }
        }
    }

    /* 코칭스태프/임원 수정 */
    @Transactional(rollbackFor = Exception.class)
    public void updateCoach(CoachDTO coachDTO) throws Exception {

        // 기존 선수 정보 존재 확인
        CoachDTO existingCoach = coachRepository.selectCoachById(coachDTO.getCoachId());
        if (existingCoach == null) {
            throw new IllegalArgumentException("존재하지 않는 선수 입니다.: " + coachDTO.getCoachId());
        }

        // 파일이 새로 업로드 되었는지 확인
        List<MultipartFile> newFiles = coachDTO.getFile();
        String savePath = "/Users/selimjo/Desktop/letskuf_pic/player/";

        // 파일 변경 있을 경우
        if (newFiles != null && !newFiles.isEmpty() && !newFiles.get(0).isEmpty()) {
            // 기존 파일이 있다면 삭제 처리
            List<CoachFileDTO> existingFiles = coachRepository.selectCoachFileListByListByCoachId(coachDTO.getCoachId());

            for (CoachFileDTO file : existingFiles) {
                File storedFile = new File(savePath + file.getStoredFileName());
                if (storedFile.exists()) {
                    storedFile.delete();
                }
                log.info("파일 변경있을 경우 삭제 되어야함");
                coachRepository.deleteFile(file.getFileId());
            }

            // 새 파일 저장
            coachDTO.setFileAttached(1);
            for (MultipartFile coachFile: newFiles) {
                String originalFileName = coachFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                String storedFilePath = savePath + storedFileName;

                // 저장 폴더 없으면 생성
                Path directoryPath = Paths.get(savePath);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                coachFile.transferTo(new File(storedFilePath));

                CoachFileDTO newFileDTO = new CoachFileDTO();
                newFileDTO.setOriginalFileName(originalFileName);
                newFileDTO.setStoredFileName(storedFileName);
                newFileDTO.setCoachId(coachDTO.getCoachId());

                coachRepository.saveFile(newFileDTO);
            }

            // 파일 변경 없을 경우
        } else {
            coachDTO.setFileAttached(existingCoach.getFileAttached());
        }
        // 선수 정보 수정
        coachRepository.update(coachDTO);
    }

    /* 코칭스태프/임원 삭제 */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCoach(int coachId) throws Exception {

        // 기존 선수 정보 존재 확인
        CoachDTO existingCoach = coachRepository.selectCoachById(coachId);
        if (existingCoach == null) {
            throw new IllegalArgumentException("존재하지 않는 선수 입니다.: " + coachId);
        }

        // 파일이 있을 경우 파일도 같이 삭제
        List<CoachFileDTO> existingCoachFile = coachRepository.selectCoachFileListByListByCoachId(coachId);

        if (existingCoachFile != null && !existingCoachFile.isEmpty()) {
            coachRepository.deleteFile(existingCoachFile.get(0).getFileId());
        }

        coachRepository.delete(coachId);
    }

    /* 코칭스태프/임원 전체 조회 */
    public Map<String, Object> retrieveCoaches(CoachDTO coachDTO) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        List<CoachDTO> list = coachRepository.selectCoaches(coachDTO);
        int cnt = coachRepository.selectCoachCnt(coachDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /* 코칭스태프/임원 상세 조회 (수정용) */
    public Map<String, Object> retrieveUpdateCoachById(int coachId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 코칭스태프/임원 정보
        CoachDTO coach = coachRepository.selectUpdateCoachById(coachId);

        // 코칭스태프/임원 사진 정보
        CoachFileDTO coachFile = coachRepository.selectUpdateCoachFileById(coachId);

        map.put("coach", coach);
        map.put("file", coachFile);

        return map;
    }
}
