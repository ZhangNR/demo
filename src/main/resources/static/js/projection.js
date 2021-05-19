var wa = 256 / 360, xa = 256 / (2 * Math.PI);
K.prototype.fromLatLngToPixel = function (a, b) {
  var c = 128 + a.lng() * wa;
  a = Math.sin(a.lat() * (Math.PI / 180));
  a = Math.max(a, -.9999);
  a = Math.min(a, .9999);
  a = 128 + .5 * Math.log((1 + a) / (1 - a)) * -xa;
  b = 1 << b;
  return new y(B(c * b), B(a * b))
};
K.prototype.fromPixelToLatLng = function (a, b, c) {
  b = 1 << b;
  return new J((2 * Math.atan(Math.exp((a.y / b - 128) / -xa)) - Math.PI / 2) / (Math.PI / 180), (a.x / b - 128) / wa, c)
};

var J = function (a, b, c) {
  if (a && (void 0 !== a.lat || void 0 !== a.lng)) {
    Hd(a), b = a.lng, a = a.lat, c = !1
  }

  a -= 0;
  b -= 0;
  c || (a = _.Uc(a, -90, 90), 180 != b && (b = _.Vc(b, -180, 180)));
  this.lat = function () {
    return a
  };
  this.lng = function () {
    return b
  }
};
J.prototype.latRadians = function () {
  return this.lat() / 180 * Math.PI
};
J.prototype.lngRadians = function () {
  return this.lng() / 180 * Math.PI
};
J.prototype.distanceFrom = function (a, b) {
  if (!v3.geometry) throw"You need to load the Maps API with the geometry library. See http://goo.gl/FDd7W for details.";
  return v3.geometry.spherical.computeDistanceBetween(this, a, b)
};
J.fromUrlValue = function (a) {
  a = a.split(",");
  return new J(parseFloat(a[0]), parseFloat(a[1]))
};