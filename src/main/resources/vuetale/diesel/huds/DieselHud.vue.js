import { defineComponent as N, ref as d, watch as H, computed as _, openBlock as l, createElementBlock as u, Fragment as b, createElementVNode as o, unref as p, toDisplayString as s, renderList as v } from "vue";
import { useData as r } from "vt:@core/composables/useData";
import { getPlayerClasses as M } from "./gameplayClasses.js";
import { hueRotateHex as z, LightenDarkenColor as I } from "./util.js";
import { playChain as A } from "./dialogue.js";
const V = { anchor: { Left: 0, Top: 0 } }, U = ["background", "value"], D = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, w = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, P = ["background", "mask-texture-path"], j = ["background"], O = ["el-style"], $ = ["background"], J = ["background"], K = { anchor: { Top: 0, Right: -35 } }, q = ["background"], Q = ["el-style"], X = ["el-style"], Y = { anchor: { Right: 0 } }, Z = ["background"], oo = ["background"], to = ["background"], eo = ["background"], no = ["background", "mask-texture-path"], ao = { anchor: { Left: 8 } }, ro = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, lo = ["background"], uo = ["background"], so = ["background", "mask-texture-path"], io = { anchor: { Left: 8 } }, ho = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, go = {
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  },
  anchor: { Top: 78 }
}, po = { anchor: { Right: -20, Bottom: -52 } }, co = ["background"], mo = ["background", "mask-texture-path"], _o = ["background"], bo = ["anchor"], ko = ["el-style"], vo = ["anchor", "el-style"], fo = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, yo = ["el-style"], Go = ["el-style"], c = /* @__PURE__ */ N({
  __name: "DieselHud",
  setup(T) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const f = r("playSound", () => {
    }), C = d(""), W = r("setLogic", () => {
    });
    H(C, (h) => {
      f.value(h);
    });
    const k = r("chain", ""), R = r("dashes", 4), F = r("objective", ""), B = r("class", "scout"), m = r("hotbarIdx", 0), E = r("health", 1);
    r("overlay", "none");
    const x = _(() => B.value.toLowerCase()), g = _(() => M()[x.value]), a = _(() => ({
      ammo: r("ammo", 4),
      o: g.value.hotbar[m.value]
    })), S = d(null), e = _(() => {
      let h = S.value ?? g.value.baseColor;
      return {
        base: h,
        light: I(h, 200),
        dark: I(h, -30),
        alt: z(h, 40),
        brass: "#c98d1c",
        screen: "#282623"
      };
    }), y = d(""), G = d("Img/portrait/none.png");
    H(k, () => {
      console.log("input message", k.value), A(k.value, y, G, f, W);
    });
    const i = d("Img/empty.png");
    return (h, t) => (l(), u(b, null, [
      t[11] || (t[11] = o("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      o("Group", V, [
        t[0] || (t[0] = o("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          o("Group", { background: "Img/top_left.png" })
        ], -1)),
        o("ProgressBar", {
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: e.value.base },
          "mask-texture-path": "Img/health/scout.png",
          "bar-texture-path": "Img/solid.png",
          "effect-height": 100,
          "effect-width": 100,
          value: 1 - p(E),
          alignment: "Vertical",
          direction: "End"
        }, null, 8, U),
        t[1] || (t[1] = o("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      o("Group", D, [
        o("Group", w, [
          t[2] || (t[2] = o("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          o("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": G.value
          }, null, 8, P)
        ]),
        o("Group", {
          anchor: { Left: 400, Bottom: 100, Width: 530, Height: 100 },
          background: i.value
        }, [
          o("Label", {
            "el-style": {
              FontSize: 35,
              FontName: "Mono",
              Wrap: !0,
              RenderUppercase: !0,
              TextColor: e.value.base
            }
          }, s(y.value), 9, O)
        ], 8, j),
        t[3] || (t[3] = o("Label", { "el-style": { FontSize: 1 } }, null, -1)),
        t[4] || (t[4] = o("Group", {
          anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 },
          background: "Img/bottom_left_wide_shine.png"
        }, null, -1)),
        o("Group", {
          anchor: { Left: 350, Bottom: 198, Height: 100, Width: 200 },
          background: i.value,
          "layout-mode": "Left"
        }, [
          (l(!0), u(b, null, v(g.value.maxDashes, (L, n) => (l(), u("Group", {
            key: n,
            anchor: { Width: 48, Height: 48, Left: 20 },
            "mask-texture-path": "Img/mask/button.png"
          }, [
            o("Group", {
              background: n < p(R) ? "Img/charge_on.png" : "Img/charge_off.png"
            }, null, 8, J)
          ]))), 128))
        ], 8, $)
      ]),
      o("Group", K, [
        t[5] || (t[5] = o("Group", { anchor: { Top: 0, Right: 80, Width: 700, Height: 220 } }, [
          o("Group", { background: "Img/top_right.png" })
        ], -1)),
        o("Group", {
          anchor: { Top: 20, Right: 180, Width: 580, Height: 90 },
          background: i.value
        }, [
          o("Label", {
            anchor: { Left: 380, Top: 10 },
            "el-style": {
              FontSize: 30,
              FontName: "Mono",
              TextColor: e.value.base
            }
          }, " OBJECTIVE ", 8, Q),
          o("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            }
          }, s(p(F)), 9, X)
        ], 8, q),
        t[6] || (t[6] = o("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      o("Group", Y, [
        t[8] || (t[8] = o("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        o("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: i.value,
          "layout-mode": "Top"
        }, [
          (l(!0), u(b, null, v(g.value.hotbar, (L, n) => (l(), u("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: p(m) == n ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            o("Group", {
              background: { Color: `${e.value.base}(0.5)` }
            }, null, 8, to),
            o("Group", {
              background: p(m) == n ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, eo),
            o("Group", {
              anchor: { Width: 60, Height: 60 },
              background: p(m) == n ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": g.value.hotbar[n]?.iconPath
            }, null, 8, no),
            o("Group", ao, [
              o("Label", ro, s(n + 1) + "/" + s(g.value.hotbar[n]?.name), 1)
            ])
          ], 8, oo))), 128))
        ], 8, Z),
        o("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: i.value,
          "layout-mode": "Top"
        }, [
          (l(!0), u(b, null, v(a.value.o?.abilities, (L, n) => (l(), u("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            o("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, uo),
            t[7] || (t[7] = o("Group", { background: "Img/empty.png" }, null, -1)),
            o("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": a.value.o?.abilities[n]?.iconPath
            }, null, 8, so),
            o("Group", io, [
              o("Label", ho, s(a.value.o?.abilities[n].name), 1),
              o("Label", go, s(a.value.o?.abilities[n].keybind), 1)
            ])
          ]))), 128))
        ], 8, lo)
      ]),
      o("Group", po, [
        t[9] || (t[9] = o("Group", { anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 } }, [
          o("Group", { background: "Img/bottom_right.png" })
        ], -1)),
        o("Group", {
          anchor: { Right: 250, Bottom: 0, Width: 400, Height: 150 },
          background: i.value
        }, [
          o("Group", {
            anchor: { Left: -20, Bottom: 62, Width: 400, Height: 130 },
            background: { Color: e.value.base },
            "mask-texture-path": a.value.o?.previewPath
          }, null, 8, mo)
        ], 8, co),
        o("Group", {
          anchor: { Width: 600, Height: 300, Right: -90, Bottom: 30 },
          background: i.value
        }, [
          a.value.o?.maxAmmo ? (l(), u("Group", {
            key: 0,
            anchor: { Top: 30, Left: a.value.ammo.value < 10 ? -15 : 40 }
          }, [
            o("Label", {
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
            }, s(a.value.ammo), 9, ko),
            o("Label", {
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
            }, s(a.value.o?.maxAmmo), 9, vo)
          ], 8, bo)) : (l(), u("Group", fo, [
            o("Label", {
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
            }, "ME", 8, yo),
            o("Label", {
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
            }, "LEE", 8, Go)
          ]))
        ], 8, _o),
        t[10] || (t[10] = o("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      t[12] || (t[12] = o("Group", { anchor: { Full: 1 } }, null, -1))
    ], 64));
  }
});
c.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(c.__hmrId, c) || __VUE_HMR_RUNTIME__.reload(c.__hmrId, c));
export {
  c as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
