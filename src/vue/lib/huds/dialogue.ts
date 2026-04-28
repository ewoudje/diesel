import type { ComputedRef, Ref } from "vue";
import { randomInt } from "./util";
//dialogue
//so clunky but genuinely i have no time to think about things and make this nicely. the moon is going to hit termina in like two days bro
//im just coding javascript idec

//bar qnl ratvar jvyy pbzr onpx naq gura v jbag unir gb jbeel nobhg guvf fuvg nalzber ohg hagvy gura v whfg unir gb xrrc qevaxvat pbssrr
//yvgrenyyl qbag rira pner nal zber shpx lbh im not making a class
const actors = {
    partner: {
        portraits: 3,
        portrait:'partner',
        interval: 4,
        voice: 'Afrenchevil'
    },
    partnercalm: {
        portraits: 3,
        portrait:'partner',
        interval: 4,
        voice: 'Afrenchcalm'
    },
    partnersad: {
        portraits: 3,
        portrait:'partner',
        interval: 4,
        voice: 'Cry'
    },
    partnersilent: {
        portraits: 3,
        portrait:'partner',
        interval: 4,
        voice: 'Silent'
    },
    evilduce:{
        portraits: 4,
        portrait: 'duce',
        voice: 'Chairmancalm'
    },
    ilduce:{
        portraits: 3,
        portrait:'nilduce',
        voice: 'Chairmanupset'
    },
    nilduce:{
        portraits: 3,
        portrait: 'nilduce',
        voice: 'Chairmanscared'
    },
    fredcalm:{
        portraits: 3,
        portrait: 'fred',
        voice: 'Fredcalm'
    },
    fred:{
        portraits: 3,
        portrait: 'fred',
        voice: 'Fredangry'
    },
    sod: {
        portraits: 3,
        portrait: 'prole',
        voice: 'Fredcalm'
    },
    boxin: {
        portraits: 1,
        interval: 3,
        portrait: 'boxin',
        voice: 'Germanangry'
    },
    avrocar: {
        portraits:1,
        portrait: 'none',
        interval:5,
        voice:'Bfrenchangry',
    },
    elevator: {
        portraits:1,
        interval:10,
        portrait: 'none',
        voice: 'Bfrenchcalm'
    }
}



let inDialogue = false;
//@ts-ignore
let dialogueTimeout = null;


function playDialogue({ text, displayTextRef, displayPortraitRef, speed, actor, chainObj, chainIndex, chainDelay, playSoundRef, setLogicRef, chainName }: {
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
    chainName: String
}){
    //@ts-ignore
    if(dialogueTimeout){
        //@ts-ignore
        clearTimeout(dialogueTimeout)
    }
    inDialogue = true;
    displayTextRef.value = ''
    let splitText = text.replace(/"/g,'').split(' ');
        let i = 0;
    printText(splitText, displayTextRef);
    function printText(text: string[],destination: Ref){
        if(i<text.length){
            //@ts-ignore
            if(actors[actor].voice != 'None' && ((i+actors[actor].interval || 2)%(actors[actor].interval || 2 )==0))             playSoundRef.value(actors[actor].voice);

            destination.value = `${destination.value} ${text[i]}`
            displayPortraitRef.value = randomPortrait(actor);
            i++;
            dialogueTimeout = setTimeout(()=>{printText(text,destination)},100);
        } else {
            //displayPortraitRef.value = 'Img/portrait/none1.png'
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
                        playSoundRef: playSoundRef,
                        setLogicRef: setLogicRef,
                        chainName: chainName
                    })
                }, chainDelay)
            } else {
                setTimeout(()=>{
                    console.log(`[DIESELHUD] Chain ${chainName} complete`)
                    setLogicRef.value(chainName, "Done")
                    console.log("portrait ref")
                    displayPortraitRef.value = 'Img/portrait/none1.png';
                    displayTextRef.value = ''
                },chainDelay)
            }

        }
    }
    function randomPortrait(id: string){
        //@ts-ignore
       return `Img/portrait/${actors[id].portrait}${randomInt(1,actors[id].portraits)}.png`
    }
}

