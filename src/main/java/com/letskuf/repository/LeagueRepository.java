package com.letskuf.repository;

import com.letskuf.dto.LeagueDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeagueRepository {
    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /* 리그/대회 등록 */
    public void save(LeagueDTO leagueDTO) {
        sql.insert("League.leagueSave", leagueDTO);
    }

    /* 리그/대회 수정 */
    public void update(LeagueDTO leagueDTO) {
        sql.update("League.leagueUpdate", leagueDTO);
    }

    /* 리그/대회 삭제 */
    public void delete(int leagueId) {
        sql.delete("League.leagueDelete", leagueId);
    }

    /* 리그/대회 전체 조회 */
    public List<LeagueDTO> selectLeague(LeagueDTO leagueDTO) {
        return sql.selectList("League.leagueList", leagueDTO);
    }

    /* 리그/대회 전체 건수 조회 */
    public int selectLeagueCnt(LeagueDTO leagueDTO) {
        return sql.selectOne("League.leagueListCnt", leagueDTO);
    }

    /* 리그/대회 상세 조회 */
    public LeagueDTO selectLeagueById(int leagueId) {
        return sql.selectOne("League.leagueByLeagueId", leagueId);
    }

}
