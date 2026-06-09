package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.MatchDTO;
import com.letskuf.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchAPIController {

    /* 사용할 서비스 주입 */
    private final MatchService matchService;

    /** 경기 등록 처리 **/
    @PostMapping("/registerMatch.do")
    public ResultVO registerMatch(@RequestBody MatchDTO matchDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 경기 등록 서비스 호출
        matchService.registerMatch(matchDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기 수정 처리 **/
    @PostMapping("/updateMatch.do")
    public ResultVO updateMatch(@RequestBody MatchDTO matchDTO) throws Exception {

        log.info("MatchDTO 출력: {}", matchDTO);

        ResultVO resultVO = new ResultVO();

        // 경기 수정 처리 서비스 호출
        matchService.updateMatch(matchDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기 삭제 처리 **/
    @PostMapping("/deleteMatch.do")
    public ResultVO deleteMatch(@RequestParam("matchId") int matchId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 경기 삭제 처리 서비스 호출
        matchService.deleteMatch(matchId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기 전체 조회 **/
    @GetMapping("/retrieveMatch.do")
    public ResultVO retrieveMatch(MatchDTO matchDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(matchDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        matchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        matchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        matchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 경기 전체 조회해 올 부분 쿼리 조회
        Map<String, Object> resultMap = matchService.retrieveMatches(matchDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("matchDTO", matchDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기 상세 조회 **/
    @GetMapping("/retrieveMatchDetail.do")
    public ResultVO retrieveMatchDetail(@RequestParam("matchId") int matchId) throws Exception {

        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = matchService.retrieveMatchById(matchId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }
}
