package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.LeagueDTO;
import com.letskuf.service.LeagueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LeagueAPIController {

    /* 사용할 서비스 주입 */
    private final LeagueService leagueService;

    /** 리그/대회 등록 처리 **/
    @PostMapping("/registerLeague.do")
    public ResultVO registerLeague(@ModelAttribute("leagueDTO") LeagueDTO leagueDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 리그/대회 등록 서비스 호출
        leagueService.registerLeague(leagueDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 리그/대회 수정 처리 **/
    @PostMapping("/updateLeague.do")
    public ResultVO updateLeague(@RequestBody LeagueDTO leagueDTO) throws Exception {

        log.info("leagueDTO 출력: {}", leagueDTO);

        ResultVO resultVO = new ResultVO();

        // 리그/대회 수정 처리 서비스 호출
        leagueService.updateLeague(leagueDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 리그/대회 삭제 처리 **/
    @PostMapping("/deleteLeague.do")
    public ResultVO deleteLeague(@RequestParam("leagueId") int leagueId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 리그/대회 삭제 처리 서비스 호출
        leagueService.deleteLeague(leagueId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 리그/대회 전체 조회 **/
    @GetMapping("/retrieveLeague.do")
    public ResultVO retrieveLeague(@ModelAttribute("leagueDTO") LeagueDTO leagueDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(leagueDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        leagueDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        leagueDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        leagueDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 리그/대회 전체 조회해 올 부분 쿼리 조회
        Map<String, Object> resultMap = leagueService.retrieveLeague(leagueDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("leagueDTO", leagueDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 리그/대회 상세 조회 **/
    @GetMapping("/retrieveLeagueDetail.do")
    public ResultVO retrieveLeagueDetail(@RequestParam("leagueId") int leagueId) throws Exception {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = leagueService.retrieveLeagueById(leagueId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

}
