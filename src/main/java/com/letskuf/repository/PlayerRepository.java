package com.letskuf.repository;

import com.letskuf.dto.CoachDTO;
import com.letskuf.dto.PlayerDTO;
import com.letskuf.dto.PlayerFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlayerRepository {
    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /* 선수 등록 */
    public PlayerDTO save(PlayerDTO playerDTO) {
        sql.insert("Player.playerSave", playerDTO);
        return playerDTO;
    }

    /* 선수 수정 */
    public void update(PlayerDTO playerDTO) {
        sql.update("Player.playerUpdate", playerDTO);
    }

    /* 선수 삭제 */
    public void delete(int playerId) {
        sql.delete("Player.playerDelete", playerId);
    }

    /* 선수 사진 조회 */
    public List<PlayerFileDTO> selectPlayerFileListByPlayerId(int playerId) {
        return sql.selectList("Player.playerFileListByPlayerId", playerId);
    }

    /* 선수 사진 저장 */
    public void saveFile(PlayerFileDTO playerFileDTO) {
        sql.insert("Player.playerFileSave", playerFileDTO);
    }

    /* 선수 사진 삭제 */
    public void deleteFile(int fileId) {
        sql.delete("Player.playerFileDelete", fileId);
    }

    /* 선수 전체 조회 */
    public List<PlayerDTO> selectPlayers(PlayerDTO playerDTO) {
        return sql.selectList("Player.playerList", playerDTO);
    }

    /* 선수 전체 건수 조회 */
    public int selectPlayersCnt(PlayerDTO playerDTO) {
        return sql.selectOne("Player.playerListCnt", playerDTO);
    }

    /* 선수 상세 조회(teamId에 따른) */
    public List<PlayerDTO> selectPlayerByTeamId(int teamId) {
        return sql.selectList("Player.playerListByTeamId", teamId);
    }

    /* 선수 상세 조회 */
    public PlayerDTO selectPlayerById(int playerId) {
        return sql.selectOne("Player.playerByPlayerId", playerId);
    }

    /* 선수 상세 조회 (수정용) */
    public PlayerDTO selectUpdatePlayerById(int playerId) {
        return sql.selectOne("Player.playerUpdateListByPlayerId", playerId);
    }

    /* 선수 사진 조회 (수정용) */
    public PlayerFileDTO selectUpdatePlayerFileById(int playerId) {
        return sql.selectOne("Player.playerFileUpdateListByPlayerId", playerId);
    }
}
