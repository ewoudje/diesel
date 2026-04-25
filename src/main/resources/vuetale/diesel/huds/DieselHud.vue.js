import { defineComponent as f, ref as n, openBlock as i, createElementBlock as l, Fragment as h, createElementVNode as t, toDisplayString as u, unref as k, renderList as p } from "vue";
import "vt:@core/components/Common";
import "vt:@core/components/core";
import { useData as v } from "vt:@core/composables/useData";
const I = { anchor: { Left: 0, Top: 0 } }, G = ["background"], T = { anchor: { Left: -20, Bottom: -50 } }, y = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, x = ["background"], H = ["background"], W = ["el-style"], L = ["background"], B = ["background"], R = { anchor: { Top: 0, Right: -35 } }, F = ["background"], E = ["el-style"], C = { anchor: { Right: 0 } }, N = ["background"], P = ["background"], U = ["background"], M = ["background"], S = { anchor: { Right: -20, Bottom: -52 } }, V = ["background"], A = ["background"], D = ["background"], w = { anchor: { Top: 10, Left: 40 } }, z = ["el-style"], j = ["el-style"], g = /* @__PURE__ */ f({
  __name: "DieselHud",
  setup(s) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const e = n({
      textPrimary: "#FB0004",
      textSecondary: "#fc7b03",
      activeTextPrimary: "#FB0004"
    });
    n({
      actor: "peeb",
      text: "not fast enough! chop chop!",
      voice: "/file/path"
    });
    const r = n("Img/empty.png");
    n("");
    const d = {
      ammo: 6,
      maxAmmo: 120
    }, m = n({
      text: "board the avrocar"
    }), c = n({
      name: "scout",
      dashes: [!0, !0, !1, !1]
    }), _ = n([
      {
        name: "1",
        active: !0,
        type: "item",
        detailImage: "",
        color: null
      },
      {
        name: "2",
        active: !1,
        type: "item",
        detailImage: "",
        color: null
      },
      {
        name: "3",
        active: !1,
        type: "ability",
        detailImage: "",
        color: null
      },
      {
        name: "4",
        active: !1,
        type: "ability",
        detailImage: "",
        color: null
      }
    ]), b = v("test", "default value");
    return setTimeout(() => {
    }, 500), ($, o) => (i(), l(h, null, [
      o[9] || (o[9] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", I, [
        o[0] || (o[0] = t("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          t("Group", { background: "Img/top_left.png" })
        ], -1)),
        t("ProgressBar", {
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: e.value.activeTextPrimary },
          "mask-texture-path": "Img/health/scout1.png",
          "bar-texture-path": "Img/solid.png",
          x: "",
          "effect-height": 100,
          "effect-width": 100,
          value: 0.5,
          alignment: "Vertical",
          direction: "End"
        }, null, 8, G),
        o[1] || (o[1] = t("Group", {
          anchor: { Left: -100, Top: -90, Width: 640, Height: 500 },
          background: "Img/top_left_shine.png"
        }, null, -1))
      ]),
      t("Group", T, [
        t("Group", y, [
          o[2] || (o[2] = t("Group", { background: "Img/bottom_left_wide.png" }, null, -1)),
          t("Group", {
            anchor: { Left: 60, Bottom: 22, Width: 320, Height: 320 },
            background: { Color: e.value.activeTextPrimary },
            "mask-texture-path": "Img/portrait/partner3.png"
          }, null, 8, x)
        ]),
        t("Group", {
          anchor: { Left: 400, Bottom: 100, Width: 530, Height: 100 },
          background: r.value
        }, [
          t("Label", {
            "el-style": {
              FontSize: 35,
              FontName: "Mono",
              RenderUppercase: !0,
              Wrap: !0,
              TextColor: e.value.activeTextPrimary
            }
          }, u(k(b)), 9, W)
        ], 8, H),
        o[3] || (o[3] = t("Group", {
          anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 },
          background: "Img/bottom_left_wide_shine.png"
        }, null, -1)),
        t("Group", {
          anchor: { Left: 350, Bottom: 198, Height: 100, Width: 200 },
          background: r.value,
          "layout-mode": "Left"
        }, [
          (i(!0), l(h, null, p(c.value.dashes, (a) => (i(), l("Group", {
            anchor: { Width: 48, Height: 48, Left: 20 },
            background: a ? "Img/charge_on.png" : "Img/charge_off.png",
            "mask-texture-path": "Img/masks/button.png"
          }, null, 8, B))), 256))
        ], 8, L)
      ]),
      t("Group", R, [
        o[4] || (o[4] = t("Group", { anchor: { Top: 0, Right: 80, Width: 700, Height: 220 } }, [
          t("Group", { background: "Img/top_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Top: 12, Right: 180, Width: 580, Height: 90 },
          background: r.value
        }, [
          t("Label", {
            padding: { Full: 10 },
            "el-style": {
              FontSize: 43,
              FontName: "Mono",
              RenderUppercase: !0,
              Wrap: !0,
              TextColor: e.value.activeTextPrimary
            }
          }, u(m.value.text), 9, E)
        ], 8, F),
        o[5] || (o[5] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", C, [
        o[6] || (o[6] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: r.value,
          "layout-mode": "Top"
        }, [
          (i(!0), l(h, null, p(_.value, (a) => (i(), l("Group", {
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: a ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/masks/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.activeTextPrimary}(0.5)` }
            }, null, 8, U),
            t("Group", {
              background: a.active ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, M),
            t("Label", null, u(a.name), 1)
          ], 8, P))), 256))
        ], 8, N)
      ]),
      t("Group", S, [
        o[7] || (o[7] = t("Group", { anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 } }, [
          t("Group", { background: "Img/bottom_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Right: 250, Bottom: 0, Width: 400, Height: 150 },
          background: r.value
        }, [
          t("Group", {
            anchor: { Left: -20, Bottom: 62, Width: 400, Height: 130 },
            background: { Color: e.value.activeTextPrimary },
            "mask-texture-path": "Img/item/shotgun.png"
          }, null, 8, A)
        ], 8, V),
        t("Group", {
          anchor: { Width: 300, Height: 300, Right: 33, Bottom: 30 },
          background: r.value
        }, [
          t("Group", w, [
            t("Label", {
              anchor: { Top: 10, Left: 0, Width: 300, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: 170,
                FontName: "Default",
                HorizontalAlignment: "Start",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.activeTextPrimary
              }
            }, u(d.ammo), 9, z),
            t("Label", {
              anchor: { Top: 100, Left: 10, Width: 200, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: 50,
                FontName: "Default",
                HorizontalAlignment: "End",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.activeTextPrimary
              }
            }, u(d.maxAmmo), 9, j)
          ])
        ], 8, D),
        o[8] || (o[8] = t("Group", {
          anchor: { Bottom: 0, Right: -90, Width: 800, Height: 450 },
          background: "Img/bottom_right_shine.png"
        }, null, -1))
      ]),
      o[10] || (o[10] = t("Group", { anchor: { Full: 1 } }, null, -1))
    ], 64));
  }
});
g.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(g.__hmrId, g) || __VUE_HMR_RUNTIME__.reload(g.__hmrId, g));
export {
  g as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
