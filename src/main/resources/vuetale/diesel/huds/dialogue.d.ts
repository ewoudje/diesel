import type {Ref} from "vue";

declare function playDialogue({
                                  text,
                                  displayTextRef,
                                  displayPortraitRef,
                                  speed,
                                  actor,
                                  chainObj,
                                  chainIndex,
                                  chainDelay,
                                  playSoundRef,
                                  setLogicRef,
                                  chainName
                              }: {
    text: string;
    displayTextRef: Ref;
    displayPortraitRef: Ref;
    speed: number;
    actor: string;
    chainObj: (number | string)[][];
    chainIndex: number;
    chainDelay: number;
    playSoundRef: Ref;
    setLogicRef: Ref;
    chainName: String;
}): void;

declare function playChain(chainName: string, displayTextRef: Ref, displayPortraitRef: Ref, playSoundRef: Ref, setLogicRef: Ref): void;

export {playChain, playDialogue};
