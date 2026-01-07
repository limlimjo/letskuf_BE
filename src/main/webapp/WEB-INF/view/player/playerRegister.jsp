<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <!-- jquery 다운로드 받아 해당 경로에 넣어주고 경로명 수정 -->
		 <script src="/resources/asset/js/jquery-3.7.1.min.js"></script>
		 <!-- Select2 CSS & JS -->
		 <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
		 <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
		 <link rel="stylesheet" type="text/css" href="/resources/asset/css/common.css" />
		 <title>렛츠KUF 선수 등록 페이지</title>
		 <script type="text/javascript">
            $(document).ready(function(){
                // 팀 검색 (select 요소에 Select2 적용)
				$('#teamId').select2({
					placeholder: '소속팀을 선택하세요',
					allowClear: false,
					width: '100%' // 명시적으로 너비 설정
				});
                // 취소
                $(".cancel").on("click", function(){
                    location.href = "/";
                });

                $("#submit").on("click", function(){
                    // if($("#title").val() == ""){
                    //     alert("콘텐츠제목을 입력해주세요.");
                    //     $("#title").focus();
                    //     return false;
                    // }
                    // if($("#contents").val() == ""){
                    //     alert("콘텐츠 내용을 입력해주세요.");
                    //     $("#contents").focus();
                    //     return false;
                    // }
                    // if($("#category").val() == ""){
                    //     alert("모집 유형을 입력해주세요.");
                    //     $("#category").focus();
                    //     return false;
                    // }
                    // if($("#startDate").val() == ""){
                    //     alert("작업 시작일을 입력해주세요.");
                    //     $("#startDate").focus();
                    //     return false;
                    // }
                    // if($("#endDate").val() == ""){
                    //     alert("작업 마감일을 입력해주세요.");
                    //     $("#endDate").focus();
                    //     return false;
                    // }
                    $("#regForm").submit();
                });
        });
		</script>
	</head>
<body>
    <div class="mainWrap">
        <%@ include file="../include/header.jsp" %>
	    <div class="container">
	        <%@ include file="../include/sideMenu.jsp" %>
	        <div class="regFormContainer">
	            <h3>선수 등록</h3>
	            <form id="regForm" method="post" action="${contextPath}/registerPlayer.do" enctype="multipart/form-data">
	                <div class="content">
	                    <div class="content">
	                        <label for="playerNm">이름</label>
	                        <input type="text" id="playerNm" name="playerNm" required><br>
	                    </div>
	                </div>

                    <div class="content">
                        <div class="content team-select-container">
                            <label for="teamId">소속팀</label>
                                <select id="teamId" name="teamId" required>
									<option value="" disabled selected>소속팀을 선택하세요</option>
                                    <c:forEach var="item" items="${teamList}">
                                        <option value="${item.teamId}">${item.teamNm}</option>
                                    </c:forEach>
                                </select>
                        </div>
	                    <div class="content">
	                        <label for="position">포지션</label>
	                        <select id="position" name="position" required>
	                            <option value="GK">GK</option>
	                            <option value="DF">DF</option>
	                            <option value="MF">MF</option>
	                            <option value="FW">FW</option>
	                        </select>
	                    </div>
                    </div>

                    <div class="content">
                        <div class="content">
                            <label for="grade">학년</label>
                            <select id="grade" name="grade" required>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                            </select>
                        </div>
                        <div class="content">
                            <label for="uniformNum">배번</label>
                            <input type="number" id="uniformNum" name="uniformNum" required>
                        </div>
                    </div>

	                <div class="content file">
	                    <div class="content">
	                        <label for="playerFile">사진 등록</label>
	                        <input type="file" id="playerFile" name="playerFile" />
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