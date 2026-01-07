package com.letskuf.repository;

import com.letskuf.dto.*;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CoachRepository {
    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /* 코칭스태프/임원 등록 */
    public CoachDTO save(CoachDTO coachDTO) {
        sql.insert("Coach.coachSave", coachDTO);
        return coachDTO;
    }

    /* 코칭스태프/임원 수정 */
    public void update(CoachDTO coachDTO) {
        sql.update("Coach.coachUpdate", coachDTO);
    }

    /* 코칭스태프/임원 삭제 */
    public void delete(int coachId) {
        sql.delete("Coach.coachDelete", coachId);
    }

    /* 코칭스태프/임원 사진 조회 */
    public List<CoachFileDTO> selectCoachFileListByListByCoachId(int coachId) {
        return sql.selectList("Coach.coachFileListByCoachId", coachId);
    }

    /* 코칭스태프/임원 사진 저장 */
    public void saveFile(CoachFileDTO coachFileDTO) {
        sql.insert("Coach.coachFileSave", coachFileDTO);
    }

    /* 코칭스태프/임원 사진 삭제 */
    public void deleteFile(int fileId) {
        sql.delete("Coach.coachFileDelete", fileId);
    }

    /* 코칭스태프/임원 전체 조회 */
    public List<CoachDTO> selectCoaches(CoachDTO coachDTO) {
        return sql.selectList("Coach.coachList", coachDTO);
    }

    /* 코칭스태프/임원 전체 건수 조회 */
    public int selectCoachCnt(CoachDTO coachDTO) {
        return sql.selectOne("Coach.coachListCnt", coachDTO);
    }

    /* 코칭스태프/임원 상세 조회(teamId에 따른) */
    public List<CoachDTO> selectCoachByTeamId(int teamId) {
        return sql.selectList("Coach.coachListByTeamId", teamId);
    }

    /* 코칭스태프/임원 상세 조회 */
    public CoachDTO selectCoachById(int coachId) {
        return sql.selectOne("Coach.coachByCoachId", coachId);
    }

    /* 코칭스태프/임원 상세 조회 (수정용) */
    public CoachDTO selectUpdateCoachById(int coachId) {
        return sql.selectOne("Coach.coachUpdateListByCoachId", coachId);
    }

    /* 코칭스태프/임원 사진 조회 (수정용) */
    public CoachFileDTO selectUpdateCoachFileById(int coachId) {
        return sql.selectOne("Coach.coachFileUpdateListByCoachId", coachId);
    }
}
