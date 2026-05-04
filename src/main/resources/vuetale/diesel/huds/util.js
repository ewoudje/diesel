function M(t, c) {
  var f = !1;
  t[0] == "#" && (t = t.slice(1), f = !0);
  var i = parseInt(t, 16), n = (i >> 16) + c;
  n > 255 ? n = 255 : n < 0 && (n = 0);
  var r = (i >> 8 & 255) + c;
  r > 255 ? r = 255 : r < 0 && (r = 0);
  var a = (i & 255) + c;
  return a > 255 ? a = 255 : a < 0 && (a = 0), (f ? "#" : "") + (a | r << 8 | n << 16).toString(16).padStart(6, "0");
}
function k(t, c) {
  const f = parseInt(t.slice(1, 3), 16) / 255, i = parseInt(t.slice(3, 5), 16) / 255, n = parseInt(t.slice(5, 7), 16) / 255, r = Math.max(f, i, n), a = Math.min(f, i, n), u = (r + a) / 2;
  let s, h;
  if (r === a)
    s = h = 0;
  else {
    const e = r - a;
    switch (h = u > 0.5 ? e / (2 - r - a) : e / (r + a), r) {
      case f:
        s = ((i - n) / e + (i < n ? 6 : 0)) / 6;
        break;
      case i:
        s = ((n - f) / e + 2) / 6;
        break;
      case n:
        s = ((f - i) / e + 4) / 6;
        break;
    }
  }
  s = (s + c / 360) % 1, s < 0 && (s += 1);
  function b(e, l, o) {
    return o < 0 && (o += 1), o > 1 && (o -= 1), o < 1 / 6 ? e + (l - e) * 6 * o : o < 1 / 2 ? l : o < 2 / 3 ? e + (l - e) * (2 / 3 - o) * 6 : e;
  }
  let d, g, v;
  if (h === 0)
    d = g = v = u;
  else {
    const e = u < 0.5 ? u * (1 + h) : u + h - u * h, l = 2 * u - e;
    d = b(l, e, s + 1 / 3), g = b(l, e, s), v = b(l, e, s - 1 / 3);
  }
  const I = (e) => Math.round(e * 255).toString(16).padStart(2, "0");
  return `#${I(d)}${I(g)}${I(v)}`;
}
function m(t, c) {
  return Math.floor(Math.random() * (c - t + 1) + t);
}
export {
  M as LightenDarkenColor,
  k as hueRotateHex,
  m as randomInt
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoidXRpbC5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
