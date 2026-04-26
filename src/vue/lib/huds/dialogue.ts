import type { ComputedRef, Ref } from "vue";
import { randomInt } from "./util";
//dialogue
//so clunky but genuinely i have no time to think about things and make this nicely. the moon is going to hit termina in like two days bro
//im just coding javascript idec

//bar qnl ratvar jvyy pbzr onpx naq gura v jbag unir gb jbeel nobhg guvf fuvg nalzber ohg hagvy gura v whfg unir gb xrrc qevaxvat pbssrr
//yvgrenyyl qbag rira pner nal zber shpx lbh im not making a class
const actors = {
    partner: {
        portrait:'prole',
        voice: 'Other'
    },
    ilduce:{
        portrait:'ilduce',
        voice: 'Upset'
    },
    nilduce:{
        portrait: 'nilduce',
        voice: 'Scared'
    },
    fred:{
        portrait: 'partner',
        voice: 'Scared'
    },
    sod: {
        portrait: 'prole',
        voice: 'Other'
    },
}



let inDialogue = false;
//@ts-ignore
let dialogueTimeout = null;


function playDialogue({ text, displayTextRef, displayPortraitRef, speed, actor, chainObj, chainIndex, chainDelay, playSoundRef }: { 
    text: string; 
    displayTextRef: Ref; 
    displayPortraitRef: Ref;
    speed: number; 
    actor: string;
    chainObj: (number | string)[][];
    chainIndex: number;
    chainDelay: number;
    playSoundRef: Ref
}){
    console.log("test")
    if(inDialogue){
        //@ts-ignore
        clearTimeout(dialogueTimeout)
    }
    inDialogue = true;
    displayTextRef.value = ''
    let splitText = text.replace(/"/g,'').split(' ');
        let i = 0;
    printText(splitText, displayTextRef);

    console.log(actor)
    function printText(text: string[],destination: Ref){
        if(i<text.length){
            //@ts-ignore
            if((i+2)%2==0)            playSoundRef.value(actors[actor].voice);

            destination.value = `${destination.value} ${text[i]}` 
            displayPortraitRef.value = randomPortrait(actor);
            i++;
            dialogueTimeout = setTimeout(()=>{printText(text,destination)},100);
        } else {
            //displayPortraitRef.value = 'Img/portrait/none.png'
            chainIndex++
            if(chainIndex<chainObj.length){
                setTimeout(()=>{
                    playDialogue({
                        //@ts-ignore //lol
                        actor: chainObj[chainIndex][0],
                        //@ts-ignore //lmao
                        text: chainObj[chainIndex][1],
                        //@ts-ignore //kek
                        speed: chainObj[chainIndex][2],
                        //@ts-ignore // )00))))00)
                        chainDelay: chainObj[chainIndex][3],
                        //@ts-ignore //wxnstunxwfqsunwxfqus
                        chainIndex: chainIndex,
                        chainObj: chainObj,
                        displayPortraitRef: displayPortraitRef,
                        displayTextRef: displayTextRef,
                        playSoundRef: playSoundRef
                    })
                }, chainDelay)
            } else {
                setTimeout(()=>{
                    displayPortraitRef.value = 'Img/portrait/none.png';
                    displayTextRef.value = ''
                },chainDelay)
            }

        }
    }
    function randomPortrait(id: string){
        //@ts-ignore
       return `Img/portrait/${actors[id].portrait}${randomInt(1,3)}.png`
    }
}

//dialogue chain
//play dialogue, then when it's over, for x time and move to the next
//if it's interrupted, check if it's marked important. if it isn't then override it, if it is then don't start it
//TODO FIX HCAIN OVERRIDES
function playChain(chainName: string, displayTextRef:Ref, displayPortraitRef:Ref, playSoundRef:Ref){
    //@ts-ignore //shut UP shut UP shut UP shut UP shut UP shut UP i wanna go HOME
    let chainObj = chains[chainName];
    let index = 0;
    playDialogue({
        actor: chainObj[index][0],
        text: chainObj[index][1],
        speed: chainObj[index][2],
        chainDelay: chainObj[index][3],
        chainIndex: index,
        chainObj: chainObj,
        displayPortraitRef: displayPortraitRef,
        displayTextRef: displayTextRef,
        playSoundRef: playSoundRef
    })
}

//actor, text, speed, delay (before next message), important 
const chains = {
    testchain: [
        ["sod", "i'm walkin' ere!", 100, 1000],
        ["sod", "you gotta watch where you're going, pal", 100, 1000],
        ["sod", "jeez. god", 100, 1000],
        ["sod", "state of this city, i'm tellin' ya", 100, 1000],
        ["sod", "you'd think we was Chasm Cataluña or somethin'", 100, 1000]
    ],
    planeDownFred: [
        ["fred","OUURUUGH!!!!", 100, 500],
        ["fred","RIGHT IN THE SPOPPEGT", 100, 1000]
    ],
    planeAttackFred: [
        ["fred","For the board!!!", 100, 500],
    ],   
    dukat: [
        ["partner","Attention, Wallonian workers.",100,1000],
        ["partner","It has come to my attention that a handful of layabouts have...",100,1500],
        ["partner","...undertaken an unfortunate exercise.",500,1000],
        ["partner","I implore you reconsider.",200,1000],
        ["partner","It is a matter of the city's productivity: remember this;",100,1000],
        ["partner","A productive Wallonia is a unified Wallonia is a safe Wallonia.",100,1000],
        ["partner","This in mind...",100,2000],
        ["partner","Any workers who aid in returning the dissidents",100,1000],
        ["partner","to their senses will be...",100,1500],
        ["partner","...appropriately rewarded.",100,3000],
        ["fred","fie.",100,2000],
        ["fred","inconvenient, but i pay more than the board",100,1000],
        ["fred","how do they say it in the academiate?",100,3000],
        ["fred","ah, yes, I recall.",200,1500],
        ["fred","go get 'em, tiger",200,3000]
    ]
}
export {playChain, playDialogue}