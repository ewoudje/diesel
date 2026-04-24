import { defineComponent as _, ref as n, openBlock as u, createElementBlock as g, Fragment as p, createElementVNode as t, toDisplayString as a, renderList as b } from "vue";
import "vt:@core/components/Common";
import "vt:@core/components/core";
import { useData as k } from "vt:@core/composables/useData";
const f = { anchor: { Left: 0, Top: 0 } }, v = ["background"], G = ["background"], I = ["background"], T = { anchor: { Left: -20, Bottom: -50 } }, y = { anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 } }, H = ["background"], x = ["background"], W = ["el-style"], L = { anchor: { Top: 0, Right: -35 } }, R = ["background"], B = ["el-style"], F = { anchor: { Right: 0 } }, E = ["background"], C = ["background"], N = ["background"], P = ["background"], S = { anchor: { Right: -20, Bottom: -52 } }, U = ["background"], A = ["background"], D = ["background"], M = { anchor: { Top: 10, Left: 40 } }, V = ["el-style"], z = ["el-style"], i = /* @__PURE__ */ _({
  __name: "DieselHud",
  setup(h) {
    console.log("[DIESELGAME] INIT VUE HUD");
    const e = n({
      textPrimary: "#FB0004",
      textSecondary: "#fc7b03",
      activeTextPrimary: "#FB0004"
    }), m = n({
      actor: "peeb",
      text: "not fast enough! chop chop!",
      voice: "/file/path"
    }), r = n("Img/empty.png");
    n("");
    const d = {
      ammo: 6,
      maxAmmo: 120
    }, s = n({
      text: "breach the palais"
    });
    n("");
    const c = n([
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
    ]);
    return k("test", "default value"), setTimeout(() => {
    }, 500), setInterval(() => {
    }), (w, o) => (u(), g(p, null, [
      o[9] || (o[9] = t("Group", {
        anchor: { Left: -50, Bottom: -35, Top: -25, Width: 200, Height: 440 },
        background: "Img/left.png"
      }, null, -1)),
      t("Group", f, [
        o[0] || (o[0] = t("Group", { anchor: { Left: -100, Top: -90, Width: 640, Height: 500 } }, [
          t("Group", { background: "Img/top_left.png" })
        ], -1)),
        t("Group", {
          anchor: { Left: 0, Top: 0, Width: 340, Height: 380 },
          background: { Color: e.value.activeTextPrimary },
          "mask-texture-path": "Img/health/scout1.png"
        }, null, 8, v),
        t("Group", {
          anchor: { Left: -20, Top: -20, Width: 350, Height: 500 },
          background: r.value
        }, [
          t("Group", { background: r.value }, null, 8, I)
        ], 8, G),
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
            "mask-texture-path": "Img/portrait/prole1.png"
          }, null, 8, H)
        ]),
        t("Group", {
          anchor: { Left: 400, Bottom: 100, Width: 530, Height: 100 },
          background: r.value
        }, [
          t("Label", {
            "el-style": {
              FontSize: 35,
              FontName: "Secondary",
              RenderUppercase: !0,
              RenderBold: !0,
              Wrap: !0,
              TextColor: e.value.activeTextPrimary
            }
          }, a(m.value.text), 9, W)
        ], 8, x),
        o[3] || (o[3] = t("Group", {
          anchor: { Left: -30, Bottom: 0, Width: 1e3, Height: 440 },
          background: "Img/bottom_left_wide_shine.png"
        }, null, -1))
      ]),
      t("Group", L, [
        o[4] || (o[4] = t("Group", { anchor: { Top: 0, Right: 80, Width: 700, Height: 220 } }, [
          t("Group", { background: "Img/top_right.png" })
        ], -1)),
        t("Group", {
          anchor: { Top: 10, Right: 170, Width: 580, Height: 90 },
          background: r.value
        }, [
          t("Label", {
            padding: { Full: 20 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              RenderUppercase: !0,
              RenderBold: !0,
              Wrap: !0,
              TextColor: e.value.activeTextPrimary
            }
          }, a(s.value.text), 9, B)
        ], 8, R),
        o[5] || (o[5] = t("Group", {
          anchor: { Top: 0, Right: 80, Width: 700, Height: 220 },
          background: "Img/top_right_shine.png"
        }, null, -1))
      ]),
      t("Group", F, [
        o[6] || (o[6] = t("Group", {
          anchor: { Right: -180, Top: -10, Bottom: 1, Width: 320 },
          background: "Img/right.png"
        }, null, -1)),
        t("Group", {
          anchor: { Right: 0, Top: 0, Bottom: 0, Width: 120 },
          background: r.value,
          "layout-mode": "Top"
        }, [
          (u(!0), g(p, null, b(c.value, (l) => (u(), g("Group", {
            anchor: { Left: 0, Top: 22, Width: 100, Height: 100 },
            background: l.active ? "Img/button_on_overlay.png" : "Img/button_off_overlay.png",
            "mask-texture-path": "Img/masks/button.png"
          }, [
            t("Group", {
              background: { Color: `${e.value.activeTextPrimary}(0.5)` }
            }, null, 8, N),
            t("Group", {
              background: l.active ? "Img/button_on_top_overlay.png" : "Img/empty.png"
            }, null, 8, P),
            t("Label", null, a(l.name), 1)
          ], 8, C))), 256))
        ], 8, E)
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
            "mask-texture-path": "Img/masks/bottom_right_mask.png"
          }, null, 8, A)
        ], 8, U),
        t("Group", {
          anchor: { Width: 300, Height: 300, Right: 33, Bottom: 30 },
          background: r.value
        }, [
          t("Group", M, [
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
            }, a(d.ammo), 9, V),
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
            }, a(d.maxAmmo), 9, z)
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
i.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(i.__hmrId, i) || __VUE_HMR_RUNTIME__.reload(i.__hmrId, i));
export {
  i as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
