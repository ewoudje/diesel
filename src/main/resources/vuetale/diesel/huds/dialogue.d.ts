import type { Ref } from "vue";
declare function playDialogue({ text, displayTextRef, displayPortraitRef, duration, actor, splitBy }: {
    text: string;
    displayTextRef: Ref;
    displayPortraitRef: Ref;
    duration: number;
    actor: string;
    splitBy: string;
}): void;
export { playDialogue };
