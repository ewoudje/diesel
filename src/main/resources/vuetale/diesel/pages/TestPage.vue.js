import { defineComponent as I, ref as n, openBlock as m, createBlock as c, unref as e, withCtx as i, createElementVNode as r, toDisplayString as a, createVNode as d, createCommentVNode as E } from "vue";
import { Common as _ } from "vt:@core/components/Common";
import { Core as V } from "vt:@core/components/core";
import { useData as h } from "vt:@core/composables/useData";
const x = {
  "layout-mode": "TopScrolling",
  anchor: { Full: 1 }
}, t = /* @__PURE__ */ I({
  __name: "TestPage",
  setup(s) {
    console.log("WORKS!");
    function p() {
      console.log("CLICKED ME"), l.value = !l.value;
    }
    const o = n("Hello!"), l = n(!1), f = n("nothing yet"), v = h("test", "default value");
    return (T, u) => (m(), c(e(_).DecoratedContainer, { anchor: { Width: 500, Height: 300 } }, {
      title: i(() => [
        d(e(_).Title, { text: "Vuetale Starter!" })
      ]),
      content: i(() => [
        r("Group", x, [
          r("Label", null, "Custom! " + a(f.value) + " | " + a(e(v)), 1),
          r("Label", null, a(o.value), 1),
          d(e(_).TextButton, {
            onActivating: p,
            text: "CLICK ME"
          }),
          l.value ? (m(), c(e(V).TextField, {
            key: 0,
            modelValue: o.value,
            "onUpdate:modelValue": u[0] || (u[0] = (C) => o.value = C)
          }, null, 8, ["modelValue"])) : E("", !0)
        ])
      ]),
      _: 1
    }));
  }
});
t.__hmrId = "7de778b2";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(t.__hmrId, t) || __VUE_HMR_RUNTIME__.reload(t.__hmrId, t));
export {
  t as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiVGVzdFBhZ2UudnVlLmpzIiwic291cmNlcyI6W10sInNvdXJjZXNDb250ZW50IjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OzsifQ==
