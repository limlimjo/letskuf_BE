package com.letskuf.repository;

import com.letskuf.dto.TeamDTO;
import com.letskuf.dto.TeamFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /* 팀 등록 */
    public TeamDTO save(TeamDTO teamDTO) {
        sql.insert("Team.teamSave", teamDTO);
        return teamDTO;
    }

    /* 팀 수정 */
    public void update(TeamDTO teamDTO) {
        sql.update("Team.teamUpdate", teamDTO);
    }

    /* 팀 삭제 */
    public void delete(int teamId) {
        sql.delete("Team.teamDelete", teamId);
    }

    /* 팀 사진 조회 */
    public List<TeamFileDTO> selectTeamFileListByTeamId(int teamId) {
        return sql.selectList("Team.teamFileListByTeamId", teamId);
    }

    /* 팀 사진 저장 */
    public void saveFile(TeamFileDTO teamFileDTO) {
        sql.insert("Team.teamFileSave", teamFileDTO);
    }

    /* 팀 사진 삭제 */
    public void deleteFile(int fileId) {
        sql.delete("Team.teamFileDelete", fileId);
    }

    /* 팀 전체 조회 */
    public List<TeamDTO> selectTeams(TeamDTO teamDTO) {
        return sql.selectList("Team.teamList", teamDTO);
    }

    /* 팀 전체 건수 조회 */
    public int selectTeamsCnt(TeamDTO teamDTO) {
        return sql.selectOne("Team.teamListCnt", teamDTO);
    }

    /* 팀 목록 조회 */
    public List<TeamDTO> selectTeamsList(TeamDTO teamDTO) {
        return sql.selectList("Team.teamListAll", teamDTO);
    }

    /* 팀 상세 조회 */
    public TeamDTO selectTeamById(int teamId) {
        return sql.selectOne("Team.teamListByTeamId", teamId);
    }

    /* 팀 상세 조회 (수정용) */
    public TeamDTO selectUpdateTeamById(int teamId) {
        return sql.selectOne("Team.teamUpdateListByTeamId", teamId);
    }

    /* 팀 사진 조회 (수정용) */
    public TeamFileDTO selectUpdateTeamFileById(int teamId) {
        return sql.selectOne("Team.teamFileUpdateListByTeamId", teamId);
    }
}
