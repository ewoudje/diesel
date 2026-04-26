import { defineComponent as B, computed as m, ref as _, watch as x, openBlock as r, createElementBlock as l, Fragment as b, createElementVNode as t, unref as g, toDisplayString as u, renderList as v } from "vue";
import { useData as s } from "vt:@core/composables/useData";
import { getPlayerClasses as E } from "./gameplayClasses.js";
import { hueRotateHex as N, LightenDarkenColor as L } from "./util.js";
import { playChain as S } from "./dialogue.js";
const M = { anchor: { Left: 0, Top: 0 } }, z = ["background", "value"], A = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, V = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, U = ["background", "mask-texture-path"], D = ["background"], w = ["el-style"], P = ["background"], j = ["background"], O = { anchor: { Top: 0, Right: -35 } }, $ = ["background"], J = ["el-style"], K = ["el-style"], q = { anchor: { Right: 0 } }, Q = ["background"], X = ["background"], Y = ["background"], Z = ["background"], tt = ["background", "mask-texture-path"], ot = { anchor: { Left: 8 } }, et = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, nt = ["background"], at = ["background"], rt = ["background", "mask-texture-path"], lt = { anchor: { Left: 8 } }, ut = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, it = {
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  },
  anchor: { Top: 78 }
}, st = { anchor: { Right: -20, Bottom: -52 } }, ht = ["background"], gt = ["background", "mask-texture-path"], pt = ["background"], dt = ["anchor"], ct = ["el-style"], mt = ["anchor", "el-style"], _t = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, bt = ["el-style"], kt = ["el-style"], d = /* @__PURE__ */ B({
  __name: "DieselHud",
  setup(H) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const k = s("chain", ""), I = s("dashes", 4), T = s("objective", ""), C = s("class", "scout"), c = s("hotbarIdx", 0), W = s("health", 1), R = m(() => C.value.toLowerCase()), h = m(() => E()[R.value]), a = m(() => ({
      ammo: s("ammo", 4),
      o: h.value.hotbar[c.value]
    })), F = _(null), e = m(() => {
      let p = F.value ?? h.value.baseColor;
      return {
        base: p,
        light: L(p, 200),
        dark: L(p, -30),
        alt: N(p, 40),
        brass: "#c98d1c",
        screen: "#282624"
      };
    }), f = _(""), y = _("Img/portrait/none.png");
    x(k, () => {
      console.log("input message", k.value), S(k.value, f, y);
    });
    const i = _("Img/empty.png");
    return (p, o) => (r(), l(b, null, [
      o[11] || (o[11] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", M, [
        o[0] || (o[0] = t("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          t("Group", { background: "Img/top_left.png" })
        ], -1)),
        t("ProgressBar", {
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: e.value.base },
          "mask-texture-path": "Img/health/scout.png",
          "bar-texture-path": "Img/solid.png",
          x: "",
          "effect-height": 100,
          "effect-width": 100,
          value: 1 - g(W),
          alignment: "Vertical",
          direction: "End"
        }, null, 8, z),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", A, [
        t("Group", V, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": y.value
          }, null, 8, U)
        ]),
        t("Group", {
          anchor: { Left: 400, Bottom: 100, Width: 530, Height: 100 },
          background: i.value
        }, [
          t("Label", {
            "el-style": {
              FontSize: 35,
              FontName: "Mono",
              Wrap: !0,
              RenderUppercase: !0,
              TextColor: e.value.base
            }
          }, u(f.value), 9, w)
        ], 8, D),
        o[3] || (o[3] = t("Label", { "el-style": { FontSize: 1 } }, null, -1)),
        o[4] || (o[4] = t("Group", {
          anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 },
          background: "Img/bottom_left_wide_shine.png"
        }, null, -1)),
        t("Group", {
          anchor: { Left: 350, Bottom: 198, Height: 100, Width: 200 },
          background: i.value,
          "layout-mode": "Left"
        }, [
          (r(!0), l(b, null, v(h.value.maxDashes, (G, n) => (r(), l("Group", {
            key: n,
            anchor: { Width: 48, Height: 48, Left: 20 },
            background: n < g(I) ? "Img/charge_on.png" : "Img/charge_off.png",
            "mask-texture-path": "Img/mask/button.png"
          }, null, 8, j))), 128))
        ], 8, P)
      ]),
      t("Group", O, [
        o[5] || (o[5] = t("Group", { anchor: { Top: 0, Right: 80, Width: 700, Height: 220 } }, [
          t("Group", { background: "Img/top_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Top: 20, Right: 180, Width: 580, Height: 90 },
          background: i.value
        }, [
          t("Label", {
            anchor: { Left: 380, Top: 10 },
            "el-style": {
              FontSize: 30,
              FontName: "Mono",
              TextColor: e.value.base
            }
          }, " OBJECTIVE ", 8, J),
          t("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            }
          }, u(g(T)), 9, K)
        ], 8, $),
        o[6] || (o[6] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", q, [
        o[8] || (o[8] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: i.value,
          "layout-mode": "Top"
        }, [
          (r(!0), l(b, null, v(h.value.hotbar, (G, n) => (r(), l("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: g(c) == n ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.base}(0.5)` }
            }, null, 8, Y),
            t("Group", {
              background: g(c) == n ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, Z),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: g(c) == n ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": h.value.hotbar[n]?.iconPath
            }, null, 8, tt),
            t("Group", ot, [
              t("Label", et, u(n + 1) + "/" + u(h.value.hotbar[n]?.name), 1)
            ])
          ], 8, X))), 128))
        ], 8, Q),
        t("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: i.value,
          "layout-mode": "Top"
        }, [
          (r(!0), l(b, null, v(a.value.o?.abilities, (G, n) => (r(), l("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, at),
            o[7] || (o[7] = t("Group", { background: "Img/empty.png" }, null, -1)),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": a.value.o?.abilities[n]?.iconPath
            }, null, 8, rt),
            t("Group", lt, [
              t("Label", ut, u(a.value.o?.abilities[n].name), 1),
              t("Label", it, u(a.value.o?.abilities[n].keybind), 1)
            ])
          ]))), 128))
        ], 8, nt)
      ]),
      t("Group", st, [
        o[9] || (o[9] = t("Group", { anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 } }, [
          t("Group", { background: "Img/bottom_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Right: 250, Bottom: 0, Width: 400, Height: 150 },
          background: i.value
        }, [
          t("Group", {
            anchor: { Left: -20, Bottom: 62, Width: 400, Height: 130 },
            background: { Color: e.value.base },
            "mask-texture-path": a.value.o?.previewPath
          }, null, 8, gt)
        ], 8, ht),
        t("Group", {
          anchor: { Width: 600, Height: 300, Right: -90, Bottom: 30 },
          background: i.value
        }, [
          a.value.o?.maxAmmo ? (r(), l("Group", {
            key: 0,
            anchor: { Top: 30, Left: a.value.ammo.value < 10 ? -15 : 40 }
          }, [
            t("Label", {
              anchor: { Top: 10, Left: 0, Right: 0, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: a.value.ammo.value < 10 ? 200 : 110,
                FontName: "Secondary",
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              }
            }, u(a.value.ammo), 9, ct),
            t("Label", {
              anchor: a.value.ammo.value < 10 ? { Top: -10, Right: 80, Width: 300, Height: 200 } : { Top: 60, Left: 0, Right: 0, Width: 300, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: a.value.ammo.value < 10 ? 100 : 60,
                FontName: "Default",
                RenderBold: !0,
                HorizontalAlignment: "Center",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.base
              }
            }, u(a.value.o?.maxAmmo), 9, mt)
          ], 8, dt)) : (r(), l("Group", _t, [
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
              },
              '"': ""
            }, "ME", 8, bt),
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
            }, "LEE", 8, kt)
          ]))
        ], 8, pt),
        o[10] || (o[10] = t("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      o[12] || (o[12] = t("Group", { anchor: { Full: 1 } }, null, -1))
    ], 64));
  }
});
d.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(d.__hmrId, d) || __VUE_HMR_RUNTIME__.reload(d.__hmrId, d));
export {
  d as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
