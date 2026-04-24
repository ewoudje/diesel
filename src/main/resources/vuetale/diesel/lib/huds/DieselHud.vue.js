import { defineComponent as a, ref as e, openBlock as p, createElementBlock as _, createElementVNode as t } from "vue";
import "vt:@core/components/Common";
import "vt:@core/components/core";
import { useData as m } from "vt:@core/composables/useData";
import { peeb as c } from "./diesel.js";
import { mix as f } from "../../node_modules/@popmotion/popcorn/dist/popcorn.es.js";
const i = {
  anchor: { Left: -20, Top: -20, Width: 350, Height: 500 },
  background: "Common/DropdownBox.png"
}, o = /* @__PURE__ */ a({
  __name: "DieselHud",
  setup(n) {
    return console.log("INIT VUE HUDs", c, f), e({
      actor: "peeb",
      text: "not fast enough! chop chop!",
      voice: "/file/path"
    }), e(5), e(null), e({
      text: "breach the palais"
    }), e(null), e("#ff1100"), e({
      textPrimary: "#ff1100",
      textSecondary: "#fc7b03",
      activeTextPrimary: "#ff1100"
    }), m("test", "default value"), setTimeout(() => {
      console.log("timeout aababa?as?se?");
    }, 500), (l, r) => (p(), _("Group", i, [...r[0] || (r[0] = [
      t("ProgressBar", {
        anchor: { Top: 0, Left: 0, Width: 350, Height: 500 },
        background: "Img/progress_back.png",
        "bar-texture-path": "Img/progress_front.png",
        "effect-texture-path": "Img/progress_effect.png",
        "effect-height": 100,
        "effect-width": 100,
        value: 1,
        alignment: "Vertical",
        direction: "Start"
      }, null, -1),
      t("Group", { background: "Common/DropdownBox.png" }, null, -1),
      t("Group", { background: "Common/DropdownBox.png" }, null, -1)
    ])]));
  }
});
o.__hmrId = "22684cca";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(o.__hmrId, o) || __VUE_HMR_RUNTIME__.reload(o.__hmrId, o));
export {
  o as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsSHVkLnZ1ZS5qcyIsInNvdXJjZXMiOltdLCJzb3VyY2VzQ29udGVudCI6W10sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
