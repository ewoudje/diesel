import { defineComponent as v, ref as l, openBlock as c, createBlock as i, unref as e, withCtx as m, createElementVNode as n, toDisplayString as a, createVNode as s, createCommentVNode as x } from "vue";
import { Common as r } from "vt:@core/components/Common";
import { Core as _ } from "vt:@core/components/core";
import { useData as g } from "vt:@core/composables/useData";
const V = {
  anchor: { Full: 1 },
  "layout-mode": "TopScrolling"
}, E = /* @__PURE__ */ v({
  __name: "TestPage",
  setup(h) {
    console.log("WORKS!");
    function d() {
      console.log("CLICKED ME"), o.value = !o.value;
    }
    const t = l("Hello!"), o = l(!1), p = l("nothing yet"), f = g("test", "default value");
    return (T, u) => (c(), i(e(r).DecoratedContainer, { anchor: { Width: 500, Height: 300 } }, {
      title: m(() => [
        s(e(r).Title, { text: "Vuetale Starter!" })
      ]),
      content: m(() => [
        n("Group", V, [
          n("Label", null, "Custom! " + a(p.value) + " | " + a(e(f)), 1),
          n("Label", null, a(t.value), 1),
          s(e(r).TextButton, {
            text: "CLICK ME",
            onActivating: d
          }),
          o.value ? (c(), i(e(_).TextField, {
            key: 0,
            modelValue: t.value,
            "onUpdate:modelValue": u[0] || (u[0] = (C) => t.value = C)
          }, null, 8, ["modelValue"])) : x("", !0)
        ])
      ]),
      _: 1
    }));
  }
});
export {
  E as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiVGVzdFBhZ2UudnVlLmpzIiwic291cmNlcyI6W10sInNvdXJjZXNDb250ZW50IjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7In0=
