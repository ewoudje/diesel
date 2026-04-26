import { randomInt as d } from "./util.js";
const v = {
  tall_devil: {
    portrait: "partner",
    voice: "dutch"
  },
  small_devil: {
    portrait: "ilduce",
    voice: "french"
  },
  smaller_devil: {
    portrait: "nilduce",
    voice: "sadfrench"
  },
  fred: {
    portrait: "fred",
    voice: "dutch"
  },
  sod: {
    portrait: "prole",
    voice: "dutch"
  }
};
function s({ text: a, displayTextRef: o, displayPortraitRef: c, duration: f, actor: i, splitBy: n }) {
  o.value = "";
  let p = a.replace(/"/g, "").split(n), t = 0;
  l(p, o), console.log(i);
  function l(r, e) {
    t < r.length && (e.value = `${e.value} ${r[t]}`, c.value = u(i), t++, setTimeout(() => {
      l(r, e);
    }, 100));
  }
  function u(r) {
    return `Img/portrait/${v[r].portrait}${d(1, 3)}.png`;
  }
}
export {
  s as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
