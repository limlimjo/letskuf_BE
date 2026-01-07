package com.letskuf.service;

import com.letskuf.dto.LeagueDTO;
import com.letskuf.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueService {

    /* 사용할 서비스 주입 */
    private final LeagueRepository leagueRepository;

    /* 리그/대회 등록 */
    @Transactional(rollbackFor = Exception.class)
    public void registerLeague(LeagueDTO leagueDTO) throws Exception {
        leagueRepository.save(leagueDTO);
    }

    /* 리그/대회 수정 */
    @Transactional(rollbackFor = Exception.class)
    public void updateLeague(LeagueDTO leagueDTO) throws Exception {

        // 리그/대회 정보 수정
        leagueRepository.update(leagueDTO);
    }

    /* 리그/대회 삭제 */
    @Transactional(rollbackFor = Exception.class)
    public void deleteLeague(int leagueId) throws Exception {

        // 기존 리그/대회 정보 존재 확인
        LeagueDTO existingLeague = leagueRepository.selectLeagueById(leagueId);
        if (existingLeague == null) {
            throw new IllegalArgumentException("존재하지 않는 리그/대회 정보입니다.: " + leagueId);
        }

        // 리그/대회 정보 삭제
        leagueRepository.delete(leagueId);
    }

    /* 리그/대회 전체 조회 */
    public Map<String, Object> retrieveLeague(LeagueDTO leagueDTO) throws Exception {

        Map<String, Object> map = new HashMap<>();

        List<LeagueDTO> list = leagueRepository.selectLeague(leagueDTO);
        int cnt = leagueRepository.selectLeagueCnt(leagueDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /* 리그/대회 상세 조회 */
    public Map<String, Object> retrieveLeagueById(int leagueId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 리그/대회 정보
        LeagueDTO league = leagueRepository.selectLeagueById(leagueId);

        map.put("league", league);

        return map;
    }
}
