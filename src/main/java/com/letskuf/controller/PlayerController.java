package com.letskuf.controller;

import com.letskuf.dto.PlayerDTO;
import com.letskuf.dto.TeamDTO;
import com.letskuf.service.PlayerService;
import com.letskuf.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayerController {
    /* 사용할 서비스 주입 */
    // 필드 주입 사용x, 생성자 주입 사용
    private final TeamService teamService;
    private final PlayerService playerService;

    /* 선수 등록 페이지 */
//    @GetMapping("/registerPlayer.do")
//    public ModelAndView registerPlayerView(HttpServletRequest request, ModelMap model) throws Exception {
//
//        TeamDTO teamDTO = new TeamDTO();
//
//        // 팀 목록 조회
//        List<TeamDTO> teamList = teamService.retrieveTeams(teamDTO);
//
//        model.addAttribute("teamList", teamList);
//
//        return new ModelAndView("player/playerRegister");
//    }

    /* 선수 등록 처리 */
//    @PostMapping("/registerPlayer.do")
//    public ModelAndView registerPlayer(@ModelAttribute("playerDTO") PlayerDTO playerDTO) throws IOException {
//        // 선수 등록 처리 서비스 호출
//        playerService.registerPlayer(playerDTO);
//        return new ModelAndView("redirect:/retrievePlayer.do");
//    }

    /** 선수 수정 페이지 **/

    /** 선수 수정 처리 **/

//    /** 선수 전체 조회 **/
//    @GetMapping("/retrievePlayer.do")
//    public ModelAndView retrievePlayer(@ModelAttribute("playerDTO") PlayerDTO playerDTO, Model model) throws Exception {
//        // 선수 전체 조회해 올 부분 쿼리 조회
//        List<PlayerDTO> result = playerService.retrievePlayers(playerDTO);
//
//        model.addAttribute("players", result);
//
//        return new ModelAndView("player/playerList");
//    }
//
//    /** 선수 상세 조회 **/
//    @GetMapping("/retrievePlayerDetail.do")
//    public ModelAndView retrievePlayerDetail(@RequestParam("playerId") int playerId, Model model) throws Exception {
//        // playerId setting 해주고 그 playerId에 해당하는 쿼리 조회
//
//        return new ModelAndView("player/playerListDetail");
//    }
}
