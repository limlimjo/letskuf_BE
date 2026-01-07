package com.letskuf.service;

import com.letskuf.dto.PlayerDTO;
import com.letskuf.dto.PlayerFileDTO;
import com.letskuf.repository.PlayerRepository;
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
public class PlayerService {
    /* 사용할 서비스 주입 */
    private final PlayerRepository playerRepository;

    /* 선수 등록 */
    @Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
    public void registerPlayer(PlayerDTO playerDTO) throws IOException {
        // 파일이 없을 때
        if (playerDTO.getFile().get(0).isEmpty()) {
            // fileAttached 값 0으로 설정하고 저장
            playerDTO.setFileAttached(0);
            playerRepository.save(playerDTO);
            log.info("선수 정보 저장(파일x): {}", playerDTO);
        }
        // 파일이 있을 때
        else {
            // fileAttached 값 1로 설정하고 저장
            playerDTO.setFileAttached(1);
            PlayerDTO savedPlayer = playerRepository.save(playerDTO);
            log.info("선수 정보 저장(파일o): {}", playerDTO);
            // 파일만 따로 가져오기
            for (MultipartFile playerFile: playerDTO.getFile()) {
                // 파일 이름 가져오기
                String originalFileName = playerFile.getOriginalFilename();
                // 저장용 이름 만들기
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                // PlayerFileDTO 세팅 (originalFileName, storedFileName, playerId)
                PlayerFileDTO playerFileDTO = new PlayerFileDTO();
                playerFileDTO.setOriginalFileName(originalFileName);
                playerFileDTO.setStoredFileName(storedFileName);
                playerFileDTO.setPlayerId(savedPlayer.getPlayerId());
                // 파일 저장용 폴더에 파일 저장 처리
                String savePath = "/Users/selimjo/Desktop/letskuf_pic/player/";
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

                playerFile.transferTo(new File(storedFilePath));
                // player_file 테이블에 저장
                playerRepository.saveFile(playerFileDTO);
            }
        }
    }

    /* 선수 수정 */
    @Transactional(rollbackFor = Exception.class)
    public void updatePlayer(PlayerDTO playerDTO) throws Exception {

        // 기존 선수 정보 존재 확인
        PlayerDTO existingPlayer = playerRepository.selectPlayerById(playerDTO.getPlayerId());
        if (existingPlayer == null) {
            throw new IllegalArgumentException("존재하지 않는 선수 입니다.: " + playerDTO.getPlayerId());
        }

        // 파일이 새로 업로드 되었는지 확인
        List<MultipartFile> newFiles = playerDTO.getFile();
        String savePath = "/Users/selimjo/Desktop/letskuf_pic/player/";

        // 파일 변경 있을 경우
        if (newFiles != null && !newFiles.isEmpty() && !newFiles.get(0).isEmpty()) {
            // 기존 파일이 있다면 삭제 처리
            List<PlayerFileDTO> existingFiles = playerRepository.selectPlayerFileListByPlayerId(playerDTO.getPlayerId());

            for (PlayerFileDTO file : existingFiles) {
                File storedFile = new File(savePath + file.getStoredFileName());
                if (storedFile.exists()) {
                    storedFile.delete();
                }
                log.info("파일 변경있을 경우 삭제 되어야함");
                playerRepository.deleteFile(file.getFileId());
            }

            // 새 파일 저장
            playerDTO.setFileAttached(1);
            for (MultipartFile playerFile: newFiles) {
                String originalFileName = playerFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                String storedFilePath = savePath + storedFileName;

                // 저장 폴더 없으면 생성
                Path directoryPath = Paths.get(savePath);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                playerFile.transferTo(new File(storedFilePath));

                PlayerFileDTO newFileDTO = new PlayerFileDTO();
                newFileDTO.setOriginalFileName(originalFileName);
                newFileDTO.setStoredFileName(storedFileName);
                newFileDTO.setPlayerId(playerDTO.getPlayerId());

                playerRepository.saveFile(newFileDTO);
            }

        // 파일 변경 없을 경우
        } else {
            playerDTO.setFileAttached(existingPlayer.getFileAttached());
        }
        // 선수 정보 수정
        playerRepository.update(playerDTO);
    }

    /* 선수 삭제 */
    @Transactional(rollbackFor = Exception.class)
    public void deletePlayer(int playerId) throws Exception {

        // 기존 선수 정보 존재 확인
        PlayerDTO existingPlayer = playerRepository.selectPlayerById(playerId);
        if (existingPlayer == null) {
            throw new IllegalArgumentException("존재하지 않는 선수 입니다.: " + playerId);
        }

        // 파일이 있을 경우 파일도 같이 삭3제
        List<PlayerFileDTO> existingPlayerFile = playerRepository.selectPlayerFileListByPlayerId(playerId);

        if (existingPlayerFile != null && !existingPlayerFile.isEmpty()) {
            playerRepository.deleteFile(existingPlayerFile.get(0).getFileId());
        }

        playerRepository.delete(playerId);
    }

    /* 선수 전체 조회 */
    public Map<String, Object> retrievePlayers(PlayerDTO playerDTO) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        List<PlayerDTO> list = playerRepository.selectPlayers(playerDTO);
        int cnt = playerRepository.selectPlayersCnt(playerDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /* 선수 상세 조회 (수정용) */
    public Map<String, Object> retrieveUpdatePlayerById(int playerId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 선수 정보
        PlayerDTO player = playerRepository.selectUpdatePlayerById(playerId);

        // 선수 사진 정보
        PlayerFileDTO playerFile = playerRepository.selectUpdatePlayerFileById(playerId);

        map.put("player", player);
        map.put("file", playerFile);

        return map;
    }
}
