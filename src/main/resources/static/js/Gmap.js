(function (window) {
    window.omap = window.omap || {};

    //地图核心对象集合
    omap.maps = omap.maps || {};

    //对元素事件进行绑定
    omap.maps.event = {};

    /**
     * 0 百度地图 1 谷歌地图 3 高德地图 4 腾讯地图 ...
     * @type {number}
     */
    window.mapType = 0;

    switch (mapType) {
        case 0:
            loadBaiduMap();
            break;
        case 1:
            loadGoogleMap();
            break;
        default:
            loadBaiduMap();
    }
})(window);

function loadGoogleMap() {
    document.write('<script src="https://maps.googleapis.com/maps/api/js?language=zh-CN&region=cn&key=********&libraries=drawing,geometry"></script>');
}

function loadBaiduMap() {
    document.write('<script src="//api.map.baidu.com/api?type=webgl&v=1.0&ak=************"></script>');
}


/**
 *
 * @param lng
 * @param lat
 * @constructor
 */
omap.maps.LngLat = function (lng, lat) {
    this.maps.lat = lat;
    this.maps.lng = lng;

    switch (mapType) {
        case 0:
            new BMapGL.Point(lng, lat);
            break;
        case 1:
            new google.maps.LatLng(lat, lng);

    }
};