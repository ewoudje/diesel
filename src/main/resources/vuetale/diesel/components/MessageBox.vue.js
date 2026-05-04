import { defineComponent as o, openBlock as n, createElementBlock as r, createElementVNode as s, toDisplayString as a } from "vue";
const c = { anchor: { Full: 1, Left: 0, Right: 0 } }, _ = /* @__PURE__ */ o({
  __name: "MessageBox",
  props: {
    test: {}
  },
  setup(e) {
    const t = e;
    return (l, p) => (n(), r("Group", c, [
      s("Label", null, "Example " + a(t.test), 1)
    ]));
  }
});
export {
  _ as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiTWVzc2FnZUJveC52dWUuanMiLCJzb3VyY2VzIjpbXSwic291cmNlc0NvbnRlbnQiOltdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7OyJ9
