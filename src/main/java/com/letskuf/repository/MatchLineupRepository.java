package com.letskuf.repository;

import com.letskuf.dto.MatchDTO;
import com.letskuf.dto.MatchLineupDTO;
import com.letskuf.dto.PlayerDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MatchLineupRepository {

    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /** 경기 정보 조회 **/
    public MatchDTO selectMatchList(int matchId) {
        return sql.selectOne("MatchLineup.matchList", matchId);
    }

    /** HOME 선수 조회 **/
    public List<PlayerDTO> selectMatchHomePlayer(int homeTeamId) {
        return sql.selectList("MatchLineup.matchHomePlayer", homeTeamId);
    }

    /** AWAY 선수 조회 **/
    public List<PlayerDTO> selectMatchAwayPlayer(int awayTeamId) {
        return sql.selectList("MatchLineup.matchAwayPlayer", awayTeamId);
    }

    /** 라인업 조회 **/
    public List<MatchLineupDTO> selectMatchLineup(int matchId, int teamId) {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("matchId", matchId);
        paramMap.put("teamId", teamId);

        return sql.selectList("MatchLineup.matchLineup", paramMap);
    }

    /** 기존 라인업 삭제 **/
    public void deleteMatchLineup(int matchId) {
        sql.delete("MatchLineup.matchLineupDelete", matchId);
    }

    /** 라인업 저장 **/
    public void saveMatchLineup(MatchLineupDTO matchLineupDTO) {
        sql.insert("MatchLineup.matchLineupSave", matchLineupDTO);
    }

    /** 포메이션 저장 **/
    public void updateMatchFormation(int matchId, String homeFormation, String awayFormation) {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("matchId", matchId);
        paramMap.put("homeFormation", homeFormation);
        paramMap.put("awayFormation", awayFormation);

        sql.update("MatchLineup.updateMatchFormation", paramMap);
    }

}
