package com.letskuf.repository;

import com.letskuf.dto.VenueDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VenueRepository {
    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /* 경기장소 등록 */
    public void save(VenueDTO venueDTO) {
        sql.insert("Venue.venueSave", venueDTO);
    }

    /* 경기장소 중복 체크 */
    public int countByNameAndAddress(VenueDTO venueDTO) {
        return sql.selectOne("Venue.countByNameAndAddress", venueDTO);
    }

    /* 경기장소 수정 */
    public void update(VenueDTO venueDTO) {
        sql.update("Venue.venueUpdate", venueDTO);
    }

    /* 경기장소 삭제 */
    public void delete(int venueId) {
        sql.delete("Venue.venueDelete", venueId);
    }

    /* 경기장소 전체 건수 조회 */
    public int selectVenueCnt(VenueDTO venueDTO) {
        return sql.selectOne("Venue.venueListCnt", venueDTO);
    }

    /* 경기장소 전체 조회 */
    public List<VenueDTO> selectVenue(VenueDTO venueDTO) {
        return sql.selectList("Venue.venueList", venueDTO);
    }

    /* 경기장소 상세 조회 */
    public VenueDTO selectVenueById(int venueId) {
        return sql.selectOne("Venue.venueByVenueId", venueId);
    }

    /* 경기장소 검색 건수 조회 */
    public int selectSearchVenueCnt(VenueDTO venueDTO) {
        return sql.selectOne("Venue.searchVenueCnt", venueDTO);
    }

    /* 경기장소 검색 */
    public List<VenueDTO> selectSearchVenue(VenueDTO venueDTO) {
        return sql.selectList("Venue.searchVenue", venueDTO);
    }
}
