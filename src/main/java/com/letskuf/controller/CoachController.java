package com.letskuf.controller;

import com.letskuf.dto.CoachDTO;
import com.letskuf.dto.TeamDTO;
import com.letskuf.service.CoachService;
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
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CoachController {
    /* 사용할 서비스 주입 */
    private final TeamService teamService;
    private final CoachService coachService;

    /* 지도자 등록 페이지 */
//    @GetMapping("/registerCoach.do")
//    public ModelAndView registerCoachView(HttpServletRequest request, ModelMap model) throws Exception {
//
//        TeamDTO teamDTO = new TeamDTO();
//
//        // 팀 목록 조회
//        List<TeamDTO> teamList = teamService.retrieveTeams(teamDTO);
//
//        model.addAttribute("teamList", teamList);
//
//        return new ModelAndView("/coach/coachRegister");
//    }

    /* 지도자 등록 처리 */
    @PostMapping("/registerCoach.do")
    public ModelAndView registerCoach(@ModelAttribute("coachDTO") CoachDTO coachDTO) throws IOException {
        // 지도자 등록 서비스 호출
        coachService.registerCoach(coachDTO);
        return new ModelAndView("redirect:/retrieveCoach.do");
    }

    /* 지도자 전체 조회 */
//    @GetMapping("/retrieveCoach.do")
//    public ModelAndView retrieveCoach(@ModelAttribute("coachDTO") CoachDTO coachDTO, Model model) throws Exception {
//        // 지도자 전체 목록 조회해 올 부분 쿼리 조회
//        List<CoachDTO> result = coachService.retrieveCoaches(coachDTO);
//
//        model.addAttribute("coaches", result);
//
//        return new ModelAndView("coach/coachList");
//    }
}
