package com.letskuf.api.controller;

import com.letskuf.api.dto.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAPIController {

    // 로그인 상태 확인 API
    @GetMapping("/me")
    public ResultVO me(Authentication authentication) {
        log.info("로그인 상태 확인 API 출력");
        ResultVO resultVO = new ResultVO();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", authentication.getName());
        resultMap.put("roles", authentication.getAuthorities());

        resultVO.setResult(resultMap);
        resultVO.setResultCode(200);
        resultVO.setResultMessage("authorized");

        return resultVO;
    }
}
