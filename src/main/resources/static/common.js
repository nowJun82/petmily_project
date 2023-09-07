$(document).ready(function () {
    //GNB
    function menuListSize(){
        var totalWidth = 0
        $('nav dl').each(function(){
            totalWidth = Math.ceil(totalWidth + $(this).width());
        });
        $('nav .menuList').css('width', totalWidth + 10);
    };
    menuListSize();
    $('nav > div').hover(function () {
        var headerHT = $('header').outerHeight();
        $('nav').stop().animate({ 'height': headerHT+341 })
        $('header .menuBG').stop().fadeIn();
    }, function () {
        var headerHT = $('header').outerHeight();
        $('nav').stop().animate({ 'height': headerHT })
        $('header .menuBG').stop().fadeOut();
    })
    $('nav dl').hover(function () {
        $('nav dt a').css('color' , '#767676')
        $(this).find('dt a').addClass('on');
    }, function () {
        if ($('#container').hasClass('sub')) {
            $('nav dt a').css('color', '#767676')
            $(this).find('dt a').removeClass('on');
        } else {
            $('nav dt a').css('color', '#101010')
            $(this).find('dt a').removeClass('on');
        }
    });
    //Moblie GNB
    function mobileMenuTags() {
        var desktopMenuTags = $('nav .menuList').html();
        $('header .mobileGNB .moMenuList').prepend(desktopMenuTags);
    };
    mobileMenuTags();
    function mobileMenuSize() {
        var windowHT = $(window).height();
        var moMenuCrrlHT = $('header .mobileGNB .ctrl').outerHeight();
        var moMenuLangHT = $('header .mobileGNB .lang').outerHeight();
        $('header .mobileGNB .moMenuList').css('height', windowHT - moMenuCrrlHT - moMenuLangHT)
    };
    mobileMenuSize();
    $('header a.moMenuopen').click(function () {
        $('header .mobileGNB').stop().animate({ 'right': 0 });
        $('header .menuBG').stop().fadeIn();
        return false;
    });
    $('header .mobileGNB .ctrl a.menuClose , header .menuBG').click(function () {
        $('header .mobileGNB').stop().animate({ 'right': '-100%' });
        $('header .menuBG').stop().fadeOut();
        $('header .mobileGNB dt a').removeClass('on');
        $('header .mobileGNB dd').slideUp();
        return false;
    });
    $('header .mobileGNB dt a').click(function () {
        if ($(this).hasClass('on')) {
            $(this).removeClass('on');
            $('header .mobileGNB dd').slideUp();
        } else {
            $('header .mobileGNB dt a').removeClass('on');
            $('header .mobileGNB dd').slideUp();
            $(this).addClass('on');
            $(this).parent('dt').next('dd').slideDown();
        }
        return false;
    });
    //Container
    function containerSize() {
        var headerHT = $('header').outerHeight();
        $('#container').css('padding-top', headerHT);
    };
    containerSize();
    $(window).resize(function () {
        containerSize();
        menuListSize();
        mobileMenuSize();
    });
    //Go Top
    $('footer a.goTop').click(function () {
        $('html,body').stop().animate({ scrollTop: 0 });
        return false;
    });
    //Popup
    function popupZindex() {
        var popupDataEA = $('.popupWrap .popupData').length;
        for (var i = 0; i < popupDataEA; i++) {
            $('.popupWrap .popupData').eq(i).css('z-index' , i + 10)
        }
    }
    popupZindex();
    /*
    function popupDataSize() {
        if ($(window).width() <= 812) {
            var windowHT = $(window).height();
            $('.popupWrap .imgArea a img').css('height', windowHT / 2)
        } else {
            $('.popupWrap .imgArea a img').css('height', 'auto')
        }
    }
    popupDataSize();
    $(window).resize(function () {
        if ($(window).width() <= 812) {
            var windowHT = $(window).height();
            $('.popupWrap .imgArea a img').css('height', windowHT / 2)
        } else {
            $('.popupWrap .imgArea a img').css('height', 'auto')
        }
    });
    */
});