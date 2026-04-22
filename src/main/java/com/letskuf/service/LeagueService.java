package com.letskuf.service;

import com.letskuf.dto.LeagueDTO;
import com.letskuf.dto.LeagueVenueDTO;
import com.letskuf.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

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

    /* 리그/대회 목록 조회 */
    public Map<String, Object> retrieveLeagueList(LeagueDTO leagueDTO) throws Exception {

        Map<String, Object> map = new HashMap<>();

        List<LeagueDTO> list = leagueRepository.selectLeagueList(leagueDTO);

        map.put("resultList", list);

        return map;
    }

    /* 리그/대회 상세 조회 */
    public Map<String, Object> retrieveLeagueById(int leagueId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 리그/대회 정보
        LeagueDTO league = leagueRepository.selectLeagueById(leagueId);
        // 경기장 정보
        List<LeagueVenueDTO> venue = leagueRepository.selectLeagueVenueList(leagueId);
        int cnt = leagueRepository.selectLeagueVenueCnt(leagueId);

        map.put("league", league);
        map.put("venue", venue);
        map.put("venueCnt", Integer.toString(cnt));

        return map;
    }

    /* 리그/대회 경기장 추가 */
    @Transactional(rollbackFor = Exception.class)
    public void registerLeagueVenue(LeagueVenueDTO leagueVenueDTO) throws Exception {
        try {
            leagueRepository.saveLeagueVenue(leagueVenueDTO);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("이미 등록된 경기장입니다.");
        }
    }

    /* 리그/대회 경기장 삭제 */
    @Transactional(rollbackFor = Exception.class)
    public void deleteLeagueVenue(LeagueVenueDTO leagueVenueDTO) throws Exception {
        leagueRepository.deleteLeagueVenue(leagueVenueDTO);
    }

    /** 리그/대회 경기장 상세 조회 **/
    /* 리그/대회 상세 조회 */
    public Map<String, Object> retrieveLeagueVenueById(int leagueId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 경기장 정보
        List<LeagueVenueDTO> venue = leagueRepository.selectLeagueVenueList(leagueId);
        int cnt = leagueRepository.selectLeagueVenueCnt(leagueId);

        map.put("venue", venue);
        map.put("venueCnt", Integer.toString(cnt));

        return map;
    }
}
