import { randomInt as E } from "./util.js";
const l = {
  partner: {
    portraits: 3,
    portrait: "partner",
    interval: 4,
    voice: "Afrenchevil"
  },
  evilduce: {
    portraits: 4,
    portrait: "duce",
    voice: "Chairmancalm"
  },
  ilduce: {
    portraits: 3,
    portrait: "nilduce",
    voice: "Chairmanupset"
  },
  nilduce: {
    portraits: 3,
    portrait: "nilduce",
    voice: "Chairmanscared"
  },
  fredcalm: {
    portraits: 3,
    portrait: "fred",
    voice: "Fredcalm"
  },
  fred: {
    portraits: 3,
    portrait: "fred",
    voice: "Fredangry"
  },
  sod: {
    portraits: 3,
    portrait: "prole",
    voice: "Fredcalm"
  },
  boxin: {
    portraits: 1,
    interval: 3,
    portrait: "boxin",
    voice: "Germanangry"
  }
};
let d = null;
function f({ text: a, displayTextRef: r, displayPortraitRef: n, speed: c, actor: o, chainObj: t, chainIndex: e, chainDelay: v, playSoundRef: m, setLogicRef: g, chainName: u }) {
  d && clearTimeout(d), r.value = "";
  let y = a.replace(/"/g, "").split(" "), s = 0;
  h(y, r);
  function h(i, p) {
    s < i.length ? ((s + l[o].interval || 2) % (l[o].interval || 2) == 0 && m.value(l[o].voice), p.value = `${p.value} ${i[s]}`, n.value = D(o), s++, d = setTimeout(() => {
      h(i, p);
    }, 100)) : (e++, e < t.length ? setTimeout(() => {
      f({
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
        displayPortraitRef: n,
        displayTextRef: r,
        playSoundRef: m,
        setLogicRef: g,
        chainName: u
      });
    }, v) : setTimeout(() => {
      console.log(`[DIESELHUD] Chain ${u} complete`), g.value(u, "Done"), console.log("portrait ref"), n.value = "Img/portrait/none.png", r.value = "";
    }, v));
  }
  function D(i) {
    return `Img/portrait/${l[i].portrait}${E(1, l[i].portraits)}.png`;
  }
}
function A(a, r, n, c, o) {
  let t = b[a], e = 0;
  console.log(`[DIESELHUD] Starting chain ${a}`), f({
    actor: t[e][0],
    text: t[e][1],
    speed: t[e][2],
    chainDelay: t[e][3],
    chainIndex: e,
    chainObj: t,
    displayPortraitRef: n,
    displayTextRef: r,
    playSoundRef: c,
    setLogicRef: o,
    chainName: a
  });
}
const b = {
  testchain: [
    ["sod", "i'm walkin' ere!", 100, 1e3],
    ["sod", "you gotta watch where you're going, pal", 100, 1e3],
    ["sod", "jeez. god", 100, 1e3],
    ["sod", "state of this city, i'm tellin' ya", 100, 1e3],
    ["sod", "you'd think we was Chasm Cataluña or somethin'", 100, 1e3]
  ],
  boxin1: [
    ["boxin", "I HATE YOU!!!", 100, 500]
  ],
  boxin2: [
    ["boxin", "YOU ARE BAD!!!", 100, 500]
  ],
  boxin3: [
    ["boxin", "UMM DIE?", 100, 500]
  ],
  boxin4: [
    ["boxin", "GET SCARED GET SCARED GET SCARED", 50, 500]
  ],
  planeDownFred: [
    ["fred", "OUURUUGH!!!!", 100, 500]
  ],
  planeAttackFred: [
    ["fred", "For the board!!!", 100, 500]
  ],
  dukat: [
    ["evilduce", "Attention, Wallonian workers.", 100, 1e3],
    ["evilduce", "It has come to my attention that a handful of layabouts have...", 100, 1500],
    ["evilduce", "...undertaken an unfortunate exercise.", 500, 1e3],
    ["evilduce", "I implore you reconsider.", 200, 1e3],
    ["evilduce", "It is a matter of the city's productivity: remember this;", 100, 1e3],
    ["evilduce", "A productive Wallonia is a unified Wallonia is a safe Wallonia.", 100, 1e3],
    ["evilduce", "This in mind...", 100, 2e3],
    ["evilduce", "Any workers who aid in returning the dissidents", 100, 1e3],
    ["evilduce", "to their senses will be...", 100, 1500],
    ["evilduce", "...appropriately rewarded.", 100, 3e3],
    ["partner", "fie.", 100, 2e3],
    ["partner", "inconvenient, but i pay more than the board", 100, 1e3],
    ["partner", "how do they say it in the academiate?", 100, 3e3],
    ["partner", "ah, yes, I recall.", 200, 1500],
    ["partner", "go get 'em, tiger", 200, 3e3]
  ],
  meeting: [
    ["partner", "Ah, you've arrived.", 100, 1e3],
    ["partner", "My apologies for the squalor.", 100, 1e3],
    ["partner", "The accomodations are... meager.", 100, 1e3]
  ]
};
export {
  A as playChain,
  f as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
