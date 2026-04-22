package com.letskuf.service;

import com.letskuf.dto.VenueDTO;
import com.letskuf.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VenueService {

    /* 사용할 서비스 주입 */
    private final VenueRepository venueRepository;

    /* 경기장소 등록 */
    @Transactional(rollbackFor = Exception.class)
    public void registerVenue(VenueDTO venueDTO) throws Exception {

        // 중복 체크
        int count = venueRepository.countByNameAndAddress(venueDTO);

        if (count > 0) {
            throw new IllegalArgumentException("이미 등록된 경기장입니다.");
        }

        try {
            // 저장
            venueRepository.save(venueDTO);

        } catch (DataIntegrityViolationException e) {
            // DB 중복 예외
            throw new IllegalArgumentException("이미 등록된 경기장입니다.");

        }
    }

    /* 경기장소 수정 */
    @Transactional(rollbackFor = Exception.class)
    public void updateVenue(VenueDTO venueDTO) throws Exception {
        venueRepository.update(venueDTO);
    }

    /* 경기장소 삭제 */
    @Transactional(rollbackFor = Exception.class)
    public void deleteVenue(int venueId) throws Exception {

        // 기존 경기장소 정보 존재 확인
        VenueDTO existingVenue = venueRepository.selectVenueById(venueId);
        if (existingVenue == null) {
            throw new IllegalArgumentException("존재하지 않는 경기장소 정보입니다.: " + venueId);
        }

        venueRepository.delete(venueId);
    }

    /* 경기장소 전체 조회 */
    public Map<String, Object> retrieveVenue(VenueDTO venueDTO) throws Exception {

        Map<String, Object> map = new HashMap<>();

        List<VenueDTO> list = venueRepository.selectVenue(venueDTO);
        int cnt = venueRepository.selectVenueCnt(venueDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /* 경기장소 상세 조회 */
    public Map<String, Object> retrieveVenueById(int venueId) throws Exception {

        Map<String, Object> map = new HashMap<>();

        VenueDTO venue = venueRepository.selectVenueById(venueId);

        map.put("venue", venue);

        return map;
    }

    /* 경기장소 검색 */
    public Map<String, Object> searchVenue(VenueDTO venueDTO) throws Exception {

        Map<String, Object> map = new HashMap<>();

        List<VenueDTO> list = venueRepository.selectSearchVenue(venueDTO);
        int cnt = venueRepository.selectSearchVenueCnt(venueDTO);

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }
}
