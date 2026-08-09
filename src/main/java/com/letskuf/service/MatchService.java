package com.letskuf.service;

import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.*;
import com.letskuf.repository.MatchEventRepository;
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
    private final MatchEventRepository matchEventRepository;

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

        int matchId = matchDTO.getMatchId();

        // 경기 수정
        matchRepository.updateMatch(matchDTO);

        // 기존 유니폼 삭제
        matchRepository.deleteMatchUniform(matchId);

        // 유니폼 재등록
        if (matchDTO.getUniformList() != null) {
            for (MatchUniformDTO uniform : matchDTO.getUniformList()) {

                uniform.setMatchId(matchId);
                matchRepository.saveMatchUniform(uniform);
            }
        }
    }

    /** 경기 삭제 **/
    @Transactional(rollbackFor = Exception.class)
    public void deleteMatch(int matchId) throws Exception {

        // 기존 경기 정보 존재 확인
        MatchDTO existingMatch = matchRepository.selectMatchById(matchId);
        if (existingMatch == null) {
            throw new IllegalArgumentException("존재하지 않는 경기 정보 입니다.: " + matchId);
        }

        // 경기 삭제
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

    /** 경기 상세 조회 **/
    public Map<String, Object> retrieveMatchById(int matchId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 경기 조회
        MatchDTO match = matchRepository.selectMatchById(matchId);

        // 유니폼 조회
        List<MatchUniformDTO> matchUniform = matchRepository.selectMatchUniformById(matchId);

        // 라인업 등록 상태 확인
        boolean lineupCompleted = matchRepository.isLineupCompleted(matchId);

        // 타임라인 리스트
        List<MatchEventDTO> timeline = null;

        // 경기 종료 상태일 때만 타임라인 조회
        if (matchStatusType.FINISHED.equals(match.getStatus())) {
            timeline = matchEventRepository.selectMatchTimeline(matchId);

            MatchEventDTO matchScore = matchEventRepository.selectMatchScore(matchId);

            if (matchScore != null) {
                match.setHomeScore(matchScore.getHomeScore());
                match.setAwayScore(matchScore.getAwayScore());
            }
        }

        map.put("match", match);
        map.put("matchUniform", matchUniform);
        map.put("lineupCompleted", lineupCompleted);
        map.put("timeline", timeline);

        return map;
    }

    /** 경기 상태 변경 **/
    @Transactional(rollbackFor = Exception.class)
    public void updateMatchStatus(MatchDTO matchDTO) throws Exception {
        matchRepository.updateMatchStatus(matchDTO);
    }

    /** 경기 결과 조회 **/
    public Map<String, Object> retrieveMatchResult(MatchSearchDTO matchSearchDTO) throws Exception {

        Map<String, Object> result = new HashMap<>();

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(matchSearchDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        matchSearchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        matchSearchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<MatchDTO> resultList = matchRepository.selectMatchResultList(matchSearchDTO);

        int totalCount = matchRepository.selectMatchResultCount(matchSearchDTO);

        paginationInfo.setTotalRecordCount(totalCount);

        result.put("resultList", resultList);
        result.put("paginationInfo", paginationInfo);

        return result;
    }
}
