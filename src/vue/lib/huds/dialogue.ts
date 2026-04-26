import type { ComputedRef, Ref } from "vue";
import { randomInt } from "./util";

//i dont even care anymore im not making a class
const actors = {
    tall_devil: {
        portrait:'partner',
        voice: 'dutch'
    },
    small_devil:{
        portrait:'ilduce',
        voice: 'french'
    },
    smaller_devil:{
        portrait: 'nilduce',
        voice: 'sadfrench'
    },
    fred:{
        portrait: 'fred',
        voice: 'dutch'
    },
    sod: {
        portrait: 'prole',
        voice: 'dutch'
    },
}

function playDialogue({ text, displayTextRef, displayPortraitRef, duration, actor, splitBy }: { 
    text: string; 
    displayTextRef: Ref; 
    displayPortraitRef: Ref;
    duration: number; 
    actor: string;
    splitBy: string
}){
    displayTextRef.value = ''
    let splitText = text.replace(/"/g,'').split(splitBy);
        let i = 0;
    printText(splitText, displayTextRef);

    console.log(actor)
    function printText(text: string[],destination: Ref){
        if(i<text.length){
            destination.value = `${destination.value} ${text[i]}` 
            displayPortraitRef.value = randomPortrait(actor);
            i++;
            setTimeout(()=>{printText(text,destination)},100);
        } else {
            //displayPortraitRef.value = 'Img/portrait/none.png'
        }
    }
    function randomPortrait(id: string){
        //@ts-ignore
       return `Img/portrait/${actors[id].portrait}${randomInt(1,3)}.png`
    }
}

export {playDialogue}