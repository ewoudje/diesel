import { defineComponent as S, ref as d, watch as H, computed as _, openBlock as r, createElementBlock as l, Fragment as b, createElementVNode as t, unref as p, toDisplayString as u, renderList as v } from "vue";
import { useData as s } from "vt:@core/composables/useData";
import { getPlayerClasses as N } from "./gameplayClasses.js";
import { hueRotateHex as M, LightenDarkenColor as I } from "./util.js";
import { playChain as z } from "./dialogue.js";
const A = { anchor: { Left: 0, Top: 0 } }, V = ["background", "value"], U = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, D = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, w = ["background", "mask-texture-path"], P = ["background"], j = ["el-style"], O = ["background"], $ = ["background"], J = { anchor: { Top: 0, Right: -35 } }, K = ["background"], q = ["el-style"], Q = ["el-style"], X = { anchor: { Right: 0 } }, Y = ["background"], Z = ["background"], tt = ["background"], ot = ["background"], et = ["background", "mask-texture-path"], nt = { anchor: { Left: 8 } }, at = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, rt = ["background"], lt = ["background"], ut = ["background", "mask-texture-path"], st = { anchor: { Left: 8 } }, it = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, ht = {
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  },
  anchor: { Top: 78 }
}, gt = { anchor: { Right: -20, Bottom: -52 } }, pt = ["background"], dt = ["background", "mask-texture-path"], ct = ["background"], mt = ["anchor"], _t = ["el-style"], bt = ["anchor", "el-style"], kt = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, vt = ["el-style"], ft = ["el-style"], c = /* @__PURE__ */ S({
  __name: "DieselHud",
  setup(T) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const f = s("playSound", () => {
    }), C = d("");
    H(C, (h) => {
      f.value(h);
    });
    const k = s("chain", ""), W = s("dashes", 4), R = s("objective", ""), F = s("class", "scout"), m = s("hotbarIdx", 0), B = s("health", 1), x = _(() => F.value.toLowerCase()), g = _(() => N()[x.value]), a = _(() => ({
      ammo: s("ammo", 4),
      o: g.value.hotbar[m.value]
    })), E = d(null), e = _(() => {
      let h = E.value ?? g.value.baseColor;
      return {
        base: h,
        light: I(h, 200),
        dark: I(h, -30),
        alt: M(h, 40),
        brass: "#c98d1c",
        screen: "#282623"
      };
    }), y = d(""), G = d("Img/portrait/none.png");
    H(k, () => {
      console.log("input message", k.value), z(k.value, y, G, f);
    });
    const i = d("Img/empty.png");
    return (h, o) => (r(), l(b, null, [
      o[11] || (o[11] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", A, [
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
          value: 1 - p(B),
          alignment: "Vertical",
          direction: "End"
        }, null, 8, V),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", U, [
        t("Group", D, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": G.value
          }, null, 8, w)
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
          }, u(y.value), 9, j)
        ], 8, P),
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
          (r(!0), l(b, null, v(g.value.maxDashes, (L, n) => (r(), l("Group", {
            key: n,
            anchor: { Width: 48, Height: 48, Left: 20 },
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: n < p(W) ? "Img/charge_on.png" : "Img/charge_off.png"
            }, null, 8, $)
          ]))), 128))
        ], 8, O)
      ]),
      t("Group", J, [
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
          }, " OBJECTIVE ", 8, q),
          t("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            }
          }, u(p(R)), 9, Q)
        ], 8, K),
        o[6] || (o[6] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", X, [
        o[8] || (o[8] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: i.value,
          "layout-mode": "Top"
        }, [
          (r(!0), l(b, null, v(g.value.hotbar, (L, n) => (r(), l("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: p(m) == n ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.base}(0.5)` }
            }, null, 8, tt),
            t("Group", {
              background: p(m) == n ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, ot),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: p(m) == n ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": g.value.hotbar[n]?.iconPath
            }, null, 8, et),
            t("Group", nt, [
              t("Label", at, u(n + 1) + "/" + u(g.value.hotbar[n]?.name), 1)
            ])
          ], 8, Z))), 128))
        ], 8, Y),
        t("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: i.value,
          "layout-mode": "Top"
        }, [
          (r(!0), l(b, null, v(a.value.o?.abilities, (L, n) => (r(), l("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, lt),
            o[7] || (o[7] = t("Group", { background: "Img/empty.png" }, null, -1)),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": a.value.o?.abilities[n]?.iconPath
            }, null, 8, ut),
            t("Group", st, [
              t("Label", it, u(a.value.o?.abilities[n].name), 1),
              t("Label", ht, u(a.value.o?.abilities[n].keybind), 1)
            ])
          ]))), 128))
        ], 8, rt)
      ]),
      t("Group", gt, [
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
          }, null, 8, dt)
        ], 8, pt),
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
            }, u(a.value.ammo), 9, _t),
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
            }, u(a.value.o?.maxAmmo), 9, bt)
          ], 8, mt)) : (r(), l("Group", kt, [
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
            }, "ME", 8, vt),
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
            }, "LEE", 8, ft)
          ]))
        ], 8, ct),
        o[10] || (o[10] = t("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      o[12] || (o[12] = t("Group", { anchor: { Full: 1 } }, null, -1))
    ], 64));
  }
});
c.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(c.__hmrId, c) || __VUE_HMR_RUNTIME__.reload(c.__hmrId, c));
export {
  c as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OzsifQ==
