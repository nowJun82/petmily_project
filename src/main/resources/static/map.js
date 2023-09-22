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
    },
    {
        position: new naver.maps.LatLng(36.3515862, 127.3803119), // 24시 타임 동물 메디컬 센터
        title: '동물병원',
        content: '<a href="https://naver.me/5xlngDzG" target="_blank">[병원] 24시 타임 동물 메디컬센터</a>'
    },
    {
        position: new naver.maps.LatLng(36.345495, 127.3817273), // 24시 아프리카 동물 메디컬 센터
        title: '동물병원',
        content: '<a href="https://naver.me/FuVEOCyV" target="_blank">[병원] 24시 아프리카 동물 메디컬센터</a>'
    },
    {
        position: new naver.maps.LatLng(36.348726, 127.3879455), // 닥터코기동물병원
        title: '동물병원',
        content: '<a href="https://naver.me/FrKu70vd" target="_blank">[병원] 닥터코기동물병원</a>'
    },
    {
        position: new naver.maps.LatLng(36.3472991, 127.3880061), // 쿨펫동물병원
        title: '동물병원',
        content: '<a href="https://naver.me/F8Kbv0UE" target="_blank">[병원] 쿨펫동물병원</a>'
    },
];

// 정보 창 스타일을 커스터마이징할 옵션 설정
var infoWindowOptions = {
    content: '', // 스타일을 커스터마이징하기 위해 빈 문자열로 설정
    borderWidth: 1, // 테두리 두께
    fontFamily: 'Arial !important', // CSS 속성에 따라 변경
    fontSize: '18px !important', // CSS 속성에 따라 변경
    backgroundColor: '#FFFFEB !important', // 배경색을 투명으로 설정
    disableAnchor: true // 기본 앵커를 비활성화
};

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