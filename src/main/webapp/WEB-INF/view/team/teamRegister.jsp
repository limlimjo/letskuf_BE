<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <!-- jquery 다운로드 받아 해당 경로에 넣어주고 경로명 수정 -->
		 <script src="/resources/asset/js/jquery-3.7.1.min.js"></script>
		 <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		 <script src="/resources/asset/js/addressSearch.js"></script>
		 <link rel="stylesheet" type="text/css" href="/resources/asset/css/common.css" />
		 <title>렛츠KUF 팀 등록 페이지</title>
		<script type="text/javascript">
		$(document).ready(function(){
			// 취소
			$(".cancel").on("click", function(){
				location.href = "/retrieveTeam.do";
			});

			$("#submit").on("click", function(){
				if($("#teamNm").val() == ""){
					alert("팀이름을 입력해주세요.");
					$("#teamNm").focus();
					return false;
				}
				if($("#foundYear").val() == ""){
					alert("창단년도를 입력해주세요.");
					$("#foundYear").focus();
					return false;
				}
				if($("#address").val() == ""){
					alert("주소를 입력해주세요.");
					$("#address").focus();
					return false;
				}
				$("#regForm").submit();
			});
		})
		</script>
	</head>
<body>
    <div class="mainWrap">
        <%@ include file="../include/header.jsp" %>
	    <div class="container">
	        <%@ include file="../include/sideMenu.jsp" %>
	        <div class="regFormContainer">
	            <h3>팀 등록</h3>
	            <form id="regForm" method="post" action="${contextPath}/registerTeam.do" enctype="multipart/form-data">
	                <div class="content">
	                    <div class="content">
	                        <label for="teamNm">팀명</label>
	                        <input type="text" id="teamNm" name="teamNm" required><br>
	                    </div>

	                    <div class="content">
	                        <label for="foundYear">창단년도</label>
	                        <input type="text" id="foundYear" name="foundYear" required>
	                    </div>
	                </div>

	                <div class="addressContent">
	                    <label for="address">주소</label>
	                    <div class="address">
	                        <input type="text" id="postcode" name="postcode" placeholder="우편번호" readonly="readonly" />
	                        <input class="addressBtn" type="button" onclick="openDaumPostcode()" value="주소 검색" / >
	                    </div>
	                    <div class="address">
	                        <input type="text" id="address" name="address" placeholder="주소" readonly="readonly" />
	                    </div>
	                    <div class="address">
	                        <input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소" />
	                    </div>
						<input type="hidden" id="regionNm" name="regionNm" />
					</div>

	                <div class="content file">
	                    <div class="content">
	                        <label for="teamFile">사진 등록</label>
	                        <input type="file" id="teamFile" name="teamFile" />
	                    </div>
	                </div>
	            </form>
	            <div class="form-group has-feedback">
	                <button class="btn btn-success" type="button" id="submit">등록</button>
	                <button class="cancel btn btn-danger" type="button">취소</button>
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>