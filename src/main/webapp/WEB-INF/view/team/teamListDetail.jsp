<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <!-- jquery 다운로드 받아 해당 경로에 넣어주고 경로명 수정 -->
		 <script src="/resources/asset/js/jquery-3.7.1.min.js"></script>
		 <link rel="stylesheet" type="text/css" href="/resources/asset/css/common.css" />
		 <title>렛츠KUF 팀 상세 페이지</title>
		 <script>
		 </script>
	</head>
<body>
    <div class="mainWrap">
        <%@ include file="../include/header.jsp" %>
        <div class="container">
        <%@ include file="../include/sideMenu.jsp" %>
            <div class="regFormContainer">
                <h3>${team.teamNm}</h3>
                <p>${team.address}</p>
                <br/>
                <h4>지도자</h4>
                <table class="courseList">
                    <thead>
                        <tr>
                            <th>이름</th>
                            <th>직함</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty team.coaches}">
                            <tr>
                                <td colspan="2">코치를 찾을 수 없습니다.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="coach" items="${team.coaches}">
                                <tr>
                                    <td>${coach.coachNm}</td>
                                    <td>${coach.position}</td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
                <h4>선수</h4>
                <table class="courseList">
                    <thead>
                    <tr>
                        <th>배번</th>
                        <th>이름</th>
                        <th>포지션</th>
                        <th>학년</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty team.players}">
                            <tr>
                                <td colspan="2">선수를 찾을 수 없습니다.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="player" items="${team.players}">
                                <tr>
                                    <td>${player.uniformNum}</td>
                                    <td>${player.playerNm}</td>
                                    <td>${player.position}</td>
                                    <td>${player.grade}</td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>