//dialogue chain
//play dialogue, then when it's over, for x time and move to the next
//if it's interrupted, check if it's marked important. if it isn't then override it, if it is then don't start it
//TODO FIX HCAIN OVERRIDES
function playChain(chainName: string, displayTextRef:Ref, displayPortraitRef:Ref, playSoundRef:Ref, setLogicRef:Ref){
    //@ts-ignore //shut UP shut UP shut UP shut UP shut UP shut UP i wanna go HOME
    let chainObj;
    //@ts-ignore
    chainObj = chains[chainName]
    /*(if(!chainName.startsWith("death.")){
        //@ts-ignore FUCK
        chainObj = chains[chainName];
        //@ts-ignore GET OUT OF MY HEAD
    } else if(deathChains[chainName].length>0){
        //@ts-ignore
        console.log(randomInt(0,deathChains[chainName].length))
        //@ts-ignore
        console.log(deathChains[chainName][randomInt(0,deathChains[chainName].length-1)]);
                //@ts-ignore

        chainObj =deathChains[chainName][randomInt(0,deathChains[chainName].length-1)];
    }*/
    if(chainObj){
        let index = 0;
        console.log(`[DIESELHUD] Starting chain ${chainName}`)
        playDialogue({
            actor: chainObj[index][0],
            text: chainObj[index][1],
            speed: chainObj[index][2],
            chainDelay: chainObj[index][3],
            chainIndex: index,
            chainObj: chainObj,
            displayPortraitRef: displayPortraitRef,
            displayTextRef: displayTextRef,
            playSoundRef: playSoundRef,
            setLogicRef: setLogicRef,
            chainName: chainName
        })
    } else {
        console.log(`bad chain not real ${chainName}`)
    }

}

