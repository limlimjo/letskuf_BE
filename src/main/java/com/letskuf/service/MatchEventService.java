package com.letskuf.service;

import com.letskuf.dto.*;
import com.letskuf.repository.MatchEventRepository;
import com.letskuf.repository.MatchLineupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchEventService {

    /* 사용할 서비스 주입 */
    private final MatchLineupRepository matchLineupRepository;
    private final MatchEventRepository matchEventRepository;

    /** 실시간 경기 정보 조회 **/
    public Map<String, Object> retrieveMatchLiveInfo(int matchId) throws Exception {

        Map<String, Object> map = new HashMap<>();
        MatchDTO match = matchLineupRepository.selectMatchList(matchId);

        List<MatchEventDTO> homeLineupPlayers = matchEventRepository.selectLineupPlayers(matchId, match.getHomeTeamId());
        List<MatchEventDTO> awayLineupPlayers = matchEventRepository.selectLineupPlayers(matchId, match.getAwayTeamId());

        List<MatchEventDTO> homeSubPlayers = matchEventRepository.selectSubPlayers(matchId, match.getHomeTeamId());
        List<MatchEventDTO> awaySubPlayers = matchEventRepository.selectSubPlayers(matchId, match.getAwayTeamId());

        map.put("match", match);
        map.put("homeLineupPlayers", homeLineupPlayers);
        map.put("awayLineupPlayers", awayLineupPlayers);
        map.put("homeSubPlayers", homeSubPlayers);
        map.put("awaySubPlayers", awaySubPlayers);

        return map;
    }

    /** 이벤트 등록 **/
    public void registerMatchEvent(MatchEventSaveDTO matchEventSaveDTO) throws Exception {
        matchEventRepository.saveMatchEvent(matchEventSaveDTO);
    }

    /** 이벤트 수정 **/
    public void deleteMatchEvent(int eventId) throws Exception {
        matchEventRepository.deleteMatchEvent(eventId);
    }

    /** 경기 상태 수정 **/
    @Transactional
    public void changeMatchStatus(MatchStatusDTO matchStatusDTO) throws Exception {

        // 1. 상태 변경
        matchEventRepository.updateMatchStatus(matchStatusDTO.getMatchId(), matchStatusDTO.getStatus());

        // 2. 상태에 따른 이벤트 기록
        MatchEventSaveDTO event = new MatchEventSaveDTO();

        event.setMatchId(matchStatusDTO.getMatchId());

        switch (matchStatusDTO.getStatus()) {

            case "FIRST_HALF":
                event.setEventType(EventType.MATCH_START);
                break;

            case "HALF_TIME":
                event.setEventType(EventType.FIRST_HALF_END);
                break;

            case "SECOND_HALF":
                event.setEventType(EventType.SECOND_HALF_START);
                break;

            case "FINISHED":
                event.setEventType(EventType.MATCH_END);
                break;

            default:
                return;
        }

        event.setMinute(matchStatusDTO.getMinute());
        event.setExtraMinute(matchStatusDTO.getExtraMinute());

        matchEventRepository.saveMatchEvent(event);
    }

    /** 타임라인 조회 **/
    public Map<String, Object> retrieveMatchTimeline(int matchId) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        List<MatchEventDTO> list = matchEventRepository.selectMatchTimeline(matchId);

        map.put("resultList", list);

        return map;
    }

}
