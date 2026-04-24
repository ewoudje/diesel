import { defineComponent as p, ref as t, openBlock as f, createElementBlock as c, createElementVNode as r, toDisplayString as _, unref as l } from "vue";
import "vt:@core/components/Common";
import "vt:@core/components/core";
import { useData as i } from "vt:@core/composables/useData";
const m = {
  anchor: { Left: -20, Top: -20, Width: 350, Height: 500 },
  background: "Common/DropdownBox.png"
}, e = /* @__PURE__ */ p({
  __name: "TestPage",
  setup(n) {
    console.log("WORKS!"), t("Hello!"), t(!1);
    const a = t("nothing yet"), s = i("test", "default value");
    return (g, o) => (f(), c("Group", m, [
      r("Label", null, "Custom! " + _(a.value) + " | " + _(l(s)), 1),
      o[0] || (o[0] = r("ProgressBar", {
        anchor: { Top: 0, Left: 0, Width: 350, Height: 500 },
        background: "Img/progress_back.png",
        "bar-texture-path": "Img/progress_front.png",
        "effect-texture-path": "Img/progress_effect.png",
        "effect-height": 100,
        "effect-width": 100,
        value: 0.5,
        alignment: "Vertical",
        direction: "Start"
      }, null, -1))
    ]));
  }
});
e.__hmrId = "2af193c9";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(e.__hmrId, e) || __VUE_HMR_RUNTIME__.reload(e.__hmrId, e));
export {
  e as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiVGVzdFBhZ2UudnVlLmpzIiwic291cmNlcyI6W10sInNvdXJjZXNDb250ZW50IjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
