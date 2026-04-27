import { defineComponent as D, ref as d, watch as y, computed as b, openBlock as i, createElementBlock as g, Fragment as k, createElementVNode as t, unref as n, toDisplayString as s, renderList as L } from "vue";
import { useData as r } from "vt:@core/composables/useData";
import { getPlayerClasses as O } from "./gameplayClasses.js";
import { hueRotateHex as v, LightenDarkenColor as m } from "./util.js";
import { playChain as $ } from "./dialogue.js";
const j = { anchor: { Left: 0, Top: 0 } }, K = ["background", "value"], J = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, X = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, q = ["background", "mask-texture-path"], Q = ["background"], Y = ["el-style"], Z = ["background"], tt = ["background"], ot = { anchor: { Top: 0, Right: -35 } }, et = ["background"], nt = ["el-style"], at = ["el-style"], rt = { anchor: { Right: 0 } }, lt = ["background"], ut = ["background"], st = ["background"], it = ["background"], gt = ["background", "mask-texture-path"], ht = { anchor: { Left: 8 } }, pt = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, ct = ["background"], dt = ["background"], mt = ["background", "mask-texture-path"], _t = { anchor: { Left: 8 } }, bt = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, kt = {
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  },
  anchor: { Top: 78 }
}, vt = { anchor: { Right: -20, Bottom: -52 } }, ft = ["background"], yt = ["background", "mask-texture-path"], Lt = ["background"], Gt = ["anchor"], It = ["el-style"], Tt = ["anchor", "el-style"], Ht = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, Ct = ["el-style"], St = ["el-style"], Wt = { anchor: { Top: 5, Left: 360 } }, Ft = { "layout-mode": "Top" }, Bt = ["background", "value"], Rt = ["el-style"], xt = ["background", "value"], Et = ["el-style"], _ = /* @__PURE__ */ D({
  __name: "DieselHud",
  setup(B) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const G = r("playSound", () => {
    }), R = r("playAnySound", () => {
    }), x = d(""), E = r("setLogic", () => {
    });
    y(x, (u) => {
      G.value(u);
    });
    const f = r("chain", ""), I = r("dashes", 4), N = r("objective", ""), M = r("class", "scout"), p = r("hotbarIdx", 0), T = r("health", 1);
    r("overlay", "none");
    const z = r("progress1", 0), A = r("progress1Label", ""), P = r("progress2"), w = r("progress2Label", ""), H = r("currentLevel", "");
    y(I, (u, o) => {
      console.log(u, o), u > o && R.value(`SFX_Stamina_Regen${u}`);
    });
    const U = b(() => M.value.toLowerCase()), c = b(() => O()[U.value]), l = b(() => ({
      ammo: r("ammo", 4),
      o: c.value.hotbar[p.value]
    }));
    let C = {
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
    const V = d(null), e = b(() => {
      let u = V.value ?? (H.length ? C[H.value] : C.StartOfGame);
      return {
        base: u,
        light: m(u, 200),
        dark: m(u, -30),
        alt: v(u, 40),
        brass: "#c98d1c",
        screen: "#282623"
      };
    }), S = d(""), W = d("Img/portrait/none.png");
    y(f, () => {
      console.log("input message", f.value), $(f.value, S, W, G, E);
    });
    const h = d("Img/empty.png");
    return (u, o) => (i(), g(k, null, [
      o[11] || (o[11] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", j, [
        o[0] || (o[0] = t("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          t("Group", { background: "Img/top_left.png" })
        ], -1)),
        t("ProgressBar", {
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: n(v)(e.value.base, -180 * (1 - n(T))) },
          "mask-texture-path": "Img/health/scout.png",
          "bar-texture-path": "Img/solid.png",
          "effect-height": 100,
          "effect-width": 100,
          value: 1 - n(T),
          alignment: "Vertical",
          direction: "End"
        }, null, 8, K),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", J, [
        t("Group", X, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": W.value
          }, null, 8, q)
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
          }, s(S.value), 9, Y)
        ], 8, Q),
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
          (i(!0), g(k, null, L(c.value.maxDashes, (F, a) => (i(), g("Group", {
            key: a,
            anchor: { Width: 48, Height: 48, Left: 20 },
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: a < n(I) ? "Img/charge_on.png" : "Img/charge_off.png"
            }, null, 8, tt)
          ]))), 128))
        ], 8, Z)
      ]),
      t("Group", ot, [
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
          }, " OBJECTIVE ", 8, nt),
          t("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            }
          }, s(n(N)), 9, at)
        ], 8, et),
        o[6] || (o[6] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", rt, [
        o[8] || (o[8] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: h.value,
          "layout-mode": "Top"
        }, [
          (i(!0), g(k, null, L(c.value.hotbar, (F, a) => (i(), g("Group", {
            key: a,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: n(p) == a ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${n(p) == a ? e.value.base : n(m)(e.value.base, -100)}(0.5)` }
            }, null, 8, st),
            t("Group", {
              background: n(p) == a ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, it),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: n(p) == a ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": c.value.hotbar[a]?.iconPath
            }, null, 8, gt),
            t("Group", ht, [
              t("Label", pt, s(a + 1) + "/" + s(c.value.hotbar[a]?.name), 1)
            ])
          ], 8, ut))), 128))
        ], 8, lt),
        t("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: h.value,
          "layout-mode": "Top"
        }, [
          (i(!0), g(k, null, L(l.value.o?.abilities, (F, a) => (i(), g("Group", {
            key: a,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, dt),
            o[7] || (o[7] = t("Group", { background: "Img/empty.png" }, null, -1)),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": l.value.o?.abilities[a]?.iconPath
            }, null, 8, mt),
            t("Group", _t, [
              t("Label", bt, s(l.value.o?.abilities[a].name), 1),
              t("Label", kt, s(l.value.o?.abilities[a].keybind), 1)
            ])
          ]))), 128))
        ], 8, ct)
      ]),
      t("Group", vt, [
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
          }, null, 8, yt)
        ], 8, ft),
        t("Group", {
          anchor: { Width: 600, Height: 300, Right: -90, Bottom: 30 },
          background: h.value
        }, [
          l.value.o?.maxAmmo ? (i(), g("Group", {
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
            }, s(l.value.ammo), 9, It),
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
            }, s(l.value.o?.maxAmmo), 9, Tt)
          ], 8, Gt)) : (i(), g("Group", Ht, [
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
            }, "ME", 8, Ct),
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
            }, "LEE", 8, St)
          ]))
        ], 8, Lt),
        o[10] || (o[10] = t("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 8e4, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      o[12] || (o[12] = t("Group", { anchor: { Full: 1 } }, null, -1)),
      t("Group", Wt, [
        t("Group", Ft, [
          t("Group", null, [
            t("ProgressBar", {
              anchor: { Left: 0, Top: 0, Width: 700, Height: 60 },
              background: { Color: n(m)(n(v)(e.value.base, 30), -120) },
              "mask-texture-path": "Img/bossbar.png",
              "bar-texture-path": "Img/solid.png",
              "effect-height": 100,
              "effect-width": 100,
              value: 1 - n(z),
              direction: "Start"
            }, null, 8, Bt),
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
            }, s(n(A).toLowerCase()), 9, Rt)
          ]),
          t("Group", null, [
            t("ProgressBar", {
              anchor: { Left: 0, Top: 0, Width: 700, Height: 60 },
              background: { Color: n(m)(e.value.base, -120) },
              "mask-texture-path": "Img/bossbar.png",
              "bar-texture-path": "Img/solid.png",
              "effect-height": 100,
              "effect-width": 100,
              value: 1 - n(P),
              direction: "Start"
            }, null, 8, xt),
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
            }, s(n(w).toLowerCase()), 9, Et)
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
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
