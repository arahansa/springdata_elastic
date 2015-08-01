<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>스테이지 3</title>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
</head>
<body>
<h3>스테이지 3.</h3> 

당신은 무더위로 인해 기력이 떨어졌습니다.<br>
 갈 수있는 곳은 시원한 지하철을 타고 갈 수있는 곳밖에 없습니다. <br>
 지하철 냉방칸에서 이상형의 옆자리에 앉아 피서를 즐기세요(...)<br>
 (사실.. 제가 더 이상은 코드짜기가..지쳤....;; )<br>
 아래의 선택지 중에서 한 군데를 골르세요 
<Br>
<a href="/stage/3/ferum"><button>페럼타워</button></a>
<a href="/stage/3/seoul"><button>서울역</button></a>
<a href="/stage/3/incheon"><button>인천역</button></a>
<a href="/stage/3/gangnam"><button>강남역</button></a>
<a href="/stage/3/shinchon"><button>신촌역</button></a>
<a href="/stage/3/yongsan"><button>용산역</button></a>


<hr>
<!-- 그냥 멤버들 전체로 받은 것 -->


<Br>
 <c:forEach items="${people }" var="member">
 <img src="/img/${member.img }">
	<div class="members" id="${member.id }">
		<ul>
			<li>당신은 ${member.location.lat }, ${member.location.lon }에서   ${member.name } 를 만났습니다!!</li>
			
			<li>${member.message }</li>
		</ul>
	</div>
</c:forEach>


<div id="map-canvas" style="width: 100%; height: 400px"></div>



<%-- <c:choose>
	<c:when test="${not empty people }">
		${people[0].location.lat }
		${people[0].location.lon }
	</c:when>	
	<c:otherwise>
		아님..
	</c:otherwise>
</c:choose>
<c:if test="${not empty people }">
	${people[0].location.lat }
	${people[0].location.lon }
</c:if> --%>


<!-- 참고주소 http://zero-gravity.tistory.com/151 -->
<!-- GoogoleMap Asynchronously Loading the API ********************************************* -->
<script type="text/javascript">
      function initialize() {

    	  <c:choose>
    	  	<c:when test="${not empty people }">
    	  	 var mapLocation = new google.maps.LatLng('${people[0].location.lat }', '${people[0].location.lon }'); // 지도에서 가운데로 위치할 위도와 경도
    	        var markLocation= new google.maps.LatLng('${people[0].location.lat }', '${people[0].location.lon }'); // 마커가 위치할 위도와 경도
    	  		${people[0].location.lat }
    	  		${people[0].location.lon }
    	  	</c:when>	
    	  	<c:otherwise>
    	  		 var mapLocation = new google.maps.LatLng('37.569787', '127.005751'); // 지도에서 가운데로 위치할 위도와 경도
    	        var markLocation= new google.maps.LatLng('37.569787', '127.005751'); // 마커가 위치할 위도와 경도
    	  	</c:otherwise>
    	  </c:choose>	  
    	  
       
         
        var mapOptions = {
          center: mapLocation, // 지도에서 가운데로 위치할 위도와 경도(변수)
          zoom: 18, // 지도 zoom단계
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"), // id: map-canvas, body에 있는 div태그의 id와 같아야 함
            mapOptions);
         
        var size_x = 60; // 마커로 사용할 이미지의 가로 크기
        var size_y = 60; // 마커로 사용할 이미지의 세로 크기
         
        // 마커로 사용할 이미지 주소
        var image = new google.maps.MarkerImage( 'http://www.larva.re.kr/home/img/boximage3.png',
                            new google.maps.Size(size_x, size_y),
                            '',
                            '',
                            new google.maps.Size(size_x, size_y));
         
        var marker;
        marker = new google.maps.Marker({
               position: markLocation, // 마커가 위치할 위도와 경도(변수)
               map: map,
               icon: image, // 마커로 사용할 이미지(변수)
//             info: '말풍선 안에 들어갈 내용',
               title: '청계천에서 쉬고 싶다..~' // 마커에 마우스 포인트를 갖다댔을 때 뜨는 타이틀
        });
         
        var content = "청계천에서 쉬고 싶다.. <br/> 하아~ "; // 말풍선 안에 들어갈 내용
         
        // 마커를 클릭했을 때의 이벤트. 말풍선 뿅~
        var infowindow = new google.maps.InfoWindow({ content: content});
 
        google.maps.event.addListener(marker, "click", function() {
            infowindow.open(map,marker);
        });
         
 
         
      }
      google.maps.event.addDomListener(window, 'load', initialize);
</script>




</body>
</html>