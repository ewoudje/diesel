import { defineComponent as h, ref as r, openBlock as _, createElementBlock as f, Fragment as x, createElementVNode as t, toDisplayString as n, unref as B } from "vue";
import "vt:@core/components/Common";
import "vt:@core/components/core";
import { useData as v } from "vt:@core/composables/useData";
const T = { anchor: { Left: -20, Bottom: -20 } }, y = {
  anchor: { Left: 250, Bottom: 0, Width: 700, Height: 200 },
  background: "Common/DropdownBox.png"
}, F = ["el-style"], b = { anchor: { Right: -20, Bottom: -20 } }, D = {
  anchor: { Width: 300, Height: 300, Right: 0, Bottom: 0 },
  background: "Common/DropdownBox.png"
}, C = { anchor: { Top: 20, Left: 5 } }, H = ["el-style"], R = ["el-style"], W = {
  anchor: { Top: -20, Right: -20, Width: 800, Height: 120 },
  background: "Common/DropdownBox.png"
}, k = ["el-style"], a = /* @__PURE__ */ h({
  __name: "DieselHud",
  setup(p) {
    console.log("INIT VUE HUDs");
    const d = r({
      actor: "peeb",
      text: "not fast enough! chop chop!",
      voice: "/file/path"
    });
    r(5), r("");
    const l = {
      ammo: 6,
      maxAmmo: 120
    }, m = r({
      text: "breach the paalais"
    });
    r("");
    const e = r({
      textPrimary: "#FB0004",
      textSecondary: "#fc7b03",
      activeTextPrimary: "#FB0004"
    }), c = v("test", "default value");
    setTimeout(() => {
      u();
    }, 500), setInterval(() => {
    });
    let s = !1;
    function u() {
      s = !0;
      const i = [
        "#FB0004",
        "#DF2E00",
        "#FB0004",
        "#AF3D00",
        "#9A3D00"
      ];
      let o = 0;
      g();
      function g() {
        o++, o >= i.length - 1 && (e.value.activeTextPrimary = e.value.textPrimary, o = 0), i[o] && (e.value.activeTextPrimary = i[o]);
      }
    }
    return (i, o) => (_(), f(x, null, [
      o[2] || (o[2] = t("Group", {
        anchor: { Left: -20, Top: -20, Width: 350, Height: 500 },
        background: "Common/DropdownBox.png"
      }, [
        t("ProgressBar", {
          anchor: { Top: 0, Left: 0, Width: 350, Height: 500 },
          background: "Img/progress_back.png",
          "bar-texture-path": "Img/progress_front.png",
          "effect-texture-path": "Img/progress_effect.png",
          "effect-height": 100,
          "effect-width": 100,
          value: 0.5,
          alignment: "Vertical",
          direction: "Start"
        }),
        t("Group", { background: "Common/DropdownBox.png" }),
        t("Group", { background: "Common/DropdownBox.png" })
      ], -1)),
      o[3] || (o[3] = t("Group", {
        anchor: { Right: -20, Top: 0, Bottom: 0, Width: 120 },
        background: "Common/DropdownBox.png"
      }, null, -1)),
      t("Group", T, [
        t("Group", y, [
          t("Label", {
            anchor: { Left: 50, Width: 600 },
            padding: { Full: 20 },
            "el-style": {
              FontSize: 43,
              FontName: "Secondary",
              RenderUppercase: !0,
              RenderBold: !0,
              Wrap: !0,
              TextColor: e.value.activeTextPrimary
            }
          }, n(d.value.text) + " " + n(B(c)), 9, F)
        ]),
        o[0] || (o[0] = t("Group", {
          anchor: { Left: 0, Bottom: 0, Width: 300, Height: 300 },
          background: "Common/DropdownBox.png"
        }, null, -1))
      ]),
      t("Group", b, [
        o[1] || (o[1] = t("Group", {
          anchor: { Right: 250, Bottom: 0, Width: 400, Height: 150 },
          background: "Common/DropdownBox.png"
        }, null, -1)),
        t("Group", D, [
          t("Group", C, [
            t("Label", {
              anchor: { Top: 10, Left: 0, Width: 300, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: 210,
                FontName: "Default",
                HorizontalAlignment: "Start",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.activeTextPrimary
              }
            }, n(l.ammo), 9, H),
            t("Label", {
              anchor: { Top: 100, Left: 80, Width: 200, Height: 200 },
              padding: { Full: 20 },
              "el-style": {
                FontSize: 60,
                FontName: "Default",
                HorizontalAlignment: "End",
                VerticalAlignment: "Center",
                Wrap: !1,
                TextColor: e.value.activeTextPrimary
              }
            }, n(l.maxAmmo), 9, R)
          ])
        ])
      ]),
      t("Group", W, [
        t("Label", {
          anchor: { Top: 20, Width: 600, Left: 20 },
          padding: { Full: 20 },
          "el-style": {
            FontSize: 43,
            FontName: "Secondary",
            RenderUppercase: !0,
            RenderBold: !0,
            Wrap: !0,
            TextColor: e.value.activeTextPrimary
          }
        }, n(m.value.text), 9, k)
      ]),
      o[4] || (o[4] = t("Group", { anchor: { Full: 1 } }, null, -1))
    ], 64));
  }
});
a.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(a.__hmrId, a) || __VUE_HMR_RUNTIME__.reload(a.__hmrId, a));
export {
  a as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
