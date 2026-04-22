package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.TeamDTO;
import com.letskuf.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173") // 프론트엔드 주소
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamAPIController {

    /* 사용할 서비스 주입 */
    private final TeamService teamService;

    /**  팀 등록 처리 **/
    @PostMapping("/registerTeam.do")
    public ResultVO registerTeam(@ModelAttribute TeamDTO teamDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        // teamFile이 null이면 빈 리스트로 초기화
        if (teamDTO.getTeamFile() == null) {
            teamDTO.setTeamFile(new ArrayList<>());
        }

        // 팀 등록 처리 서비스 호출
        teamService.registerTeam(teamDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 팀 수정 처리 **/
    @PostMapping("/updateTeam.do")
    public ResultVO updateTeam(@ModelAttribute TeamDTO teamDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        // teamFile이 null이면 빈 리스트로 초기화
        if (teamDTO.getTeamFile() == null) {
            teamDTO.setTeamFile(new ArrayList<>());
        }

        // 팀 수정 처리 서비스 호출
        teamService.updateTeam(teamDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 팀 삭제 처리 **/
    @PostMapping("/deleteTeam.do")
    public ResultVO deleteTeam(@RequestParam("teamId") int teamId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 팀 삭제 처리 서비스 호출
        teamService.deleteTeam(teamId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 팀 전체 조회 **/
    @GetMapping("/retrieveTeam.do")
    public ResultVO retrieveTeam(TeamDTO teamDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(teamDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        teamDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        teamDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        teamDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 팀 전체 조회해 올 부분 쿼리 조회
        Map<String, Object> resultMap = (Map<String, Object>) teamService.retrieveTeams(teamDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("teamDTO", teamDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 팀 목록 조회 **/
    @GetMapping("/retrieveTeamList.do")
    public ResultVO retrieveTeamList(TeamDTO teamDTO) throws Exception {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = teamService.retrieveTeamsList(teamDTO);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 팀 상세 조회 **/
    @GetMapping("/retrieveTeamDetail.do")
    public ResultVO retrieveTeamDetail(@RequestParam("teamId") int teamId) throws Exception {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = teamService.retrieveTeamById(teamId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 팀 상세 조회 (수정용) **/
    @GetMapping("/retrieveUpdateTeamDetail.do")
    public ResultVO retrieveUpdateTeamDetail(@RequestParam("teamId") int teamId) throws Exception {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = teamService.retrieveUpdateTeamById(teamId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }
}