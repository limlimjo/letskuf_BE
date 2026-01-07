package com.letskuf.controller;

import com.letskuf.dto.PlayerDTO;
import com.letskuf.service.TeamService;
import com.letskuf.dto.TeamDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamController {
    /* 사용할 서비스 주입 */
//    private final TeamService teamService;
//
//    /** 팀 등록 페이지 **/
//    @GetMapping("/registerTeam.do")
//    public ModelAndView registerTeamView() {
//        return new ModelAndView("team/teamRegister");
//    }
//
//    /**  팀 등록 처리 **/
//    @PostMapping("/registerTeam.do")
//    public ModelAndView registerTeam(@ModelAttribute("teamDTO") TeamDTO teamDTO) throws IOException {
//        // 팀 등록 처리 서비스 호출
//        teamService.registerTeam(teamDTO);
//        return new ModelAndView("redirect:/retrieveTeam.do");
//    }
//
//    /** 주소 세션과 쿠키에 저장 **/
//
//
//    /** 팀 검색 ajax 호출 **/
//    @GetMapping("/searchTeam")
//    public List<TeamDTO> searchTeam(@ModelAttribute("teamDTO") TeamDTO teamDTO,
//                                    @RequestParam("searchQuery") String searchQuery) throws Exception {
//        List<TeamDTO> teamResult = teamService.retrieveTeams(teamDTO);
//
//        List<TeamDTO> queryResult = teamResult.stream()
//                .filter(team -> team.getTeamNm().toLowerCase().contains(searchQuery.toLowerCase()))
//                .collect(Collectors.toList());
//        return queryResult;
//    }
//
//    /** 팀 수정 페이지 **/
//    @GetMapping("/modifyTeam.do")
//    public ModelAndView modifyTeamView() {
//        return new ModelAndView("team/teamModify");
//    }
//
//    /** 팀 수정 처리 **/
//    @PostMapping("/modifyTeam.do")
//    public ModelAndView modifyTeam(@ModelAttribute("teamDTO") TeamDTO teamDTO) throws IOException {
//        // 팀 수정 처리 서비스 호출
//
//        return new ModelAndView("redirect:/retrieveTeamDetail.do");
//    }
//
//    /** 팀 전체 조회 **/
//    @GetMapping("/retrieveTeam.do")
//    public ModelAndView retrieveTeam(@ModelAttribute("teamDTO") TeamDTO teamDTO, Model model) throws Exception {
//        // 팀 전체 조회해 올 부분 쿼리 조회
//        List<TeamDTO> result = teamService.retrieveTeams(teamDTO);
//
//        //System.out.println("result 출력: " + result);
//
//        model.addAttribute("teams", result);
//
//        return new ModelAndView("team/teamList");
//    }
//
//    /** 팀 상세 조회 **/
//    @GetMapping("/retrieveTeamDetail.do")
//    public ModelAndView retrieveTeamDetail(@RequestParam("teamId") int teamId, Model model) throws Exception {
//        // teamId setting 해주고 그 teamId에 해당하는 쿼리 조회
//        // 쿼리 조회해올 때 팀에 해당되는 지도자와 선수도 조회해옴
//        TeamDTO team = teamService.retrieveTeamById(teamId);
//
//        // 선수 리스트는 uniformNum 기준으로 정렬
//        List<PlayerDTO> sortedPlayers = team.getPlayers().stream()
//                                            .sorted(Comparator.comparingInt(PlayerDTO::getUniformNum))
//                                            .toList();
//        team.setPlayers(sortedPlayers);
//
//        model.addAttribute("team", team);
//
//        //System.out.println("team 출력: " + team);
//
//        return new ModelAndView("team/teamListDetail");
//    }

}
