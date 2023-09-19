var HOME_PATH = window.HOME_PATH || '.';

// 스타일을 정의하는 함수
function setMarkerContentStyle(element) {
    element.style.backgroundColor = '#f2f2f2';
    element.style.border = '1px solid #ccc';
    element.style.padding = '10px';
    element.style.borderRadius = '5px';
    element.style.fontSize = '16px';
    element.style.color = '#333';

    // 폰트 패밀리 설정
    element.style.fontFamily = 'Arial, sans-serif';
}

// content 내의 타이틀 스타일 설정 함수
function setMarkerTitleStyle(titleElement) {
    titleElement.style.fontSize = '18px';
    titleElement.style.fontWeight = 'bold';
    titleElement.style.marginBottom = '5px';
    titleElement.style.color = '#555';

    // 폰트 패밀리 설정
    titleElement.style.fontFamily = 'Arial, sans-serif';
}

// content 내의 내용 스타일 설정 함수
function setMarkerContentParagraphStyle(paragraphElement) {
    paragraphElement.style.margin = '0';
    paragraphElement.style.color = '#777';

    // 폰트 패밀리 설정
    paragraphElement.style.fontFamily = 'Arial, sans-serif';
}

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

// 마커를 담을 배열 생성
var markers = [];

// 현재 페이지의 URL을 가져오는 함수
function getCurrentPageURL() {
    return window.location.pathname;
}

// 현재 페이지의 URL을 기반으로 마커를 추가하는 함수
function addMarkersBasedOnURL() {
    var currentPageURL = getCurrentPageURL();

    // "/map/hospitel" 페이지에서 실행 중인 경우
    if (currentPageURL === "/map/hospitel") {
        var hospitalMarkerPositions = [
            {
                position: new naver.maps.LatLng(36.3494029, 127.3768244),
                title: '병원1',
                content: '병원1 정보'
            },
            // 추가할 다른 병원 마커들...
        ];

        // 병원 관련 마커 추가
        addMarkers(hospitalMarkerPositions);
    }

    // "/map/hotel" 페이지에서 실행 중인 경우
    else if (currentPageURL === "/map/hotel") {
        var hotelMarkerPositions = [
            {
                position: new naver.maps.LatLng(36.3515862, 127.3803119),
                title: '호텔1',
                content: '호텔1 정보'
            },
            // 추가할 다른 호텔 마커들...
        ];

        // 호텔 관련 마커 추가
        addMarkers(hotelMarkerPositions);
    }

    // "/map/accommodations" 페이지에서 실행 중인 경우
    else if (currentPageURL === "/map/accommodations") {
        var accommodationMarkerPositions = [
            {
                position: new naver.maps.LatLng(36.3024808, 127.3354944),
                title: '숙소1',
                content: '숙소1 정보'
            },
            // 추가할 다른 숙소 마커들...
        ];

        // 숙소 관련 마커 추가
        addMarkers(accommodationMarkerPositions);
    }
}

// 마커를 추가하는 함수
function addMarkers(markerPositions) {
    for (var i = 0; i < markerPositions.length; i++) {
        addMarker(
            markerPositions[i].position,
            markerPositions[i].title,
            markerPositions[i].content
        );
    }
}

// 마커들을 지도에 추가
addMarkersBasedOnURL();