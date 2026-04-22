package com.letskuf.service;

import com.letskuf.dto.LeagueDTO;
import com.letskuf.dto.MatchDTO;
import com.letskuf.dto.MatchUniformDTO;
import com.letskuf.repository.MatchRepository;
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
public class MatchService {

    /* 사용할 서비스 주입 */
    private final MatchRepository matchRepository;

    /** 경기 등록 **/
    @Transactional(rollbackFor = Exception.class)
    public void registerMatch(MatchDTO matchDTO) throws Exception {

        // TODO: 경기 일시 등록할 때 경기 일시가 league_tournament의 start_date와 end_date 사이에 속하는지 체크

        // 경기 저장 (matchId 자동 세팅됨)
        matchRepository.saveMatch(matchDTO);

        int matchId = matchDTO.getMatchId();

        // 유니폼 저장
        if (matchDTO.getUniformList() != null) {
            for (MatchUniformDTO uniform : matchDTO.getUniformList()) {

                uniform.setMatchId(matchId);
                matchRepository.saveMatchUniform(uniform);
            }
        }
    }

    /** 경기 수정 **/
    @Transactional(rollbackFor = Exception.class)
    public void updateMatch(MatchDTO matchDTO) throws Exception {

        // TODO: 경기 일시 수정할 때 경기 일시가 league_tournament의 start_date와 end_date 사이에 속하는지 체크

        // 경기 수정
        matchRepository.updateMatch(matchDTO);
    }

    /** 경기 삭제 **/
    @Transactional(rollbackFor = Exception.class)
    public void deleteMatch(int matchId) throws Exception {

        // 기존 경기 정보 존재 확인
        MatchDTO existingMatch = matchRepository.selectMatchById(matchId);
        if (existingMatch == null) {
            throw new IllegalArgumentException("존재하지 않는 경기 정보 입니다.: " + matchId);
        }

        matchRepository.deleteMatch(matchId);
    }

    /** 경기 전체 조회 **/
    public Map<String, Object> retrieveMatches(MatchDTO matchDTO) throws Exception {

        Map<String, Object> map = new HashMap<>();

        List<MatchDTO> list = matchRepository.selectMatches(matchDTO);
        int cnt = matchRepository.selectMatchCnt(matchDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }
}
