package com.letskuf.service;

import com.letskuf.dto.MatchDTO;
import com.letskuf.dto.MatchLineupDTO;
import com.letskuf.dto.MatchLineupSaveDTO;
import com.letskuf.dto.PlayerDTO;
import com.letskuf.repository.MatchLineupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchLineupService {

    /* 사용할 서비스 주입 */
    private final MatchLineupRepository matchLineupRepository;

    /** 라인업 조회 **/
    public Map<String, Object> retrieveMatchLineupInfo(int matchId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        MatchDTO match = matchLineupRepository.selectMatchList(matchId);
        List<PlayerDTO> homePlayers = matchLineupRepository.selectMatchHomePlayer(match.getHomeTeamId());
        List<PlayerDTO> awayPlayers = matchLineupRepository.selectMatchAwayPlayer(match.getAwayTeamId());

        List<MatchLineupDTO> homeLineupList = matchLineupRepository.selectMatchLineup(matchId, match.getHomeTeamId());

        List<MatchLineupDTO> awayLineupList = matchLineupRepository.selectMatchLineup(matchId, match.getAwayTeamId());

        Map<String, Integer> homeLineup = new HashMap<>();
        List<Integer> homeSubs = new ArrayList<>();

        for (MatchLineupDTO player : homeLineupList) {

            if (player.getIsStarting() == 1) {
                homeLineup.put(player.getPosition(), player.getPlayerId());
            } else {
                homeSubs.add(player.getPlayerId());
            }
        }

        Map<String, Integer> awayLineup = new HashMap<>();
        List<Integer> awaySubs = new ArrayList<>();

        for (MatchLineupDTO player : awayLineupList) {

            if (player.getIsStarting() == 1) {
                awayLineup.put(player.getPosition(), player.getPlayerId());
            } else {
                awaySubs.add(player.getPlayerId());
            }
        }

        map.put("match", match);

        map.put("homePlayers", homePlayers);
        map.put("awayPlayers", awayPlayers);

        map.put("homeFormation", match.getHomeFormation());
        map.put("awayFormation", match.getAwayFormation());

        map.put("homeLineup", homeLineup);
        map.put("awayLineup", awayLineup);

        map.put("homeSubs", homeSubs);
        map.put("awaySubs", awaySubs);

        return map;
    }

    /** 라인업 등록 **/
    @Transactional(rollbackFor = Exception.class)
    public void registerMatchLineup(MatchLineupSaveDTO matchLineupSaveDTO) throws Exception {

        // 1. 포메이션 저장
        matchLineupRepository.updateMatchFormation(
                matchLineupSaveDTO.getMatchId(),
                matchLineupSaveDTO.getHomeFormation(),
                matchLineupSaveDTO.getAwayFormation()
        );

        // 경기 정보 조회
        MatchDTO match = matchLineupRepository.selectMatchList(matchLineupSaveDTO.getMatchId());

        // 2. 기존 라인업 삭제
        matchLineupRepository.deleteMatchLineup(matchLineupSaveDTO.getMatchId());

        // 3. HOME 라인업 저장
        for (MatchLineupDTO player : matchLineupSaveDTO.getHomePlayers()) {

            player.setMatchId(matchLineupSaveDTO.getMatchId());
            player.setTeamId(match.getHomeTeamId());

            matchLineupRepository.saveMatchLineup(player);
        }

        // 4. AWAY 라인업 저장
        for (MatchLineupDTO player : matchLineupSaveDTO.getAwayPlayers()) {

            player.setMatchId(matchLineupSaveDTO.getMatchId());
            player.setTeamId(match.getAwayTeamId());

            matchLineupRepository.saveMatchLineup(player);
        }
    }

}
