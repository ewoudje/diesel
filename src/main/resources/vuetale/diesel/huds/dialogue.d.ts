import type { Ref } from "vue";
declare function playDialogue({ text, displayTextRef, displayPortraitRef, speed, actor, chainObj, chainIndex, chainDelay, playSoundRef }: {
    text: string;
    displayTextRef: Ref;
    displayPortraitRef: Ref;
    speed: number;
    actor: string;
    chainObj: (number | string)[][];
    chainIndex: number;
    chainDelay: number;
    playSoundRef: Ref;
}): void;
declare function playChain(chainName: string, displayTextRef: Ref, displayPortraitRef: Ref, playSoundRef: Ref): void;
export { playChain, playDialogue };
