import { defineComponent as V, ref as d, watch as y, computed as b, openBlock as s, createElementBlock as i, Fragment as _, createElementVNode as t, unref as n, toDisplayString as g, renderList as L, createCommentVNode as B } from "vue";
import { useData as r } from "vt:@core/composables/useData";
import { getPlayerClasses as O } from "./gameplayClasses.js";
import { hueRotateHex as k, LightenDarkenColor as m } from "./util.js";
import { playChain as U } from "./dialogue.js";
const $ = { anchor: { Left: 0, Top: 0 } }, j = ["background", "value"], K = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, J = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, X = ["background", "mask-texture-path"], q = ["background"], Q = ["el-style"], Y = ["background"], Z = ["background"], tt = { anchor: { Top: 0, Right: -35 } }, ot = ["background"], et = ["el-style"], nt = ["el-style"], at = { anchor: { Right: 0 } }, rt = ["background"], lt = ["background"], ut = ["background"], st = ["background"], it = ["background", "mask-texture-path"], gt = { anchor: { Left: 8 } }, ht = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, pt = ["background"], ct = ["background"], dt = ["background", "mask-texture-path"], mt = { anchor: { Left: 8 } }, bt = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, _t = {
  anchor: { Top: 78 },
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  }
}, kt = { anchor: { Right: -20, Bottom: -52 } }, vt = ["background"], ft = ["background", "mask-texture-path"], yt = ["background"], Lt = ["anchor"], Gt = ["el-style"], Tt = ["anchor", "el-style"], Ct = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, It = ["el-style"], Ht = ["el-style"], St = { anchor: { Top: 5, Left: 360 } }, Wt = { "layout-mode": "Top" }, Ft = ["background", "value"], Bt = ["el-style"], xt = ["background", "value"], Rt = ["el-style"], Mt = /* @__PURE__ */ V({
  __name: "DieselHud",
  setup(Nt) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const G = r("playSound", () => {
    }), x = r("playAnySound", () => {
    }), R = d(""), N = r("setLogic", () => {
    });
    y(R, (u) => {
      G.value(u);
    });
    const v = r("chain", ""), T = r("dashes", 4), z = r("objective", ""), A = r("class", "scout"), p = r("hotbarIdx", 0), C = r("health", 1);
    r("overlay", "none");
    const E = r("progress1", 0), P = r("progress1Label", ""), w = r("progress2"), f = r("progress2Label", ""), I = r("currentLevel", "");
    y(T, (u, o) => {
      console.log(u, o), u > o && x.value(`SFX_Stamina_Regen${u}`);
    });
    const M = b(() => A.value.toLowerCase()), c = b(() => O()[M.value]), l = b(() => ({
      ammo: r("ammo", 4),
      o: c.value.hotbar[p.value]
    }));
    let H = {
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
      let u = D.value ?? (I.value.length ? H[I.value] : H.StartOfGame);
      return {
        base: u,
        light: m(u, 200),
        dark: m(u, -30),
        alt: k(u, 40),
        brass: "#c98d1c",
        screen: "#282623"
      };
    }), S = d(""), W = d("Img/portrait/none1.png");
    y(v, () => {
      console.log("input message", v.value), U(v.value, S, W, G, N);
    });
    const h = d("Img/empty.png");
    return (u, o) => (s(), i(_, null, [
      o[11] || (o[11] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", $, [
        o[0] || (o[0] = t("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          t("Group", { background: "Img/top_left.png" })
        ], -1)),
        t("ProgressBar", {
          alignment: "Vertical",
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: n(k)(e.value.base, -180 * (1 - n(C))) },
          "bar-texture-path": "Img/solid.png",
          direction: "End",
          "effect-height": 100,
          "effect-width": 100,
          "mask-texture-path": "Img/health/scout.png",
          value: 1 - n(C)
        }, null, 8, j),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", K, [
        t("Group", J, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": W.value
          }, null, 8, X)
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
          }, g(S.value), 9, Q)
        ], 8, q),
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
          (s(!0), i(_, null, L(c.value.maxDashes, (F, a) => (s(), i("Group", {
            key: a,
            anchor: { Width: 48, Height: 48, Left: 20 },
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: a < n(T) ? "Img/charge_on.png" : "Img/charge_off.png"
            }, null, 8, Z)
          ]))), 128))
        ], 8, Y)
      ]),
      t("Group", tt, [
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
          }, " OBJECTIVE ", 8, et),
          t("Label", {
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            },
            padding: { Full: 10 }
          }, g(n(z)), 9, nt)
        ], 8, ot),
        o[6] || (o[6] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", at, [
        o[8] || (o[8] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: h.value,
          "layout-mode": "Top"
        }, [
          (s(!0), i(_, null, L(c.value.hotbar, (F, a) => (s(), i("Group", {
            key: a,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: n(p) == a ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${n(p) == a ? e.value.base : n(m)(e.value.base, -100)}(0.5)` }
            }, null, 8, ut),
            t("Group", {
              background: n(p) == a ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, st),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: n(p) == a ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": c.value.hotbar[a]?.iconPath
            }, null, 8, it),
            t("Group", gt, [
              t("Label", ht, g(a + 1) + "/" + g(c.value.hotbar[a]?.name), 1)
            ])
          ], 8, lt))), 128))
        ], 8, rt),
        t("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: h.value,
          "layout-mode": "Top"
        }, [
          (s(!0), i(_, null, L(l.value.o?.abilities, (F, a) => (s(), i("Group", {
            key: a,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, ct),
            o[7] || (o[7] = t("Group", { background: "Img/empty.png" }, null, -1)),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": l.value.o?.abilities[a]?.iconPath
            }, null, 8, dt),
            t("Group", mt, [
              t("Label", bt, g(l.value.o?.abilities[a].name), 1),
              t("Label", _t, g(l.value.o?.abilities[a].keybind), 1)
            ])
          ]))), 128))
        ], 8, pt)
      ]),
      t("Group", kt, [
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
          }, null, 8, ft)
        ], 8, vt),
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
              "el-style": {
                FontSize: l.value.ammo.value < 10 ? 200 : 110,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              },
              padding: { Full: 20 }
            }, g(l.value.ammo), 9, Gt),
            t("Label", {
              anchor: l.value.ammo.value < 10 ? { Top: -10, Right: 80, Width: 300, Height: 200 } : { Top: 60, Left: 0, Right: 0, Width: 300, Height: 200 },
              "el-style": {
                FontSize: l.value.ammo.value < 10 ? 100 : 60,
                FontName: "Default",
                RenderBold: !0,
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              },
              padding: { Full: 20 }
            }, g(l.value.o?.maxAmmo), 9, Tt)
          ], 8, Lt)) : (s(), i("Group", Ct, [
            t("Label", {
              anchor: { Top: 30, Left: 50, Right: 0, Height: 200 },
              "el-style": {
                FontSize: 100,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              },
              padding: { Full: 20 }
            }, "ME", 8, It),
            t("Label", {
              anchor: { Top: -30, Left: 50, Right: 0, Height: 10 },
              "el-style": {
                FontSize: 100,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              },
              padding: { Full: 20 },
              '"': ""
            }, "LEE", 8, Ht)
          ]))
        ], 8, yt),
        o[10] || (o[10] = t("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 8e4, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      o[12] || (o[12] = t("Group", { anchor: { Full: 1 } }, null, -1)),
      t("Group", St, [
        t("Group", Wt, [
          t("Group", null, [
            n(f).length > 0 ? (s(), i("ProgressBar", {
              key: 0,
              anchor: { Left: 0, Top: 0, Width: 700, Height: 60 },
              background: { Color: n(m)(n(k)(e.value.base, 30), -120) },
              "bar-texture-path": "Img/solid.png",
              direction: "Start",
              "effect-height": 100,
              "effect-width": 100,
              "mask-texture-path": "Img/bossbar.png",
              value: 1 - n(E)
            }, null, 8, Ft)) : B("", !0),
            t("Label", {
              anchor: {
                Left: 10,
                Top: 5
              },
              "el-style": {
                TextColor: n(k)(e.value.base, 30),
                FontName: "Mono",
                FontSize: 30
              }
            }, g(n(P).toLowerCase()), 9, Bt)
          ]),
          t("Group", null, [
            n(f).length > 0 ? (s(), i("ProgressBar", {
              key: 0,
              anchor: { Left: 0, Top: 0, Width: 700, Height: 60 },
              background: { Color: n(m)(e.value.base, -120) },
              "bar-texture-path": "Img/solid.png",
              direction: "Start",
              "effect-height": 100,
              "effect-width": 100,
              "mask-texture-path": "Img/bossbar.png",
              value: 1 - n(w)
            }, null, 8, xt)) : B("", !0),
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
            }, g(n(f).toLowerCase()), 9, Rt)
          ])
        ])
      ])
    ], 64));
  }
});
export {
  Mt as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
