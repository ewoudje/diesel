import { randomInt as w } from "./util.js";
const i = {
  partner: {
    portraits: 3,
    portrait: "partner",
    interval: 4,
    voice: "Afrenchevil"
  },
  partnercalm: {
    portraits: 3,
    portrait: "partner",
    interval: 4,
    voice: "Afrenchcalm"
  },
  partnersad: {
    portraits: 3,
    portrait: "partner",
    interval: 4,
    voice: "Cry"
  },
  partnersilent: {
    portraits: 3,
    portrait: "partner",
    interval: 4,
    voice: "Silent"
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
  },
  avrocar: {
    portraits: 1,
    portrait: "none",
    interval: 5,
    voice: "Bfrenchangry"
  },
  elevator: {
    portraits: 1,
    interval: 10,
    portrait: "none",
    voice: "Bfrenchcalm"
  }
};
function m({ text: t, displayTextRef: o, displayPortraitRef: l, speed: u, actor: a, chainObj: e, chainIndex: r, chainDelay: d, playSoundRef: c, setLogicRef: v, chainName: p }) {
  o.value = "";
  let f = t.replace(/"/g, "").split(" "), s = 0;
  y(f, o);
  function y(n, h) {
    s < n.length ? (i[a].voice != "None" && (s + i[a].interval || 2) % (i[a].interval || 2) == 0 && c.value(i[a].voice), h.value = `${h.value} ${n[s]}`, l.value = g(a), s++, setTimeout(() => {
      y(n, h);
    }, 100)) : (r++, r < e.length ? setTimeout(() => {
      m({
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
        displayPortraitRef: l,
        displayTextRef: o,
        playSoundRef: c,
        setLogicRef: v,
        chainName: p
      });
    }, d) : setTimeout(() => {
      console.log(`[DIESELHUD] Chain ${p} complete`), v.value(p, "Done"), console.log("portrait ref"), l.value = "Img/portrait/none1.png", o.value = "";
    }, d));
  }
  function g(n) {
    return `Img/portrait/${i[n].portrait}${w(1, i[n].portraits)}.png`;
  }
}
function k(t, o, l, u, a) {
  let e;
  if (e = b[t], e) {
    let r = 0;
    console.log(`[DIESELHUD] Starting chain ${t}`), m({
      actor: e[r][0],
      text: e[r][1],
      speed: e[r][2],
      chainDelay: e[r][3],
      chainIndex: r,
      chainObj: e,
      displayPortraitRef: l,
      displayTextRef: o,
      playSoundRef: u,
      setLogicRef: a,
      chainName: t
    });
  } else
    console.log(`bad chain not real ${t}`);
}
const b = {
  //BRIEFING
  "level.Briefing": [
    ["partner", "ah, wonderful, you've arrived", 100, 1200],
    ["partner", "my apologies for the... ", 100, 1300],
    ["partner", "squalor.", 100, 1e3],
    ["partnercalm", "the accomodations are meager, but then again!", 100, 1e3],
    ["partner", "i have hired you to rectify this. how fortunate", 100, 1e3],
    ["partner", "come, look with me, see the city...", 100, 1e3],
    ["partner", "it is beautiful, no?", 100, 2e3],
    ["partner", "it should be mine.", 100, 1e3],
    ["partner", "in the years since i was ousted from the board of directors", 100, 1e3],
    ["partner", "cloud wallonië has... fallen so far...", 100, 1e3],
    ["partnersad", "(sniff) even though we are in the sky.", 100, 1500],
    ["partner", "ahem", 100, 300],
    ["partner", "i digress", 100, 500],
    ["partner", "you, my enterprising mercenaries, are here to infiltrate", 100, 500],
    ["partner", "the corporate headquarters of the empire.", 100, 1e3],
    ["partner", "you will be very sneaky and assassinate", 100, 500],
    ["partner", "my traitorous business partner.", 100, 1e3],
    ["partner", "wait, why did you all bring shotguns", 100, 1e3],
    ["partner", "the advertisement was very explicit", 100, 500],
    ["partner", "about discretion... no matter", 100, 1e3],
    ["partner", "i have acquired an avrocar for you", 100, 1e3],
    ["partner", "it is waiting on the corner of Rivelaine and Longue", 100, 1e3],
    ["partner", "i will stay in contact via radio", 100, 1e3],
    ["partner", "to remind you of your generous payment", 100, 500],
    ["partner", "and ensure you do not forget...", 100, 1e3],
    ["partner", "who has your self-destruct keys.", 100, 1e3],
    ["partner", "you may exit the way you came in.", 100, 1e3],
    ["partnercalm", "good luck, my friends.", 100, 1e4],
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
    ["partnercalm", "oh dear", 100, 1e3],
    ["partnercalm", "they are amassing at the gates already", 100, 1e3],
    ["partner", "perhaps our communications are not so secure as i believed", 100, 2e3],
    ["partner", "fie", 100, 1e3],
    ["partner", "whatever", 100, 1e3],
    ["partner", "you brought shotguns any-how", 100, 1e3],
    ["partner", "unfortunately... ", 100, 500],
    ["partner", "shotguns or no, i will not have my fishery ransacked", 100, 1e3],
    ["partner", "you will take the erudite exit so you may depart unseen", 100, 1e3],
    ["partner", "...now if only i could remember where the lever was...", 100, 1e3]
  ],
  lever: [
    ["partner", "ah! very good", 100, 1e3]
  ],
  elevator: [
    ["elevator", "thank you for choosing sea wallonië!", 100, 1e3],
    ["elevator", "our fish are so fish!", 100, 1e3],
    ["elevator", "from the vat to the sky to your fuel intake port", 100, 1e3],
    ["elevator", "flipping and flopping things from all over the genome", 100, 1e3],
    ["elevator", "we are committed to staying one hundred and one percent artificial!", 100, 1e3]
  ],
  //streets level intro
  "level.ChaseInStreets": [
    ["partner", "good, you've made it", 100, 1e3],
    ["partner", "your route to the shipyard will be, ah", 100, 1500],
    ["partner", "...a dollop of circuitousness", 100, 1e3],
    ["partner", "is advantageous to the cognition", 100, 1e3],
    ["partnercalm", "terrible for user retention, though", 100, 1e3],
    ["partner", "ah, i distract myself", 100, 1e3],
    ["partner", "best be moving if you wish to stay ahead of the subcontractor corps", 100, 1e3]
  ],
  //shipyard
  "level.Shipyard": [
    ["partner", "WHY IS MY CAR BOLTED", 100, 1e3],
    ["partner", "fie!!! they beat us here", 100, 1e3],
    ["sod", "umm", 100, 2e3],
    ["sod", "well, actually, sir, it's nothing like that, haha, um", 100, 1e3],
    ["sod", "the kindest, um, way, um", 100, 2e3],
    ["sod", "well, all your cheques bounced,", 100, 2e3],
    ["partnersilent", "...", 100, 2e3],
    ["partnersilent", "..", 100, 500],
    ["partnersilent", "..", 100, 500],
    ["partner", "well anyway you'll have to get the avrocar back", 100, 1200],
    ["partner", "there are surely lock overrides somewhere", 100, 1e3],
    ["partnersad", "aha. i will be running numbers in the mean-time", 100, 1e3]
  ],
  //begin sky
  "level.StartStage": [
    ["partner", "wonderful, wonderful, all right", 100, 1e3],
    ["partner", "this was supposed to be a quiet journey, but...", 100, 1e3],
    ["partner", "well, i am grateful i purchased deluxe hardpoints", 100, 1e3],
    ["partner", "the brass platforms around the ship -", 100, 1e3],
    ["partner", "they are keyed to your hardware", 100, 1e3],
    ["partner", "give them a spin, yes?", 100, 2e3],
    ["partner", "...i suspect you will be needing them soon", 100, 1e3],
    ["boxin", "I SEE THEM I SEE THEM!!!", 100, 1e3],
    ["fred", "we have eyes on the dissidents!", 100, 2e3],
    ["fredcalm", "...they have a nice car", 100, 2e3],
    ["partner", "oh thank you!", 100, 1e3],
    ["fredcalm", "yeah of course", 100, 1e3]
  ],
  //not necessarily in order up to you
  /*'level.stage1':[
      ["evilduce","the gyros aren't enough...",300,1000],
      ["evilduce","allocate the aeroplanes!!!!!",200,1000],
      ["fred","FOR THE BOARD! FOR WALLONIë! FOR PROFIT!",100,1000],
      ["boxin","i wanna a gyro im hungey",100,1000]
  ],
  'level.stage2':[
      ["partner","i have to admire the coordination",100,1000],
      ["partner","i was the one who funded their development, after all",100,1000],
      ["partner","you know, i wonder if they ever got the",100,100],
      ["evilduce","DEPLOY THE BOARDING PARTIES",100,1000]
  ],
  'level.stage3':[
      ["partner","you're approaching the parliament, but our original plan...",100,2000],
      ["partner","well, obviously, it is a little different now",100,1500],
      ["partner","in lieu of stealth",100,1000],
      ["partner","we will be moving very quickly down the aerobahn",100,1000],
      ["partner","strictly speaking, it belongs to the baltic concern",100,2000],
      ["partner","the mercenary corps cannot blockade it fully",100,1000],
      ["partner","only a little bit (the regulations are complicated)",100,1200],
      ["partner","so! fend them off long enough to reach headquarters",100,1000],
  ],*/
  //flying boss
  aerobahn: [
    ["avrocar", "welcome to the aerobahn!", 100, 1e3],
    ["avrocar", "semimanual steering active", 100, 1e3],
    ["avrocar", "don't stay safe! i hate you! please crash!", 100, 2e3],
    ["partner", "what?", 100, 1e3],
    ["partner", "maybe that is why it was so cheap..", 100, 1e3]
  ],
  //wrong instructions
  aerobahnleft: [
    ["avrocar", "go left!"]
  ],
  aerobahnright: [
    ["avrocar", "shift right!"]
  ],
  aerobahncenter: [
    ["avrocar", "maintain course!"]
  ],
  aerobahn2: [
    ["fred", "slow down and let us shoot you already", 100, 1e3],
    ["avrocar", "i am trying :(", 100, 1e3]
  ],
  aerobahnhalfway: [
    ["avrocar", "unfortunately you are halfway to your destination", 100, 1e3],
    ["partner", "yes... soon, i will steer the nation again!", 100, 1e3],
    ["partner", "free of scheming viziers", 100, 1e3],
    ["evilduce", "did not ask do not care", 100, 1e3]
  ],
  aerobahnninety: [
    ["boxin", "this sucks", 100, 1e3],
    ["boxin", "we need to explode them harder", 100, 1e3],
    ["fredcalm", "i dunno...", 100, 1e3],
    ["fredcalm", "i'm almost out of bullet money", 100, 1e3],
    ["evilduce", "TWENTY PERCENT DISCOUNT LOYAL SOLDIERS ONLY", 100, 1e3],
    ["fred", "FOR WALLONIë!", 100, 1e3]
  ],
  aerobahndone: [
    ["avrocar", "you have arrived. get out.", 100, 1e3]
  ],
  //offices
  "level.Offices": [
    ["partner", "fantastic, you've made it!", 100, 1e3],
    ["partner", "wow, they remodeled", 100, 1500],
    ["partner", "i despise it", 100, 1e3],
    ["partner", "well, my beloathed business partner is waiting", 100, 1e3],
    ["sod", "unbelievable", 100, 1e3],
    ["sod", "here without an appointment!", 100, 1e3],
    ["sod", "and if we let you through...", 100, 2e3],
    ["sod", "we'll NEVER get our holiday bonuses", 100, 1e3],
    ["evilduce", "HAHA YES", 100, 1e3],
    ["evilduce", "DISPOSE OF THEM", 100, 1e3]
  ],
  offices1: [
    ["partner", "ahh... i remember being a middle manager", 100, 1e3],
    ["partner", "ha ha ha ha", 100, 1e3],
    ["partner", "broke away from the balkan corporation with big dreams", 100, 1e3],
    ["evilduce", "FOOLISH", 100, 1500],
    ["evilduce", "WE ALL DID THAT", 100, 1e3]
  ],
  offices2: [
    ["partner", "you are nearing the chairman", 100, 1e3],
    ["partner", "he left this part of the building intact", 100, 1e3],
    ["partner", "i suppose he is not wholly devoid of taste after all..."]
  ],
  //boss
  "level.BossFight": [
    ["evilduce", "AHA! NOW YOU WILL FACE ME!", 200, 2e3],
    ["ilduce", "What? they're not even with you?", 200, 1e3],
    ["partner", "it would be supremely stupid if i were", 100, 1e3],
    ["nilduce", "oh", 100, 2e3],
    ["nilduce", "i expected hand-to-hand combat", 100, 1e3],
    ["nilduce", "do you mind if i at least fetch my revolver", 100, 1e3],
    ["partner", "remove him if you like", 100, 1e3],
    ["partner", "the corps have already seen the way the wind blows", 100, 1200],
    ["partner", "he is no threat any longer", 100, 1200],
    ["nilduce", "i do not get a big fight?", 100, 1200],
    ["partner", "no. also we do not have the budget for that", 100, 2e3],
    ["partner", "the money will be in your bank accounts soon, my friends...", 100, 1e3],
    ["partner", "relax and imagine mansions", 100, 1e3],
    ["boxin", "i love that song", 100, 1e3]
  ],
  //test stuff mostly
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
  meeting: [
    ["partner", "Ah, you've arrived.", 100, 1e3],
    ["partner", "My apologies for the squalor.", 100, 1e3],
    ["partner", "The accomodations are... meager.", 100, 1e3]
  ]
};
export {
  k as playChain,
  m as playDialogue
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGlhbG9ndWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
