import type { Ref } from "vue";
declare function playDialogue({ text, displayTextRef, displayPortraitRef, speed, actor, chainObj, chainIndex, chainDelay }: {
    text: string;
    displayTextRef: Ref;
    displayPortraitRef: Ref;
    speed: number;
    actor: string;
    chainObj: (number | string)[][];
    chainIndex: number;
    chainDelay: number;
}): void;
declare function playChain(chainName: string, displayTextRef: Ref, displayPortraitRef: Ref): void;
export { playChain, playDialogue };
