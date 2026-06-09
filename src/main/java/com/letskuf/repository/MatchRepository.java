package com.letskuf.repository;

import com.letskuf.dto.MatchDTO;
import com.letskuf.dto.MatchUniformDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchRepository {

    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /** 경기 등록 **/
    public void saveMatch(MatchDTO matchDTO) {
        sql.insert("Match.matchSave", matchDTO);
    }

    /** 경기 유니폼 등록 **/
    public void saveMatchUniform(MatchUniformDTO matchUniformDTO) {
        sql.insert("Match.matchUniformSave", matchUniformDTO);
    }

    /** 경기 수정 **/
    public void updateMatch(MatchDTO matchDTO) {
        sql.update("Match.matchUpdate", matchDTO);
    }

    /** 경기 유니폼 수정 **/
    public void updateMatchUniform(MatchUniformDTO matchUniformDTO) {
        sql.update("Match.matchUniformUpdate", matchUniformDTO);
    }

    /** 경기 삭제 **/
    public void deleteMatch(int matchId) {
        sql.delete("Match.matchDelete", matchId);
    }

    /** 경기 유니폼 삭제 **/
    public void deleteMatchUniform(int matchUniformId) {
        sql.delete("Match.matchUniformDelete", matchUniformId);
    }

    /** 경기 전체 조회 **/
    public List<MatchDTO> selectMatches(MatchDTO matchDTO) {
        return sql.selectList("Match.matchList", matchDTO);
    }

    /** 경기 전체 건수 조회 **/
    public int selectMatchCnt(MatchDTO matchDTO) {
        return sql.selectOne("Match.matchListCnt", matchDTO);
    }

    /** 경기 상세 조회 **/
    public MatchDTO selectMatchById(int matchId) {
        return sql.selectOne("Match.matchByMatchId", matchId);
    }

    /** 경기 유니폼 상세 조회 **/
    public List<MatchUniformDTO> selectMatchUniformById(int matchId) {
        return sql.selectList("Match.matchUniformByMatchId", matchId);
    }
}
