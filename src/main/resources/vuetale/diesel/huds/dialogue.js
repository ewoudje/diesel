import { randomInt as v } from "./util.js";
const p = {
  partner: {
    portrait: "partner",
    voice: "Other"
  },
  ilduce: {
    portrait: "ilduce",
    voice: "Upset"
  },
  nilduce: {
    portrait: "nilduce",
    voice: "Scared"
  },
  fred: {
    portrait: "fred",
    voice: "Other"
  },
  sod: {
    portrait: "prole",
    voice: "Other"
  }
};
let d = !1, g = null;
function c({ text: i, displayTextRef: a, displayPortraitRef: o, speed: l, actor: t, chainObj: e, chainIndex: r, chainDelay: m, playSoundRef: u }) {
  d && clearTimeout(g), d = !0, a.value = "", h(i, a);
  function h(n, s) {
    u.value(p[t].voice, e), s.value = `${s.value} ${n}`, o.value = f(t), r++, r < e.length ? setTimeout(() => {
      c({
        //@ts-ignore //lol
        actor: e[r][0],
        //@ts-ignore //lmao
        text: e[r][1],
        //@ts-ignore //kek
        speed: e[r][2],
        //@ts-ignore // )00))))00)
        chainDelay: e[r][3],
        //@ts-ignore //wxnstunxwfqsunwxfqus
        chainIndex: r,
        chainObj: e,
        displayPortraitRef: o,
        displayTextRef: a,
        playSoundRef: u
      });
    }, m) : setTimeout(() => {
      o.value = "Img/portrait/none.png", a.value = "";
    }, 1e3);
  }
  function f(n) {
    return `Img/portrait/${p[n].portrait}${v(1, 3)}.png`;
  }
}
function k(i, a, o, l) {
  let t = y[i], e = 0;
  c({
    actor: t[e][0],
    text: t[e][1],
    speed: t[e][2],
    chainDelay: t[e][3],
    chainIndex: e,
    chainObj: t,
    displayPortraitRef: o,
    displayTextRef: a,
    playSoundRef: l
  });
}
const y = {
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
  c as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
