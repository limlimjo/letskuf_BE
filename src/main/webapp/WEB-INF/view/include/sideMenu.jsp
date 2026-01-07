<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<body>
	<div class="sidebarContainer">
    	<div class="sidebarBox">
    	    <div class="sidebarMainTitle">
    	        <img src="/resources/asset/img/team_logo.png" class="sideLogo" />
                <span class="txtCate">팀 관리</span><br/>
    	    </div>
    	    <div class="sidebarDetailTitle">
    		    <a href="/retrieveTeam.do" class="txtList">팀 목록</a><br/>
    		    <a href="/registerTeam.do" class="txtList">팀 등록</a><br/>
    		</div>
    	</div>
    	<div class="sidebarBox">
    	    <div class="sidebarMainTitle">
                <img src="/resources/asset/img/player_logo.png" class="sideLogo" />
                <span class="txtCate">선수 관리</span><br/>
            </div>
            <div>
                <a href="/retrievePlayer.do" class="txtList">선수 목록</a><br/>
                <a href="/retrieveCoach.do" class="txtList">지도자 목록</a><br/>
                <a href="/registerPlayer.do" class="txtList">선수 등록</a><br/>
                <a href="/registerCoach.do" class="txtList">지도자 등록</a><br/>
            </div>
    	</div>
        <div class="sidebarBox">
            <div class="sidebarMainTitle">
                <img src="/resources/asset/img/league_logo.png" class="sideLogo" />
                <span class="txtCate">리그/대회 관리</span><br/>
            </div>
            <div>
                <a href="#" class="txtList">리그/대회 목록</a><br/>
                <a href="#" class="txtList">리그/대회 등록</a><br/>
            </div>
        </div>
        <div class="sidebarBox">
            <div class="sidebarMainTitle">
                <img src="/resources/asset/img/match_logo.png" class="sideLogo" />
                <span class="txtCate">경기 관리</span><br/>
            </div>
            <div>
                <a href="#" class="txtList">경기 목록</a><br/>
                <a href="#" class="txtList">경기 등록</a><br/>
                <a href="#" class="txtList">실시간 경기 등록<br/>(오늘 경기)</a><br/>
            </div>
        </div>
    </div>
</body>
</html>