//actor, text, speed, delay (before next message), important
const chains = {

    //BRIEFING
    'level.Briefing': [
        ["partner", "ah, wonderful, you've arrived", 100, 1200],
        ["partner","my apologies for the... ",100,1300],
        ["partner","squalor.",100,1000],
        ["partnercalm","the accomodations are meager, but then again!",100,1000],
        ["partner","i have hired you to rectify this. how fortunate",100,1000],
        ["partner","come, look with me, see the city...",100,1000],
        ["partner","it is beautiful, no?",100,2000],
        ["partner","it should be mine.",100,1000],
        ["partner","in the years since i was ousted from the board of directors",100,1000],
        ["partner","cloud wallonië has... fallen so far...",100,1000],
        ["partnersad","(sniff) even though we are in the sky.",100,1500],
        ["partner","ahem",100,300],
        ["partner","i digress",100,500],
        ["partner","you, my enterprising mercenaries, are here to infiltrate",100,500],
        ["partner","the corporate headquarters of the empire.",100,1000],
        ["partner","you will be very sneaky and assassinate",100,500],
        ["partner","my traitorous business partner.",100,1000],
        ["partner","wait, why did you all bring shotguns",100,1000],
        ["partner","the advertisement was very explicit",100,500],
        ["partner","about discretion... no matter",100,1000],
        ["partner","i have acquired an avrocar for you",100,1000],
        ["partner","it is waiting on the corner of Rivelaine and Longue",100,1000],
        ["partner","i will stay in contact via radio",100,1000],
        ["partner","to remind you of your generous payment",100,500],
        ["partner","and ensure you do not forget...",100,1000],
        ["partner","who has your self-destruct keys.",100,1000],
        ["partner","you may exit the way you came in.",100,1000],
        ["partnercalm","good luck, my friends.",100,10000],
        ["evilduce","Attention, Wallonian workers.",100,1000],
        ["evilduce","It has come to my attention that a handful of layabouts have...",100,1500],
        ["evilduce","...undertaken an unfortunate exercise.",500,1000],
        ["evilduce","I implore you reconsider.",200,1000],
        ["evilduce","It is a matter of the city's productivity: remember this;",100,1000],
        ["evilduce","A productive Wallonia is a unified Wallonia is a safe Wallonia.",100,1000],
        ["evilduce","This in mind...",100,2000],
        ["evilduce","Any workers who aid in returning the dissidents",100,1000],
        ["evilduce","to their senses will be...",100,1500],
        ["evilduce","...appropriately rewarded.",100,3000],
        ["partnercalm","oh dear",100,1000],
        ["partnercalm","they are amassing at the gates already",100,1000],
        ["partner","perhaps our communications are not so secure as i believed",100,2000],
        ["partner","fie",100,1000],
        ["partner","whatever",100,1000],
        ["partner","you brought shotguns any-how",100,1000],
        ["partner","unfortunately... ",100,500],
        ["partner","shotguns or no, i will not have my fishery ransacked",100,1000],
        ["partner","you will take the erudite exit so you may depart unseen",100,1000],
        ["partner","...now if only i could remember where the lever was...",100,1000]
    ],
    'lever':[
        ["partner","ah! very good",100,1000]
    ],
    'elevator':[
        ["elevator","thank you for choosing sea wallonië!",100,1000],
        ["elevator","our fish are so fish!",100,1000],
        ["elevator","from the vat to the sky to your fuel intake port",100,1000],
        ["elevator","flipping and flopping things from all over the genome",100,1000],
        ["elevator","we are committed to staying one hundred and one percent artificial!",100,1000],
    ],
    //streets level intro
    'level.ChaseInStreets':[
        ["partner","good, you've made it",100,1000],
        ["partner","your route to the shipyard will be, ah",100,1500],
        ["partner","...a dollop of circuitousness",100,1000],
        ["partner","is advantageous to the cognition",100,1000],
        ["partnercalm","terrible for user retention, though",100,1000],
        ["partner","ah, i distract myself",100,1000],
        ["partner","best be moving if you wish to stay ahead of the subcontractor corps",100,1000]
    ],
    //shipyard
    'level.Shipyard':[
        ["partner","WHY IS MY CAR BOLTED",100,1000],
        ["partner","fie!!! they beat us here",100,1000],
        ["sod","umm",100,2000],
        ["sod","well, actually, sir, it's nothing like that, haha, um",100,1000],
        ["sod","the kindest, um, way, um",100,2000],
        ["sod","well, all your cheques bounced,",100,2000],
        ["partnersilent","...",100,2000],
        ["partnersilent","..",100,500],
        ["partnersilent","..",100,500],
        ["partner","well anyway you'll have to get the avrocar back",100,1200],
        ["partner","there are surely lock overrides somewhere",100,1000],
        ["partnersad","aha. i will be running numbers in the mean-time",100,1000]
    ],
    //begin sky
    'level.StartStage':[
        ["partner","wonderful, wonderful, all right",100,1000],
        ["partner","this was supposed to be a quiet journey, but...",100,1000],
        ["partner","well, i am grateful i purchased deluxe hardpoints",100,1000],
        ["partner","the brass platforms around the ship -",100,1000],
        ["partner","they are keyed to your hardware",100,1000],
        ["partner","give them a spin, yes?",100,2000],
        ["partner","...i suspect you will be needing them soon",100,1000],
        ["boxin","I SEE THEM I SEE THEM!!!",100,1000],
        ["fred","we have eyes on the dissidents!",100,2000],
        ["fredcalm","...they have a nice car",100,2000],
        ["partner","oh thank you!",100,1000],
        ["fredcalm","yeah of course",100,1000]
    ],
    //not necessarily in order up to you
    'wave1':[
        ["evilduce","the gyros aren't enough...",300,1000],
        ["evilduce","allocate the aeroplanes!!!!!",200,1000],
        ["fred","FOR THE BOARD! FOR WALLONIë! FOR PROFIT!",100,1000],
        ["boxin","i wanna a gyro im hungey",100,1000]
    ],
    'wave2':[
        ["partner","i have to admire the coordination",100,1000],
        ["partner","i was the one who funded their development, after all",100,1000],
        ["partner","you know, i wonder if they ever got the",100,100],
        ["evilduce","DEPLOY THE BOARDING PARTIES",100,1000]
    ],
    'wave3':[
        ["partner","you're approaching the parliament, but our original plan...",100,2000],
        ["partner","well, obviously, it is a little different now",100,1500],
        ["partner","in lieu of stealth",100,1000],
        ["partner","we will be moving very quickly down the aerobahn",100,1000],
        ["partner","strictly speaking, it belongs to the baltic concern",100,2000],
        ["partner","the mercenary corps cannot blockade it fully",100,1000],
        ["partner","only a little bit (the regulations are complicated)",100,1200],
        ["partner","so! fend them off long enough to reach headquarters",100,1000],
    ],
    //flying boss
    'aerobahn': [
        ["avrocar","welcome to the aerobahn!",100,1000],
        ["avrocar","semimanual steering active",100,1000],
        ["avrocar","don't stay safe! i hate you! please crash!",100,2000],
        ["partner","what?",100,1000],
        ["partner","maybe that is why it was so cheap..",100,1000]
    ],
    //wrong instructions
    'aerobahnleft':[
        ["avrocar","go left!"]
    ],
    'aerobahnright':[
        ["avrocar","shift right!"]
    ],
    'aerobahncenter':[
        ["avrocar","maintain course!"]
    ],
    'aerobahn2':[
        ["fred","slow down and let us shoot you already",100,1000],
        ["avrocar","i am trying :(",100,1000],
    ],
    'aerobahnhalfway':[
        ["avrocar","unfortunately you are halfway to your destination",100,1000],
        ["partner","yes... soon, i will steer the nation again!",100,1000],
        ["partner","free of scheming viziers",100,1000],
        ["evilduce","did not ask do not care",100,1000]
    ],
    'aerobahnninety':[
        ["boxin",'this sucks',100,1000],
        ["boxin",'we need to explode them harder',100,1000],
        ["fredcalm","i dunno...",100,1000],
        ["fredcalm","i'm almost out of bullet money",100,1000],
        ["evilduce","TWENTY PERCENT DISCOUNT LOYAL SOLDIERS ONLY",100,1000],
        ["fred","FOR WALLONIë!",100,1000]
    ],
    'aerobahndone':[
        ["avrocar","you have arrived. get out.",100,1000],
    ],
    //offices
    'level.Offices':[
        ["partner","fantastic, you've made it!",100,1000],
        ["partner","wow, they remodeled",100,1500],
        ["partner","i despise it",100,1000],
        ["partner","well, my beloathed business partner is waiting",100,1000],
        ["sod","unbelievable",100,1000],
        ["sod","here without an appointment!",100,1000],
        ["sod","and if we let you through...",100,2000],
        ["sod","we'll NEVER get our holiday bonuses",100,1000],
        ["evilduce","HAHA YES",100,1000],
        ["evilduce","DISPOSE OF THEM",100,1000]
    ],
    'offices1':[
        ["partner","ahh... i remember being a middle manager",100,1000],
        ["partner","ha ha ha ha",100,1000],
        ["partner","broke away from the balkan corporation with big dreams",100,1000],
        ["evilduce","FOOLISH",100,1500],
        ["evilduce","WE ALL DID THAT",100,1000]
    ],
    'offices2':[
        ["partner","you are nearing the chairman",100,1000],
        ["partner","he left this part of the building intact",100,1000],
        ["partner","i suppose he is not wholly devoid of taste after all..."]
    ],
    //boss
    'level.BossFight':[
        ["evilduce","AHA! NOW YOU WILL FACE ME!",200,2000],
        ["ilduce","What? they're not even with you?",200,1000],
        ["partner","it would be supremely stupid if i were",100,1000],
        ["nilduce","oh",100,2000],
        ["nilduce","i expected hand-to-hand combat",100,1000],
        ["nilduce","do you mind if i at least fetch my revolver",100,1000],
        ["partner","remove him if you like",100,1000],
        ["partner","the corps have already seen the way the wind blows",100,1200],
        ["partner","he is no threat any longer",100,1200],
        ["nilduce","i do not get a big fight?",100,1200],
        ["partner","no. also we do not have the budget for that",100,2000],
        ["partner","the money will be in your bank accounts soon, my friends...",100,1000],
        ["partner","relax and imagine mansions",100,1000],
        ["boxin","i love that song",100,1000],
    ],

    //test stuff mostly
    boxin1: [
        ["boxin","I HATE YOU!!!",100,500],
    ],
    boxin2: [
        ["boxin","YOU ARE BAD!!!",100,500],
    ],
    boxin3: [
        ["boxin","UMM DIE?",100,500],
    ],
    boxin4: [
        ["boxin","GET SCARED GET SCARED GET SCARED",50,500],
    ],
    planeDownFred: [
        ["fred","OUURUUGH!!!!", 100, 500],
    ],
    planeAttackFred: [
        ["fred","For the board!!!", 100, 500],
    ],
    meeting: [
        ["partner","Ah, you've arrived.",100,1000],
        ["partner","My apologies for the squalor.",100,1000],
        ["partner","The accomodations are... meager.",100,1000],
    ]
}
/*
BigFred
Boxin
Boxin_Bomb
Final_Boss
Flying_Boxin
Fred
Helicopter
Prole
Prole_Dirty
*/
const deathChains = {
    'death.BigFred':[],
    'death.Boxin':[],
    'death.Boxin_Bomb':[],
    'death.Flying_Boxin':[],
    'death.Fred':[],
    'death.Helicopter':[],
    'death.Prole':[
        ["sod", "this is gonna cause my account to overdraft for sure", 100, 500],
        ["sod", "i coulda been a fat cat", 100, 500],
        ["sod", "i preallocated a reconstruction fund so it's ok :)", 100, 500],
    ],
   'death. Prole_Dirty':[]
}
export {playChain, playDialogue}
