/**
 *   地图引擎：
 *       需求：拿到 ->地图引擎名<-，去匹配对应的引擎,并加载初始化地图
 *            返回 ->对应的地图引擎<- 提供给MapHelper.js用于初始化
 *
 */

//默认地图引擎，由系统设置提供
const defaultMapType = 0;

//

//可支持的地图引擎
const MapEngineList = {
    0: '百度地图',
    1: '谷歌地图',
    2: '高德地图',
    3: '腾讯地图'
};

//起始坐标点,外部参数，(坐标定位暂时不处理，需要根据算法重新换算)
const defaulePoint = {
    x: 0,
    y: 0
};


//加载默认地图
LoadMap(defaultMapType);

//绑定地图切换事件
document.getElementById('MapEngineList').onchange = MapChange;

//地图切换函数
//MapType -> 0: '百度地图', 1: '谷歌地图', 2: '高德地图', 3: '腾讯地图'
function mapChange(MapType) {
    switch (MapType) {
        //百度地图
        case 0:
            loadScript('//api.map.baidu.com/api?type=webgl&v=1.0&ak=***', function () {
                removeMapArea();
                console.log('Here地图加载OK');
                let map = new BMapGL.Map("container", {
                    // mapType: BMAP_EARTH_MAP,
                    coordsType: 3 // coordsType指定输入输出的坐标类型，3为gcj02坐标，5为bd0ll坐标，默认为5。
                                  // 指定完成后API将以指定的坐标类型处理您传入的坐标
                });          // 创建地图实例
                // 创建地图实例
                let point = new BMapGL.Point(116.404, 39.915);
                // 创建点坐标
                map.centerAndZoom(point, 15);
                map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
                // 初始化地图，设置中心点坐标和地图级别

                map.addControl(new BMapGL.NavigationControl3D());   // 添加3D控件
                map.addControl(new BMapGL.ScaleControl());  // 添加比例尺控件
                map.addControl(new BMapGL.ZoomControl());   // 添加缩放控件
                map.addControl(new BMapGL.CityListControl());   // 添加城市列表控件

                let locationControl = new BMapGL.LocationControl({
                    // 控件的停靠位置（可选，默认左上角）
                    anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
                    // 控件基于停靠位置的偏移量（可选）
                    offset: new BMapGL.Size(10, 180)
                });
                // 将控件添加到地图上
                map.addControl(locationControl);

            });
            break;
        //Google Map
        case 1:
            loadScript('https://maps.googleapis.com/maps/api/js?language=zh-CN&region=cn&key=******&libraries=drawing,geometry', function () {
                removeMapArea();
                console.log('Google Map加载OK');
                let map = new google.maps.Map(document.getElementById('container'), {
                    center: new google.maps.LatLng(32.07754, 118.770559),
                    // center: new google.maps.LatLng(center.lat, center.lng),
                    zoom: 17,
                    mapTypeId: "hybrid",
                    scaleControl: true,
                    streetViewControl: false,
                    rotateControl: true,
                });

            });
            break;
        default:
            console.log('error');
            break;
    }
}

//根据参数加载对应地图()
//MapType -> 0: '百度地图', 1: '谷歌地图', 2: '高德地图', 3: '腾讯地图'
function LoadMap(MapType) {
    mapChange(MapType);
}

//url -> String 资源加载地址
function loadScript(url, callback) {
    let script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;
    document.head.appendChild(script);
}

//清空地图容器，避免重复渲染
function removeMapArea(containId) {
    let MapEngineConstant = document.getElementById(`#${containId}`);
    let childNodes = MapEngineConstant.childNodes;
    for (let i = childNodes.length - 1; i >= 0; i--) {
        MapEngineConstant.removeChild(childNodes[i]);
    }
}