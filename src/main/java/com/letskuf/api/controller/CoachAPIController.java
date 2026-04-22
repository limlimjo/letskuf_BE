package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.CoachDTO;
import com.letskuf.service.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CoachAPIController {

    /* 사용할 서비스 주입 */
    private final CoachService coachService;

    /* TODO: 한 대학교에 한 명의 감독만 등록될 수 있게 체크 필요 */
    /** 선수단 (코칭스태프/임원) 등록 처리**/
    @PostMapping("/registerCoach.do")
    public ResultVO registerCoach(@ModelAttribute CoachDTO coachDTO) throws IOException {

        ResultVO resultVO = new ResultVO();

        // coachFile이 null이면 빈 리스트로 초기화
        if (coachDTO.getFile() == null) {
            coachDTO.setFile(new ArrayList<>());
        }

        // 선수단 (코칭스태프/임원) 처리 서비스 호출
        coachService.registerCoach(coachDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수단 (코칭스태프/임원) 수정 처리 **/
    @PostMapping("/updateCoach.do")
    public ResultVO updateCoach(@ModelAttribute CoachDTO coachDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        // playerFile이 null이면 빈 리스트로 초기화
        if (coachDTO.getFile() == null) {
            coachDTO.setFile(new ArrayList<>());
        }

        // 선수 수정 처리 서비스 호출
        coachService.updateCoach(coachDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수단 (코칭스태프/임원) 삭제 처리 **/
    @PostMapping("/deleteCoach.do")
    public ResultVO deleteCoach(@RequestParam("coachId") int coachId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 선수 삭제 처리 서비스 호출
        coachService.deleteCoach(coachId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수단 (코칭스태프/임원) 전체 조회 **/
    @GetMapping("/retrieveCoach.do")
    public ResultVO retrieveCoach(CoachDTO coachDTO, Model model) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(coachDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        coachDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        coachDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        coachDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 코칭스태프/임원 전체 목록 조회해 올 부분 쿼리 조회
        Map<String, Object> resultMap = coachService.retrieveCoaches(coachDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("coachDTO", coachDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 코칭스태프/임원 상세 조회 (수정용) **/
    @GetMapping("/retrieveUpdateCoachDetail.do")
    public ResultVO retrieveUpdateCoachDetail(@RequestParam("coachId") int coachId) throws Exception {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = coachService.retrieveUpdateCoachById(coachId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }
}