<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <!-- jquery 다운로드 받아 해당 경로에 넣어주고 경로명 수정 -->
		 <script src="/resources/asset/js/jquery-3.7.1.min.js"></script>
		 <link rel="stylesheet" type="text/css" href="/resources/asset/css/common.css" />
		 <title>렛츠KUF 메인 페이지</title>
		 <script>
		 </script>
	</head>
<body>
    <div class="mainWrap">
        <%@ include file="../include/header.jsp" %>
        <div class="container">
        <%@ include file="../include/sideMenu.jsp" %>
            <div class="regFormContainer">
                <h3>선수 목록</h3>
                <form method="post">
                    <table class="lookupContainer">
                        <tbody>
                            <tr>
                            <td>
                                <select name="search_condition" disabled>
                                    <option value="전체" checked>전체</option>
                                    <option value=""></option>
                                    <option value=""></option>
                                    <option value=""></option>
                                </select>
                                <input type="text"  size="30" disabled/>
                                <input type="button"  value="조회" disabled/>
                            </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
                <div></div>
                <table class="courseList">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>선수명</th>
                            <th>팀명</th>
                            <th>포지션</th>
                            <th>배번</th>
                            <th>학년</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty players}">
                                <tr>
                                    <td>아무것도 없음</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="item" items="${players}">
                                    <tr>
                                        <td>${item.no}</td>
                                        <td>
                                            <img src="" />
                                            ${item.playerNm}
                                        </td>
                                        <td>${item.teamNm}</td>
                                        <td>${item.position}</td>
                                        <td>${item.uniformNum}</td>
                                        <td>${item.grade}</td>
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