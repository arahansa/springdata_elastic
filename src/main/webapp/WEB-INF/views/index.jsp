<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>안녕 엘라스틱?~</title>
<style>
	.members{
		width: 200px;
		height: 100px;
		float: left;
		border: 1px solid black;
		margin: 10px;
	}
	.members ul li{
		margin-bottom: 3px;
	}
	.body, .footer, .pages{
		display: block;
		width: 100%;
		float: left;
		margin-bottom: 5px;
	}
	.btn_del{
		cursor: pointer;
	}
	
</style>
</head>
<body>
<h3>안녕 스프링데이터엘라스틱</h3>

<form action="/member" method="post">
	아이디 :<input type="text" name="id"><br>
	이름 : <input type="text" name="name"><br>
	이상형 : 
	<select name="ideal">
		<option value="앤">앤</option>
		<option value="비비안수">비비안수</option>
		<option value="스칼렛">스칼렛</option>
		<option value="니콜라스">니콜라스</option>
		<option value="톰">톰</option>
		<option value="미니언">미니언</option>
	</select>
	<input type="submit" value="전송">
</form>

<div class="body">
<h3>사용자들</h3>
<hr>
<!-- 그냥 멤버들 전체로 받은 것 -->
<c:forEach items="${members }" var="member">
	<div class="members" id="${member.id }">
		<ul>
			<li>아이디 : ${member.id } <small class="btn_del">(삭제)</small></li>
			<li>이름 : ${member.name }</li>
			<li>이상형 : ${member.ideal }</li>
		</ul>
	</div>
</c:forEach>
<br>


<!-- 페이지들로 가보자 -->
<c:forEach items="${pages.content }" var="member">
	<div class="members">
		<ul>
			<li>아이디 : ${member.id }</li>
			<li>이름 : ${member.name }</li>
			<li>이상형 : ${member.ideal }</li>
		</ul>
	</div>
</c:forEach>
<div class="pages">
<c:if test="${pages.hasPrevious () }"><a href="${pages.number  }">이전페이지</a></c:if>
<c:if test="${pages.hasNext() }"><a href="${pages.number + 2 }">다음페이지</a></c:if>
<br>
</div>

<!-- 통계 -->
<c:forEach items="${terms }" var="term">
	<div class="members">
		<ul>
			<li>이상형 : ${term.term } </li>
			<li>카운트 :  ${ term.count }</li>
		</ul>
	</div>
</c:forEach>
</div>



<hr>
<div class="footer">
<h3>인원보기</h3>
<a href="/members">전체인원!</a>
<a href="/members/1">1페이지부터~?</a>
<h3>캐릭터별 이상형 고른사람 보기</h3>
<select onchange="if(this.value) location.href=(this.value);">
<option value="">선택하세요.</option>
<option value="/membersideal?ideal=비비안수">비비안수</option>
<option value="/membersideal?ideal=앤">앤</option>
<option value="/membersideal?ideal=스칼렛">스칼렛</option>
<option value="/membersideal?ideal=니콜라스">니콜라스</option>
<option value="/membersideal?ideal=톰">톰</option>
<option value="/membersideal?ideal=미니언">미니언</option>
</select>

<h3>캐릭터별 통계(페이셋) 보기</h3>
<a href="/memberterms/ideal"> 이상형 타입 통계로 가자! (나중에 다른 필드를 넣어도 확장이 가능하다. )</a>
<h3>다음 스테이지</h3>
	<a href="/stage/2">전문검색</a>
</div>

<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script>
$(function() {
    $('body').on('click','.btn_del',function(){
        if(confirm("회원을 삭제 하시겠습니까?")){
            var num = $(this).parent().parent().parent()[0].id;
            var obj = $(this).parent().parent().parent();
            ajax_delete(num, obj);
        }else{
            return;
        }
    });
});
function ajax_delete(num, obj){
    $.ajax({
        url: "/member/" + num,
        type: 'DELETE',
        success: function(result) {
            if(result=="OK"){
                obj.remove();
            }else{
                alert("삭제 에러");
            }
        },
        error:function(request,status,error){
            alert('무슨 일인지 모르겠지만 삭제실패하였습니다 ㅠㅠ');
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}
</script>

</body>
</html>