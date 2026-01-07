package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.common.PaginationInfo;
import com.letskuf.dto.PlayerDTO;
import com.letskuf.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173") // 프론트엔드 주소
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerAPIController {

    /* 사용할 서비스 주입 */
    private final PlayerService playerService;

    /** 선수단 (선수) 등록 처리 **/
    @PostMapping("/registerPlayer.do")
    public ResultVO registerPlayer(@ModelAttribute("playerDTO") PlayerDTO playerDTO) throws IOException {

        ResultVO resultVO = new ResultVO();

        // playerFile이 null이면 빈 리스트로 초기화
        if (playerDTO.getFile() == null) {
            playerDTO.setFile(new ArrayList<>());
        }

        // 선수단 등록 처리 서비스 호출
        playerService.registerPlayer(playerDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수단(선수) 수정 처리 **/
    @PostMapping("/updatePlayer.do")
    public ResultVO updatePlayer(@ModelAttribute("playerDTO") PlayerDTO playerDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        // playerFile이 null이면 빈 리스트로 초기화
        if (playerDTO.getFile() == null) {
            playerDTO.setFile(new ArrayList<>());
        }

        // 선수 수정 처리 서비스 호출
        playerService.updatePlayer(playerDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수단(선수) 삭제 처리 **/
    @PostMapping("/deletePlayer.do")
    public ResultVO deletePlayer(@RequestParam("playerId") int playerId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 선수 삭제 처리 서비스 호출
        playerService.deletePlayer(playerId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수단 (선수) 전체 조회 **/
    @GetMapping("/retrievePlayer.do")
    public ResultVO retrievePlayer(@ModelAttribute("playerDTO") PlayerDTO playerDTO, Model model) throws Exception {

        ResultVO resultVO = new ResultVO();

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(playerDTO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);

        playerDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        playerDTO.setLastIndex(paginationInfo.getLastRecordIndex());
        playerDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        // 선수 전체 조회해 올 부분 쿼리 조회
        Map<String, Object> resultMap = playerService.retrievePlayers(playerDTO);

        // 전체 갯수
        int totCnt = Integer.parseInt((String) resultMap.get("resultCnt"));
        paginationInfo.setTotalRecordCount(totCnt);

        resultMap.put("playerDTO", playerDTO);
        resultMap.put("paginationInfo", paginationInfo);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 선수 상세 조회 (수정용) **/
    @GetMapping("/retrieveUpdatePlayerDetail.do")
    public ResultVO retrieveUpdatePlayerDetail(@RequestParam("playerId") int playerId) throws Exception {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = playerService.retrieveUpdatePlayerById(playerId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }
}
