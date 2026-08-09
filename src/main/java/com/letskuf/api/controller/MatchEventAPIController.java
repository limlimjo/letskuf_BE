package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import com.letskuf.dto.MatchEventSaveDTO;
import com.letskuf.dto.MatchStatusDTO;
import com.letskuf.service.MatchEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchEventAPIController {

    /* 사용할 서비스 주입 */
    private final MatchEventService matchEventService;

    /** 실시간 경기 정보 조회 **/
    @GetMapping("/retrieveMatchLiveInfo.do")
    public ResultVO retrieveMatchLiveInfo(@RequestParam int matchId) throws Exception {

        ResultVO resultVO = new ResultVO();

        // 실시간 경기 정보 조회 서비스 호출
        Map<String, Object> resultMap = matchEventService.retrieveMatchLiveInfo(matchId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 이벤트 등록 **/
    @PostMapping("/registerMatchEvent.do")
    public ResultVO registerMatchEvent(@RequestBody MatchEventSaveDTO matchEventSaveDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        matchEventService.registerMatchEvent(matchEventSaveDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 이벤트 삭제 **/
    @PostMapping("/deleteMatchEvent.do")
    public ResultVO deleteMatchEvent(@RequestParam int eventId) throws Exception {

        ResultVO resultVO = new ResultVO();

        matchEventService.deleteMatchEvent(eventId);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 경기 상태 변경 **/
    @PostMapping("/changeMatchStatus.do")
    public ResultVO changeMatchStatus(@RequestBody MatchStatusDTO matchStatusDTO) throws Exception {

        ResultVO resultVO = new ResultVO();

        String currentStatus = matchStatusDTO.getStatus();

        matchEventService.changeMatchStatus(matchStatusDTO);

        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

    /** 타임라인 조회 **/
    @GetMapping("/retrieveMatchTimeline.do")
    public ResultVO retrieveMatchTimeline(@RequestParam int matchId) throws Exception {

        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = matchEventService.retrieveMatchTimeline(matchId);

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("성공했습니다.");

        return resultVO;
    }

}
