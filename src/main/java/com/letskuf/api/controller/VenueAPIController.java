package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.VenueDTO;
import com.letskuf.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VenueAPIController {

    /* 사용할 서비스 주입 */
    private final VenueService venueService;

    /** 경기장소 등록 처리 **/
    @PostMapping("/registerVenue.do")
    public ResultVO registerVenue(@RequestBody VenueDTO venueDTO) throws  Exception {

        ResultVO resultVO = new ResultVO();

        try {
            // 경기장소 등록 서비스 호출
            venueService.registerVenue(venueDTO);

            resultVO.setResultCode(200);
            resultVO.setResultMessage("경기장이x 등록되었습니다.");

        } catch (IllegalArgumentException e) {

            resultVO.setResultCode(400);
            resultVO.setResultMessage(e.getMessage());

        } catch (Exception e) {

            resultVO.setResultCode(500);
            resultVO.setResultMessage("서버 오류가 발생했습니다.");

        }

        return resultVO;
    }

    /** 경기장소 수정 처리 **/
    @PostMapping("/updateVenue.do")
    public ResultVO updateVenue(@RequestBody VenueDTO venueDTO) throws Exception {

        log.info("venueDTO 출력: {}", venueDTO);

        ResultVO resultVO = new ResultVO();

        // 경기장소 수정 처리 서비스 호출
        venueService.updateVenue(venueDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기장소 삭제 처리 **/
    @PostMapping("/deleteVenue.do")
    public ResultVO deleteVenue(@RequestParam("venueId") int venueId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 경기장소 삭제 처리 서비스 호출
        venueService.deleteVenue(venueId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기장소 전체 조회 **/
    @GetMapping("/retrieveVenue.do")
    public ResultVO retrieveVenue(VenueDTO venueDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(venueDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        venueDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        venueDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        venueDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 경기장소 전체 조회해 올 부분 쿼리 조회
        Map<String, Object> resultMap = venueService.retrieveVenue(venueDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("venueDTO", venueDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기장소 상세 조회 **/
    @GetMapping("/retrieveVenueDetail.do")
    public ResultVO retrieveVenueDetail(@RequestParam("venueId") int venueId) throws Exception {

        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = venueService.retrieveVenueById(venueId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기장소 검색 **/
    @GetMapping("/searchVenue.do")
    public ResultVO searchVenue(VenueDTO venueDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(venueDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        venueDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        venueDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        venueDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 검색 서비스 호출
        Map<String, Object> resultMap = venueService.searchVenue(venueDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("venueDTO", venueDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }
}