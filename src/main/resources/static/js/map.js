/**
 * 切换地图
 */

(function (window) {
    window.omap = window.omap || {};

    //地图核心对象集合
    omap.maps = omap.maps || {};

    //对元素事件进行绑定
    omap.maps.event = {};

    var mapperKey, //地图核心对象的名称
        mapKey, //地图key
        tempMaps; //当前使用地图api函数入口

    var _is = {
        BMapGL: false,
        googleMap: false,
        gdMap: false
    };

    if (typeof BMapGL != 'undefined') {
        _is.BMapGL = true;
        tempMaps = BMapGL;
        mapperKey = 'baidu';
    } else  if (typeof AMap != 'undefined') {
        _is.gdMap = true;
        tempMaps = AMap;
        mapperKey = 'gaode';
    } else if (typeof google != 'undefined' && google.maps) {
        _is.googleMap = true;
        tempMaps = google.maps;
        mapperKey = 'google';
    } else {
        window.onerror = function () {
            return true;
        };
        window.MAP_LOAD_ERROR = true;
        return;
    }

    var E = function (e) {//e只能是高德的对象
        e = window.event || e.domEvent || e;

        return {
            stop: function () {
                if (e && e.stopPropagation()) {
                    e.stopPropagation();
                }
                e.cancelBubble = true;
            },
            prevent: function () {
                if (e && e.preventDefault()) {
                    e.preventDefault();
                } else {
                    e.returnValue = false;
                }

            }
        }

    }

    omap.maps.event.addListener = function (object, name, handler) {
        var _self = object._source || object;
        if (_is.gdMap) {

            _self.addEventListener(name, function (e) {
                //e = e || window.event;
                if (e && e.point) {
                    e.latLng = new omap.maps.LatLag(e.point);
                }

                var r = handler.call(_self, e);

                if (r == false) {
                    E(e).stop();
                    E(e).prevent();

                }
                return;

            })
        } else {
            if (_is.googleMap) {

                google.maps.event.addListener(_self, name, function (e) {

                    if (e && e.latLng) {
                        e.latLng = new omap.maps.LatLng(e.latLng);
                    }
                    var r = handler.call(_self, e);

                    if (r == false) {
                        e.stop()
                    }
                })
            }

        }
        return;
    }


    //Map
    omap.maps.Map = function (lat, lng, level) {
        if (_is.gdMap) {
            var map = new AMap.Map('container', {
                zoom: 18,
                expandZoomRange: true,
                zooms: [3, 20]
            });
            //map.setCenter(new AMap.LngLat(lng,lat));
            //map.setZoom(level);
            map.setZoomAndCenter(level, new AMap.LngLat(lng, lat));

            AMap.plugin(['AMap.ToolBar', 'AMap.MapType'], function () {//异步加载插件
                map.addControl(new AMap.ToolBar());
                map.addControl(new AMap.MapType());

            });
            this._source = map;
            this.maptype = 1;
        } else if (_is.googleMap) {

            var latlng = new google.maps.LatLng(lat, lng);
            var myOptions = {
                zoom: level,
                center: latlng,
                gestureHandling: 'greedy',
                mapTypeControl: true,
                mapTypeId: google.maps.MapTypeId.ROADMAP

            };

            map = new google.maps.Map(document.getElementById("container"), myOptions);
            this._source = map;
            this.maptype = 0;

        }

    }
//var overlayers = [];
    //remove
    omap.maps.Map.prototype.remove = function (overlayers) {
        if (_is.googleMap) {

        }
        if (_is.gdMap) {
            this._source.remove(overlayers);
        }
    }

    //clearMap
    omap.maps.Map.prototype.clearMap = function () {

        if (_is.gdMap) {
            this._source.clearMap();
        }
        if (_is.googleMap) {

        }
    }

    //add
    omap.maps.Map.prototype.add = function (overlayers) {

        if (_is.gdMap) {
            this._source.add(overlayers);
        }
        if (_is.googleMap) {

        }
    }

    //getCenter
    omap.maps.Map.prototype.getCenter = function () {
        return new omap.maps.LatLng(this._source.getCenter())
    }

    omap.maps.Map.prototype.setCenter = function (lat, lng) { //ok

        if (arguments.length !== 1) {
            if (_is.gdMap) {

                this._source.setCenter(new AMap.LngLat(lng, lat));
            }
            if (_is.googleMap) {

                this._source.setCenter(new google.maps.LatLng(lat, lng));
            }
        } else {

            this._source.setCenter(lat._source || lat);
        }

    };

    //getZoom
    omap.maps.Map.prototype.getZoom = function () {
        return this._source.getZoom();
    }

    omap.maps.Map.prototype.setZoom = function (zoom) {
        return this._source.setZoom(zoom);
    }

    //setFitView
    omap.maps.Map.prototype.setFitView = function (overlayers) {

        if (_is.gdMap) {
            this._source.setFitView(overlayers);
        }
        if (_is.googleMap) {

        }
    }

    //Pixel
    omap.maps.Pixel = function (x, y) {
        if (arguments.length === 2) {
            if (_is.gdMap) {
                this._source = new AMap.Pixel(x, y);
            }
            if (_is.googleMap) {
                this._source = new google.maps.Point(x, y);
            }
        } else {
            this._source = x;
        }
        this.x = this._source.x;
        this.y = this._source.y;
    }

    //LngLat
    omap.maps.LngLat = function (lat, lng) {
        if (arguments.length !== 1) {
            if (_is.gdMap) {
                this._source = new AMap.LngLat(lng, lat);
            }
            if (_is.googleMap) {
                this._source = new google.maps.LatLng(lat, lng);
            }
        } else {
            this._source = lat;
        }
    }

    omap.maps.LngLat.prototype.offset = function (w, s) {
        if (arguments.length === 2) {
            if (_is.gdMap) {
                return this._source.offset(w, s);
            }
            if (_is.googleMap) {

            }
        }
    }

    omap.maps.LngLat.prototype.lat = function () {
        if (_is.gdMap) {
            return this._source.getLat();
        }
        if (_is.googleMap) {
            return this._source.lat();
        }
    }

    omap.maps.LngLat.prototype.lng = function () {
        if (_is.gdMap) {
            return this._source.getLng();
        }
        if (_is.googleMap) {
            return this._source.lng();
        }
    }

    omap.maps.LngLat.prototype.distance = function (lnglat) {
        if (_is.gdMap) {
            return this._source.distance(lnglat);
        }
        if (_is.googleMap) {

        }
    }

    omap.maps.Marker = function (opt) {
        var latlng = opt.latLng;
        var map = opt.map;
        var txt = opt.txt;
        var icon = opt.icon;
        var draggable = (typeof  opt.draggable != 'undefined') ? opt.draggable : false;
        var clickable = (typeof  opt.clickable != 'undefined') ? opt.clickable : false;

        this.latlng_ = latlng || {};

        if (_is.gdMap) {

            var marker = new AMap.Marker({
                map: map._source,
                position: latlng._source,
                icon: icon ? icon._source : icon,
                draggable: draggable,
                extra: opt.data

            });

            marker.setMap(map);
            marker.on("click", function (e) {
                console.log(e.target);
            });
        }

        if (_is.googleMap) {
            var marker = new google.maps.Marker({
                position: latlng._source,
                map: map._source,
                icon: icon ? icon._source : icon,
                clickable: clickable,
                draggable: draggable

            });
            this._source = marker;

            var _this = this;

            google.maps.event.addListener(marker, "click", function () {
                _this.on("click", function (e) {
                    console.log(e.target);
                });
            })
        }

        this.map_ = map;

    }

    omap.maps.Marker.prototype.setIcon = function (icon) {
        this._source.setIcon(icon._source);
    }

    omap.maps.Marker.prototype.getPosition = function () {
        var latlng = this._source.getPosition ? this._source.getPosition() : this._source.getLocation();
        return new omap.maps.LngLat(latlng);
    }

    omap.maps.Marker.prototype.setPosition = function (latlng) {
        this._source.setPosition ? this._source.setPosition(latlng._source || latlng) : this._source.setLocation(latlng._source || latlng);
    }


})(window);



function Gmap(mapId) {
    this.mapId = mapId; //地图对象ID
    this.mapTypeId = 'GoogleMap'; //地图类型ID
    this.maps = {}; //当前地图对象
}


let point = new BMapGL.Point(116.404, 39.915);

class Map {

}

Map.Point = function(x,y) {
    this.longitude = x;
    this.latitude = y
};


// 0： 代表百度地图 1：google地图 2：高德地图 3： 腾讯地图
map.maptype = 0;

switch (map.maptype) {
    case 0:
        map = new BMapGL.Map("container", {
            // mapType: BMAP_EARTH_MAP,
            coordsType: 3 // coordsType指定输入输出的坐标类型，3为gcj02坐标，5为bd0ll坐标，默认为5。
                          // 指定完成后API将以指定的坐标类型处理您传入的坐标
        });
}