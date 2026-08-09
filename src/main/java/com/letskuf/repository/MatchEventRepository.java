package com.letskuf.repository;

import com.letskuf.dto.MatchEventDTO;
import com.letskuf.dto.MatchEventSaveDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MatchEventRepository {

    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /** 선발 선수 조회 **/
    public List<MatchEventDTO> selectLineupPlayers(int matchId, int teamId) {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("matchId", matchId);
        paramMap.put("teamId", teamId);

        return sql.selectList("MatchEvent.selectLineupPlayers", paramMap);
    }

    /** 교체 선수 조회 **/
    public List<MatchEventDTO> selectSubPlayers(int matchId, int teamId) {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("matchId", matchId);
        paramMap.put("teamId", teamId);

        return sql.selectList("MatchEvent.selectSubPlayers", paramMap);
    }

    /** 이벤트 등록 **/
    public void saveMatchEvent(MatchEventSaveDTO matchEventSaveDTO) {
        sql.insert("MatchEvent.insertMatchEvent", matchEventSaveDTO);
    }

    /** 이벤트 삭제 **/
    public void deleteMatchEvent(int eventId) {
        sql.delete("MatchEvent.deleteMatchEvent", eventId);
    }

    /** 경기 상태 변경 **/
    public void updateMatchStatus(int matchId, String status) {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("matchId", matchId);
        paramMap.put("status", status);

        sql.update("MatchEvent.updateMatchStatus", paramMap);
    }

    /** 타임라인 조회 **/
    public List<MatchEventDTO> selectMatchTimeline(int matchId) {
        return sql.selectList("MatchEvent.selectMatchTimeline", matchId);
    }

    /** 스코어 조회 **/
    public MatchEventDTO selectMatchScore(int matchId) {
        return sql.selectOne("MatchEvent.selectMatchScore", matchId);
    }

}
