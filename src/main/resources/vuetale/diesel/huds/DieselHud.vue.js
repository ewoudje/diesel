import { defineComponent as O, ref as d, watch as L, computed as b, openBlock as s, createElementBlock as i, Fragment as k, createElementVNode as t, unref as n, toDisplayString as g, renderList as G, createCommentVNode as R } from "vue";
import { useData as r } from "vt:@core/composables/useData";
import { getPlayerClasses as $ } from "./gameplayClasses.js";
import { hueRotateHex as v, LightenDarkenColor as m } from "./util.js";
import { playChain as j } from "./dialogue.js";
const K = { anchor: { Left: 0, Top: 0 } }, J = ["background", "value"], X = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, q = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, Q = ["background", "mask-texture-path"], Y = ["background"], Z = ["el-style"], tt = ["background"], ot = ["background"], et = { anchor: { Top: 0, Right: -35 } }, nt = ["background"], at = ["el-style"], rt = ["el-style"], lt = { anchor: { Right: 0 } }, ut = ["background"], st = ["background"], it = ["background"], gt = ["background"], ht = ["background", "mask-texture-path"], pt = { anchor: { Left: 8 } }, ct = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, dt = ["background"], mt = ["background"], _t = ["background", "mask-texture-path"], bt = { anchor: { Left: 8 } }, kt = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, vt = {
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  },
  anchor: { Top: 78 }
}, ft = { anchor: { Right: -20, Bottom: -52 } }, yt = ["background"], Lt = ["background", "mask-texture-path"], Gt = ["background"], It = ["anchor"], Tt = ["el-style"], Ct = ["anchor", "el-style"], Ht = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, St = ["el-style"], Wt = ["el-style"], Ft = { anchor: { Top: 5, Left: 360 } }, Bt = { "layout-mode": "Top" }, Rt = ["background", "value"], xt = ["el-style"], Et = ["background", "value"], Nt = ["el-style"], _ = /* @__PURE__ */ O({
  __name: "DieselHud",
  setup(x) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const I = r("playSound", () => {
    }), E = r("playAnySound", () => {
    }), N = d(""), M = r("setLogic", () => {
    });
    L(N, (u) => {
      I.value(u);
    });
    const f = r("chain", ""), T = r("dashes", 4), z = r("objective", ""), A = r("class", "scout"), p = r("hotbarIdx", 0), C = r("health", 1);
    r("overlay", "none");
    const P = r("progress1", 0), w = r("progress1Label", ""), V = r("progress2"), y = r("progress2Label", ""), H = r("currentLevel", "");
    L(T, (u, o) => {
      console.log(u, o), u > o && E.value(`SFX_Stamina_Regen${u}`);
    });
    const U = b(() => A.value.toLowerCase()), c = b(() => $()[U.value]), l = b(() => ({
      ammo: r("ammo", 4),
      o: c.value.hotbar[p.value]
    }));
    let S = {
      StartOfGame: "#FF0000",
      InOffice: "#282623",
      ChaseInStreets: "#282623",
      Shipyard: "#282623",
      StartStage: "#282623",
      Stage1: "#282623",
      Stage2: "#282623",
      BossStage: "#282623",
      EnterMech: "#282623",
      BreakIn: "#282623",
      BrokeIn: "#282623",
      UnlockedDoor: "#282623",
      KGB: "#282623",
      FinalStretch: "#282623",
      BossFight: "#282623"
    };
    const D = d(null), e = b(() => {
      let u = D.value ?? (H.value.length ? S[H.value] : S.StartOfGame);
      return {
        base: u,
        light: m(u, 200),
        dark: m(u, -30),
        alt: v(u, 40),
        brass: "#c98d1c",
        screen: "#282623"
      };
    }), W = d(""), F = d("Img/portrait/none1.png");
    L(f, () => {
      console.log("input message", f.value), j(f.value, W, F, I, M);
    });
    const h = d("Img/empty.png");
    return (u, o) => (s(), i(k, null, [
      o[11] || (o[11] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", K, [
        o[0] || (o[0] = t("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          t("Group", { background: "Img/top_left.png" })
        ], -1)),
        t("ProgressBar", {
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: n(v)(e.value.base, -180 * (1 - n(C))) },
          "mask-texture-path": "Img/health/scout.png",
          "bar-texture-path": "Img/solid.png",
          "effect-height": 100,
          "effect-width": 100,
          value: 1 - n(C),
          alignment: "Vertical",
          direction: "End"
        }, null, 8, J),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", X, [
        t("Group", q, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": F.value
          }, null, 8, Q)
        ]),
        t("Group", {
          anchor: { Left: 400, Bottom: 100, Width: 530, Height: 100 },
          background: h.value
        }, [
          t("Label", {
            "el-style": {
              FontSize: 35,
              FontName: "Mono",
              Wrap: !0,
              RenderUppercase: !0,
              TextColor: e.value.base
            }
          }, g(W.value), 9, Z)
        ], 8, Y),
        o[3] || (o[3] = t("Label", { "el-style": { FontSize: 1 } }, null, -1)),
        o[4] || (o[4] = t("Group", {
          anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 },
          background: "Img/bottom_left_wide_shine.png"
        }, null, -1)),
        t("Group", {
          anchor: { Left: 350, Bottom: 198, Height: 100, Width: 200 },
          background: h.value,
          "layout-mode": "Left"
        }, [
          (s(!0), i(k, null, G(c.value.maxDashes, (B, a) => (s(), i("Group", {
            key: a,
            anchor: { Width: 48, Height: 48, Left: 20 },
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: a < n(T) ? "Img/charge_on.png" : "Img/charge_off.png"
            }, null, 8, ot)
          ]))), 128))
        ], 8, tt)
      ]),
      t("Group", et, [
        o[5] || (o[5] = t("Group", { anchor: { Top: 0, Right: 80, Width: 700, Height: 220 } }, [
          t("Group", { background: "Img/top_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Top: 20, Right: 180, Width: 580, Height: 90 },
          background: h.value
        }, [
          t("Label", {
            anchor: { Left: 380, Top: 10 },
            "el-style": {
              FontSize: 30,
              FontName: "Mono",
              TextColor: e.value.base
            }
          }, " OBJECTIVE ", 8, at),
          t("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            }
          }, g(n(z)), 9, rt)
        ], 8, nt),
        o[6] || (o[6] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", lt, [
        o[8] || (o[8] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: h.value,
          "layout-mode": "Top"
        }, [
          (s(!0), i(k, null, G(c.value.hotbar, (B, a) => (s(), i("Group", {
            key: a,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: n(p) == a ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${n(p) == a ? e.value.base : n(m)(e.value.base, -100)}(0.5)` }
            }, null, 8, it),
            t("Group", {
              background: n(p) == a ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, gt),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: n(p) == a ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": c.value.hotbar[a]?.iconPath
            }, null, 8, ht),
            t("Group", pt, [
              t("Label", ct, g(a + 1) + "/" + g(c.value.hotbar[a]?.name), 1)
            ])
          ], 8, st))), 128))
        ], 8, ut),
        t("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: h.value,
          "layout-mode": "Top"
        }, [
          (s(!0), i(k, null, G(l.value.o?.abilities, (B, a) => (s(), i("Group", {
            key: a,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, mt),
            o[7] || (o[7] = t("Group", { background: "Img/empty.png" }, null, -1)),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": l.value.o?.abilities[a]?.iconPath
            }, null, 8, _t),
            t("Group", bt, [
              t("Label", kt, g(l.value.o?.abilities[a].name), 1),
              t("Label", vt, g(l.value.o?.abilities[a].keybind), 1)
            ])
          ]))), 128))
        ], 8, dt)
      ]),
      t("Group", ft, [
        o[9] || (o[9] = t("Group", { anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 } }, [
          t("Group", { background: "Img/bottom_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Right: 250, Bottom: 0, Width: 400, Height: 150 },
          background: h.value
        }, [
          t("Group", {
            anchor: { Left: -20, Bottom: 62, Width: 400, Height: 130 },
            background: { Color: e.value.base },
            "mask-texture-path": l.value.o?.previewPath
          }, null, 8, Lt)
        ], 8, yt),
        t("Group", {
          anchor: { Width: 600, Height: 300, Right: -90, Bottom: 30 },
          background: h.value
        }, [
          l.value.o?.maxAmmo ? (s(), i("Group", {
            key: 0,
            anchor: { Top: 30, Left: l.value.ammo.value < 10 ? -15 : 40 }
          }, [
            t("Label", {
              anchor: { Top: 10, Left: 0, Right: 0, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: l.value.ammo.value < 10 ? 200 : 110,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              }
            }, g(l.value.ammo), 9, Tt),
            t("Label", {
              anchor: l.value.ammo.value < 10 ? { Top: -10, Right: 80, Width: 300, Height: 200 } : { Top: 60, Left: 0, Right: 0, Width: 300, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: l.value.ammo.value < 10 ? 100 : 60,
                FontName: "Default",
                RenderBold: !0,
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              }
            }, g(l.value.o?.maxAmmo), 9, Ct)
          ], 8, It)) : (s(), i("Group", Ht, [
            t("Label", {
              anchor: { Top: 30, Left: 50, Right: 0, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: 100,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              }
            }, "ME", 8, St),
            t("Label", {
              anchor: { Top: -30, Left: 50, Right: 0, Height: 10 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: 100,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              },
              '"': ""
            }, "LEE", 8, Wt)
          ]))
        ], 8, Gt),
        o[10] || (o[10] = t("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 8e4, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      o[12] || (o[12] = t("Group", { anchor: { Full: 1 } }, null, -1)),
      t("Group", Ft, [
        t("Group", Bt, [
          t("Group", null, [
            n(y).length > 0 ? (s(), i("ProgressBar", {
              key: 0,
              anchor: { Left: 0, Top: 0, Width: 700, Height: 60 },
              background: { Color: n(m)(n(v)(e.value.base, 30), -120) },
              "mask-texture-path": "Img/bossbar.png",
              "bar-texture-path": "Img/solid.png",
              "effect-height": 100,
              "effect-width": 100,
              value: 1 - n(P),
              direction: "Start"
            }, null, 8, Rt)) : R("", !0),
            t("Label", {
              anchor: {
                Left: 10,
                Top: 5
              },
              "el-style": {
                TextColor: n(v)(e.value.base, 30),
                FontName: "Mono",
                FontSize: 30
              }
            }, g(n(w).toLowerCase()), 9, xt)
          ]),
          t("Group", null, [
            n(y).length > 0 ? (s(), i("ProgressBar", {
              key: 0,
              anchor: { Left: 0, Top: 0, Width: 700, Height: 60 },
              background: { Color: n(m)(e.value.base, -120) },
              "mask-texture-path": "Img/bossbar.png",
              "bar-texture-path": "Img/solid.png",
              "effect-height": 100,
              "effect-width": 100,
              value: 1 - n(V),
              direction: "Start"
            }, null, 8, Et)) : R("", !0),
            t("Label", {
              anchor: {
                Left: 10,
                Top: 5
              },
              "el-style": {
                TextColor: e.value.base,
                FontName: "Mono",
                FontSize: 30
              }
            }, g(n(y).toLowerCase()), 9, Nt)
          ])
        ])
      ])
    ], 64));
  }
});
_.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(_.__hmrId, _) || __VUE_HMR_RUNTIME__.reload(_.__hmrId, _));
export {
  _ as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
