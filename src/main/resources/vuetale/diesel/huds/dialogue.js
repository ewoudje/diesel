import { randomInt as w } from "./util.js";
const m = {
  partner: {
    portrait: "prole",
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
    portrait: "partner",
    voice: "Scared"
  },
  sod: {
    portrait: "prole",
    voice: "Other"
  }
};
let h = !1, f = null;
function g({ text: l, displayTextRef: o, displayPortraitRef: a, speed: u, actor: t, chainObj: e, chainIndex: r, chainDelay: p, playSoundRef: d }) {
  console.log("test"), h && clearTimeout(f), h = !0, o.value = "";
  let v = l.replace(/"/g, "").split(" "), n = 0;
  c(v, o), console.log(t);
  function c(i, s) {
    n < i.length ? ((n + 2) % 2 == 0 && d.value(m[t].voice), s.value = `${s.value} ${i[n]}`, a.value = y(t), n++, f = setTimeout(() => {
      c(i, s);
    }, 100)) : (r++, r < e.length ? setTimeout(() => {
      g({
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
        displayPortraitRef: a,
        displayTextRef: o,
        playSoundRef: d
      });
    }, p) : setTimeout(() => {
      a.value = "Img/portrait/none.png", o.value = "";
    }, p));
  }
  function y(i) {
    return `Img/portrait/${m[i].portrait}${w(1, 3)}.png`;
  }
}
function U(l, o, a, u) {
  let t = T[l], e = 0;
  g({
    actor: t[e][0],
    text: t[e][1],
    speed: t[e][2],
    chainDelay: t[e][3],
    chainIndex: e,
    chainObj: t,
    displayPortraitRef: a,
    displayTextRef: o,
    playSoundRef: u
  });
}
const T = {
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
  planeAttackFred: [
    ["fred", "For the board!!!", 100, 500]
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
  U as playChain,
  g as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
