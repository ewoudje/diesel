import { randomInt as v } from "./util.js";
const y = {
  partner: {
    portrait: "partner",
    voice: "other"
  },
  ilduce: {
    portrait: "ilduce",
    voice: "upset"
  },
  nilduce: {
    portrait: "nilduce",
    voice: "scared"
  },
  fred: {
    portrait: "fred",
    voice: "other"
  },
  sod: {
    portrait: "prole",
    voice: "other"
  }
};
let c = !1, m = null;
function h({ text: l, displayTextRef: r, displayPortraitRef: i, speed: o, actor: e, chainObj: a, chainIndex: t, chainDelay: p }) {
  c && clearTimeout(m), c = !0, r.value = "";
  let f = l.replace(/"/g, "").split(" "), s = 0;
  d(f, r), console.log(e);
  function d(n, u) {
    s < n.length ? (u.value = `${u.value} ${n[s]}`, i.value = g(e), s++, m = setTimeout(() => {
      d(n, u);
    }, 100)) : (t++, t < a.length ? setTimeout(() => {
      h({
        //@ts-ignore //lol
        actor: a[t][0],
        //@ts-ignore //lmao
        text: a[t][1],
        //@ts-ignore //kek
        speed: a[t][2],
        //@ts-ignore // )00))))00)
        chainDelay: a[t][3],
        //@ts-ignore //wxnstunxwfqsunwxfqus
        chainIndex: t,
        chainObj: a,
        displayPortraitRef: i,
        displayTextRef: r
      });
    }, p) : setTimeout(() => {
      i.value = "Img/portrait/none.png", r.value = "";
    }, p));
  }
  function g(n) {
    return `Img/portrait/${y[n].portrait}${v(1, 3)}.png`;
  }
}
function k(l, r, i) {
  let o = w[l], e = 0;
  h({
    actor: o[e][0],
    text: o[e][1],
    speed: o[e][2],
    chainDelay: o[e][3],
    chainIndex: e,
    chainObj: o,
    displayPortraitRef: i,
    displayTextRef: r
  });
}
const w = {
  testchain: [
    ["sod", "i'm walkin' ere!", 100, 1e3],
    ["sod", "you gotta watch where you're going, pal", 100, 1e3],
    ["sod", "jeez. god", 100, 1e3],
    ["sod", "state of this city, i'm tellin' ya", 100, 1e3],
    ["sod", "you'd think we was Chasm Cataluña or somethin'", 100, 1e3]
  ],
  planeDownFred: [
    ["fred", "OUURUUGH!!!!", 100, 500],
    ["fred", "RIGHT IN THE SPOPPEGT", 100, 1e3]
  ],
  dukat: [
    ["partner", "Attention, Wallonian workers.", 100, 1e3],
    ["partner", "It has come to my attention that a handful of layabouts have...", 100, 1500],
    ["partner", "...undertaken an unfortunate exercise.", 500, 1e3],
    ["partner", "I implore you reconsider.", 200, 1e3],
    ["partner", "It is a matter of the city's productivity: remember this;", 100, 1e3],
    ["partner", "A productive Wallonia is a unified Wallonia is a safe Wallonia.", 100, 1e3],
    ["partner", "This in mind...", 100, 2e3],
    ["partner", "Any workers who aid in returning the dissidents", 100, 1e3],
    ["partner", "to their senses will be...", 100, 1500],
    ["partner", "...appropriately rewarded.", 100, 3e3],
    ["fred", "fie.", 100, 2e3],
    ["fred", "inconvenient, but i pay more than the board", 100, 1e3],
    ["fred", "how do they say it in the academiate?", 100, 3e3],
    ["fred", "ah, yes, I recall.", 200, 1500],
    ["fred", "go get 'em, tiger", 200, 3e3]
  ]
};
export {
  k as playChain,
  h as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OzsifQ==
