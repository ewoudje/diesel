import { randomInt as k } from "./util.js";
const f = {
  partner: {
    portrait: "partner",
    voice: "Afrenchevil"
  },
  bilduce: {
    portrait: "prole",
    voice: "Chairmanangry"
  },
  ilduce: {
    portrait: "prole",
    voice: "Charimancalm"
  },
  nilduce: {
    portrait: "prole",
    voice: "Chairmancared"
  },
  fred: {
    portrait: "fred",
    voice: "Fredcalm"
  },
  fredfighter: {
    portrait: "fred",
    voice: "Fredangry"
  },
  sod: {
    portrait: "prole",
    voice: "Other"
  }
};
let h = !1, g = null;
function v({ text: o, displayTextRef: r, displayPortraitRef: a, speed: s, actor: n, chainObj: t, chainIndex: e, chainDelay: u, playSoundRef: d, setLogicRef: y, chainName: c }) {
  h && clearTimeout(g), h = !0, r.value = "";
  let w = o.replace(/"/g, "").split(" "), l = 0;
  m(w, r);
  function m(i, p) {
    l < i.length ? ((l + 2) % 2 == 0 && d.value(f[n].voice), p.value = `${p.value} ${i[l]}`, a.value = D(n), l++, g = setTimeout(() => {
      m(i, p);
    }, 100)) : (e++, e < t.length ? setTimeout(() => {
      v({
        //@ts-ignore //lol
        actor: t[e][0],
        //@ts-ignore //lmao
        text: t[e][1],
        //@ts-ignore //kek
        speed: t[e][2],
        //@ts-ignore // )00))))00)
        chainDelay: t[e][3],
        //@ts-ignore //wxnstunxwfqsunwxfqus
        chainIndex: e,
        chainObj: t,
        displayPortraitRef: a,
        displayTextRef: r,
        playSoundRef: d
      });
    }, u) : setTimeout(() => {
      console.log(`[DIESELHUD] Chain ${c} complete`), y.value(c, "Done"), a.value = "Img/portrait/none.png", r.value = "";
    }, u));
  }
  function D(i) {
    return `Img/portrait/${f[i].portrait}${k(1, 3)}.png`;
  }
}
function $(o, r, a, s, n) {
  let t = C[o], e = 0;
  console.log(`[DIESELHUD] Starting chain ${o}`), v({
    actor: t[e][0],
    text: t[e][1],
    speed: t[e][2],
    chainDelay: t[e][3],
    chainIndex: e,
    chainObj: t,
    displayPortraitRef: a,
    displayTextRef: r,
    playSoundRef: s,
    setLogicRef: n,
    chainName: o
  });
}
const C = {
  testchain: [
    ["sod", "i'm walkin' ere!", 100, 1e3],
    ["sod", "you gotta watch where you're going, pal", 100, 1e3],
    ["sod", "jeez. god", 100, 1e3],
    ["sod", "state of this city, i'm tellin' ya", 100, 1e3],
    ["sod", "you'd think we was Chasm Cataluña or somethin'", 100, 1e3]
  ],
  planeDownFred: [
    ["fred", "OUURUUGH!!!!", 100, 500]
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
  $ as playChain,
  v as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
