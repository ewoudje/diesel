import { defineComponent as M, computed as m, ref as _, watch as A, openBlock as l, createElementBlock as u, Fragment as b, createElementVNode as t, unref as p, toDisplayString as s, renderList as k } from "vue";
import { useData as r } from "vt:@core/composables/useData";
import { getPlayerClasses as z } from "./gameplayClasses.js";
import { hueRotateHex as I, LightenDarkenColor as T } from "./util.js";
import { playDialogue as D } from "./dialogue.js";
const V = { anchor: { Left: 0, Top: 0 } }, w = ["background", "value"], U = { anchor: { Left: -20, Bottom: -50, Height: 100 } }, P = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, j = ["background", "mask-texture-path"], O = ["background"], $ = ["el-style"], J = ["background"], K = ["background"], q = { anchor: { Top: 0, Right: -35 } }, Q = ["background"], X = ["el-style"], Y = ["el-style"], Z = { anchor: { Right: 0 } }, tt = ["background"], ot = ["background"], et = ["background"], nt = ["background"], at = ["background", "mask-texture-path"], rt = { anchor: { Left: 8 } }, lt = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, ut = ["background"], st = ["background"], it = ["background", "mask-texture-path"], gt = { anchor: { Left: 8 } }, ht = {
  anchor: { Left: 2 },
  "el-style": {
    FontName: "Secondary"
  }
}, pt = {
  "el-style": {
    RenderUppercase: !0,
    FontName: "Mono"
  },
  anchor: { Top: 78 }
}, dt = { anchor: { Right: -20, Bottom: -52 } }, ct = ["background"], mt = ["background", "mask-texture-path"], _t = ["background"], bt = ["anchor"], kt = ["el-style"], vt = ["anchor", "el-style"], ft = {
  key: 1,
  anchor: { Top: -30 },
  "layout-mode": "Top"
}, yt = ["el-style"], Gt = ["el-style"], d = /* @__PURE__ */ M({
  __name: "DieselHud",
  setup(C) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const v = r("message", ""), W = r("messageActor", "fred"), R = r("messageDuration", 4), B = r("splitBy", " ");
    r("current_overlay", "none");
    const F = r("dashes", 4), x = r("objective", ""), E = r("class", "scout"), c = r("hotbarIdx", 0), S = r("health", 1), N = m(() => E.value.toLowerCase()), i = m(() => z()[N.value]), a = m(() => ({
      ammo: r("ammo", 4),
      o: i.value.hotbar[c.value]
    })), f = _(null), e = m(() => {
      let h = f.value ?? i.value.baseColor;
      return {
        base: h,
        light: T(h, 200),
        dark: T(h, -30),
        alt: I(h, 40),
        brass: "#c98d1c",
        screen: "#282624"
      };
    }), y = _(""), G = _("Img/portrait/none.png");
    A(v, () => {
      let h = W.value.toString().toLowerCase();
      D({
        text: v.value,
        displayTextRef: y,
        displayPortraitRef: G,
        duration: R.value,
        actor: h,
        splitBy: B.value
      });
    });
    const g = _("Img/empty.png");
    let L = 0;
    return setInterval(() => {
      L++, f.value = I(i.value.baseColor, L);
    }, 10), (h, o) => (l(), u(b, null, [
      o[11] || (o[11] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", V, [
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
          value: 1 - p(S),
          alignment: "Vertical",
          direction: "End"
        }, null, 8, w),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", U, [
        t("Group", P, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.base },
            "mask-texture-path": G.value
          }, null, 8, j)
        ]),
        t("Group", {
          anchor: { Left: 400, Bottom: 100, Width: 530, Height: 100 },
          background: g.value
        }, [
          t("Label", {
            "el-style": {
              FontSize: 35,
              FontName: "Mono",
              Wrap: !0,
              TextColor: e.value.base
            }
          }, s(y.value), 9, $)
        ], 8, O),
        o[3] || (o[3] = t("Label", { "el-style": { FontSize: 1 } }, null, -1)),
        o[4] || (o[4] = t("Group", {
          anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 },
          background: "Img/bottom_left_wide_shine.png"
        }, null, -1)),
        t("Group", {
          anchor: { Left: 350, Bottom: 198, Height: 100, Width: 200 },
          background: g.value,
          "layout-mode": "Left"
        }, [
          (l(!0), u(b, null, k(i.value.maxDashes, (H, n) => (l(), u("Group", {
            key: n,
            anchor: { Width: 48, Height: 48, Left: 20 },
            background: n < p(F) ? "Img/charge_on.png" : "Img/charge_off.png",
            "mask-texture-path": "Img/mask/button.png"
          }, null, 8, K))), 128))
        ], 8, J)
      ]),
      t("Group", q, [
        o[5] || (o[5] = t("Group", { anchor: { Top: 0, Right: 80, Width: 700, Height: 220 } }, [
          t("Group", { background: "Img/top_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Top: 20, Right: 180, Width: 580, Height: 90 },
          background: g.value
        }, [
          t("Label", {
            anchor: { Left: 380, Top: 10 },
            "el-style": {
              FontSize: 30,
              FontName: "Mono",
              TextColor: e.value.base
            }
          }, " OBJECTIVE ", 8, X),
          t("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              Wrap: !0,
              TextColor: e.value.base,
              Alignment: "End"
            }
          }, s(p(x)), 9, Y)
        ], 8, Q),
        o[6] || (o[6] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", Z, [
        o[8] || (o[8] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: g.value,
          "layout-mode": "Top"
        }, [
          (l(!0), u(b, null, k(i.value.hotbar, (H, n) => (l(), u("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: p(c) == n ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.base}(0.5)` }
            }, null, 8, et),
            t("Group", {
              background: p(c) == n ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, nt),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: p(c) == n ? { Color: e.value.dark } : { Color: e.value.light },
              "mask-texture-path": i.value.hotbar[n]?.iconPath
            }, null, 8, at),
            t("Group", rt, [
              t("Label", lt, s(n + 1) + "/" + s(i.value.hotbar[n]?.name), 1)
            ])
          ], 8, ot))), 128))
        ], 8, tt),
        t("Group", {
          anchor: { Right: 0, Top: 280, Bottom: 0, Width: 120 },
          background: g.value,
          "layout-mode": "Top"
        }, [
          (l(!0), u(b, null, k(a.value.o?.abilities, (H, n) => (l(), u("Group", {
            key: n,
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: "Img/button_off_overlay.png",
            "mask-texture-path": "Img/mask/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.alt}(0.3)` }
            }, null, 8, st),
            o[7] || (o[7] = t("Group", { background: "Img/empty.png" }, null, -1)),
            t("Group", {
              anchor: { Width: 60, Height: 60 },
              background: { Color: e.value.light },
              "mask-texture-path": a.value.o?.abilities[n]?.iconPath
            }, null, 8, it),
            t("Group", gt, [
              t("Label", ht, s(a.value.o?.abilities[n].name), 1),
              t("Label", pt, s(a.value.o?.abilities[n].keybind), 1)
            ])
          ]))), 128))
        ], 8, ut)
      ]),
      t("Group", dt, [
        o[9] || (o[9] = t("Group", { anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 } }, [
          t("Group", { background: "Img/bottom_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Right: 250, Bottom: 0, Width: 400, Height: 150 },
          background: g.value
        }, [
          t("Group", {
            anchor: { Left: -20, Bottom: 62, Width: 400, Height: 130 },
            background: { Color: e.value.base },
            "mask-texture-path": a.value.o?.previewPath
          }, null, 8, mt)
        ], 8, ct),
        t("Group", {
          anchor: { Width: 600, Height: 300, Right: -90, Bottom: 30 },
          background: g.value
        }, [
          a.value.o?.maxAmmo ? (l(), u("Group", {
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
            }, s(a.value.ammo), 9, kt),
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
            }, s(a.value.o?.maxAmmo), 9, vt)
          ], 8, bt)) : (l(), u("Group", ft, [
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
            }, "ME", 8, yt),
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
            }, "LEE", 8, Gt)
          ]))
        ], 8, _t),
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
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
