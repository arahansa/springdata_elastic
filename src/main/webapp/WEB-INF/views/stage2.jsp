<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>당신은 이제...</title>
<style type="text/css">
	.stage2{
		width:45%;
		line-height: 20px;
		margin: 0 auto;
	}
	em{
		color: black;
		background-color: yellow;
	}
</style>
</head>

<body>

<h3>가상의 시나리오..</h3> 

<br>오늘의 세미나를 열심히 경청한 당신에게 하루. 원하는 곳으로 떠나갈 기회가 주어졌다. <br>
그 원하는 곳에서는 당신이 선택한 이상형이 휴가를 즐길지도 모른다.<br> 
하지만 그 전에 문제가 당신은 이 키워드에서 하나의 단서를 찾아 입력을 해야 한다..

어떻게 찾을까? 키워드 : 9788960773431

<form action="/stage/2with" method="get">
	검색 키워드 : <input type="text" name="keyword" >
	<input type="submit" value="검색해보자!">
</form>
<form action="stage/2check" method="get">
	결과 키워드 : <input type="text" name="keyword" id="answer">
	<input type="button" value="정답은...." onclick="isAnswer()">
</form>

<hr>
<c:forEach items="${stage2page.content }" var="stage2">
검색결과 : ${stage2.hilightedContent }
</c:forEach>
<div class="stage2">${stage2.content }</div>

<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script>
function isAnswer(){
	if($("#answer").val()=="토비의스프링"){
		alert("정답입니다.");
		location.href="/stage/3";
	}else{
		alert("정답이 아닙니다.");
	}
}

</script>
</body>
</html>