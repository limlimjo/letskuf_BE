package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.dto.MatchLineupSaveDTO;
import com.letskuf.service.MatchLineupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchLineupAPIController {

    /* 사용할 서비스 주입 */
    private final MatchLineupService matchLineupService;

    /** 라인업 조회 **/
    @GetMapping("/retrieveMatchLineupInfo.do")
    public ResultVO retrieveMatchLineupInfo(@RequestParam int matchId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 라인업 조회 서비스 호출
        Map<String, Object> resultMap = matchLineupService.retrieveMatchLineupInfo(matchId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 라인업 등록 처리 **/
    @PostMapping("/registerMatchLineup.do")
    public ResultVO registerMatchLineup(@RequestBody MatchLineupSaveDTO matchLineupSaveDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        if (matchLineupSaveDTO.getHomePlayers().stream()
                .filter(p -> p.getIsStarting() == 1)
                .count() != 11) {

            throw new RuntimeException("HOME 선발은 11명이어야 합니다.");
        }

        if (matchLineupSaveDTO.getAwayPlayers().stream()
                .filter(p -> p.getIsStarting() == 1)
                .count() != 11) {

            throw new RuntimeException("AWAY 선발은 11명이어야 합니다.");
        }

        // 라인업 등록 서비스 호출
        matchLineupService.registerMatchLineup(matchLineupSaveDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }
}
