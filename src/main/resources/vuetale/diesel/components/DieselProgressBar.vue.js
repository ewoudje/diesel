import {
    createElementBlock as s,
    createElementVNode as t,
    defineComponent as o,
    openBlock as n,
    toDisplayString as _
} from "vue";

const a = ["background", "value"], i = {
    "el-style": {
        FontName: "Mono"
    }
}, e = /* @__PURE__ */ o({
    __name: "DieselProgressBar",
    props: {
        inputProgress1: Number,
        inputProgress2: Number,
        inputProgress1Label: String,
        inputProgress2Label: String,
        baseColor: String
    },
    setup(r) {
        return (l, u) => (n(), s("Group", null, [
            t("ProgressBar", {
                anchor: {Left: 0, Top: 0, Width: 600, Height: 50},
                background: {baseColor: r.baseColor},
                "mask-texture-path": "Img/bossbar.png",
                "bar-texture-path": "Img/solid.png",
                "effect-height": 100,
                "effect-width": 100,
                value: r.inputProgress1,
                direction: "Start"
            }, null, 8, a),
            t("Label", i, _(r.inputProgress1Label), 1)
        ]));
    }
});
e.__hmrId = "6e277889";
typeof __VUE_HMR_RUNTIME__ < "u" && (__VUE_HMR_RUNTIME__.createRecord(e.__hmrId, e) || __VUE_HMR_RUNTIME__.reload(e.__hmrId, e));
export {
    e as default
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiRGllc2VsUHJvZ3Jlc3NCYXIudnVlLmpzIiwic291cmNlcyI6W10sInNvdXJjZXNDb250ZW50IjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
