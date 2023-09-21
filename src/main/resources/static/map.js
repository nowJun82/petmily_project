var HOME_PATH = window.HOME_PATH || '.';

// 설정한 위도와 경도, 줌 레벨에 맞는 위치를 센터로 잡아서 제공
var map = new naver.maps.Map('map', {
    center: new naver.maps.LatLng(36.3494029, 127.3768244),
    zoom: 15,
    zoomControl: true,
    zoomControlOptions: {
        style: naver.maps.ZoomControlStyle.SMALL,
        position: naver.maps.Position.TOP_RIGHT
    }
});

// 마커들의 위치 정보를 배열로 정의
var markerPositions = [
    {
        position: new naver.maps.LatLng(36.3494029, 127.3768244), // 학원
        title: '학원',
        content: '<a href="https://naver.me/Gvkd2ar9" target="_blank">[현위치] SBS아카데미 컴퓨터 학원</a>'
    },
    {
        position: new naver.maps.LatLng(36.3024808, 127.3354944), // 웰케어 동물병원
        title: '동물병원',
        content: '<a href="https://naver.me/xDsj3W1B" target="_blank">[병원] 웰케어 동물병원</a>'
    }
];

// 마커를 담을 배열 생성
var markers = [];

// 마커를 생성하고 지도에 추가하는 함수
function addMarker(position, title, content) {
    var marker = new naver.maps.Marker({
        position: position,
        map: map,
        title: title
    });

    var infoWindow = new naver.maps.InfoWindow({
        content: content,
        autoClose: false // 정보창을 자동으로 닫지 않도록 설정
    });

    naver.maps.Event.addListener(marker, 'click', function() {
        if (infoWindow.getMap()) {
            infoWindow.close();
        } else {
            infoWindow.open(map, marker);
        }
    });

    markers.push(marker);
}

// 마커들을 지도에 추가
for (var i = 0; i < markerPositions.length; i++) {
    addMarker(
        markerPositions[i].position,
        markerPositions[i].title,
        markerPositions[i].content
    );
}