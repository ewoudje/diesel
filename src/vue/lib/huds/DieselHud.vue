<script setup lang="ts">
import { useData } from 'vt:@core/composables/useData';
import { ref, computed, watch } from 'vue';
import { getPlayerClasses } from './gameplayClasses';
import { hueRotateHex, LightenDarkenColor } from './util';
import { playChain, playDialogue } from './dialogue';
console.log("[DIESELGAME] INIT VUE HUD")

const playSound = useData("playSound",()=>{});
const soundToPlay = ref('');
watch (soundToPlay,(sound: String)=>{
    //@ts-ignore
    playSound.value(sound);
})

const inputMessageChain = useData<string>("chain","");

const inputDashes = useData("dashes",4);
const inputObjective = useData<String>("objective","")
const inputClass = useData<string>("class","scout");
const inputHotbarIdx = useData<number>("hotbarIdx",0);
const inputHealth = useData<number>("health",1);
    
const classKey = computed(()=>{
    return inputClass.value.toLowerCase()
})
const playerClass = computed(()=>{
    //@ts-ignore
    return getPlayerClasses()[classKey.value]
})
const heldItem = computed(()=>{
    return({
        ammo: useData<number>("ammo",4),
        o:playerClass.value.hotbar[inputHotbarIdx.value]
    })
})


//UI Colors
const modifiedBaseColor = ref<String | null>(null)
const colors = computed(()=>{
    let startcolor = modifiedBaseColor.value ?? playerClass.value.baseColor;
    return {
        base: startcolor,
        light: LightenDarkenColor(startcolor,200),
        dark: LightenDarkenColor(startcolor,-30),
        alt: hueRotateHex(startcolor,40),
        brass: '#c98d1c',
        screen: `#282623`,
    }
})

//Dialogue
const displayMessage = ref('')
const displayPortrait = ref('Img/portrait/none.png')
watch(inputMessageChain,()=>{
    console.log("input message", inputMessageChain.value)
    playChain(inputMessageChain.value, displayMessage, displayPortrait, playSound)
})

//Debug background
const contentBg = ref('Img/empty.png')


</script>
<template>
    <!--it's not like this is getting reused anywhere so who needs components really-->

    <!--===MISC UNDERLAYS===-->
    <Group :anchor="{ Left:-50,Bottom:-35,Top:-25,Width: 200, Height: 440 }" :background="'Img/left.png'"/>


    <!--==HEALTH===-->
    <Group :anchor="{Left:0,Top:0}">
        <!--Image asset-->
        <Group :anchor="{ Left:-100,Top:-90,Width: 640, Height: 500 }">
            <Group :background="'Img/top_left.png'"/>
        </Group>

        <!--Health-->
            <ProgressBar
            :anchor="{Left:0, Top:0, Width: 340, Height: 380 }"
            :background="{Color:colors.base}"
            :mask-texture-path="'Img/health/scout.png'"
            :bar-texture-path="'Img/solid.png'"
x            :effect-height="100"
                :effect-width="100"
                :value="1- inputHealth"
                :alignment="'Vertical'"
                :direction="'End'"
            />

        <!--Shine overlay-->
        <Group :anchor="{ Left:-100,Top:-90,Width: 640, Height: 500 }" :background="'Img/top_left_shine.png'"/>
    </Group>



    <!--==DIALOGUE===-->
    <Group :anchor="{Left:-20, Bottom: -50, Height: 100}">
        <!--Image asset-->
        <Group :anchor="{ Left:-30,Bottom:0,Width: 1000, Height: 440 }">
            <Group :background="'Img/bottom_left_wide.png'"/>
            <!--portrait-->
            <Group :anchor="{Left:60, Bottom:22, Width: 320, Height: 320 }" :background="{Color:colors.base}" :mask-texture-path="displayPortrait"></Group>
        </Group>

        <!--Text-->
        <Group :anchor="{ Left:400,Bottom:100,Width: 530, Height: 100 }" :background="contentBg">
            
        <Label
                :el-style="{
                    FontSize: 35,
                    FontName: 'Mono',
                    Wrap: true,
                    RenderUppercase: true,
                    TextColor: colors.base
                }"
            >
            {{displayMessage}}  
        </Label>
        </Group>

        <!--tf2 coconut NOBODY TOUCH THIS IT'S REALLY IMPORTANT-->
        <Label :el-style="{FontSize: 1}">
        </Label>

        <!--Shine overlay-->
        <Group :anchor="{ Left:-30,Bottom:0,Width: 1000, Height: 440 }" :background="'Img/bottom_left_wide_shine.png'"></Group>

        <!--Dashes-->
        <Group :anchor="{Left:350, Bottom:198, Height:100, Width:200}" :background="contentBg" :layout-mode="'Left'">
            <Group v-for="(entry, index) in playerClass.maxDashes" :key=index :anchor="{Width: 48, Height: 48, Left:20 }" :mask-texture-path="'Img/mask/button.png'">
                <Group :background="index < inputDashes ? 'Img/charge_on.png' : 'Img/charge_off.png'"></Group>
            </Group>
        </Group>

    </Group>



    <!--==OBJECTIVES==-->
    <Group :anchor="{Top:0,Right:-35}">
        <!--Image asset-->
        <Group :anchor="{Top:0,Right:80, Width:700,Height:220}">
            <Group :background="'Img/top_right.png'"/>
        </Group>
        <!--Text-->
        <Group :anchor="{Top: 20, Right:180, Width:580, Height:90}" :background="contentBg">
            <Label
            :anchor="{Left:380,Top:10}"
            :el-style="{
                FontSize:30,
                FontName: 'Mono',
                TextColor: colors.base,
                }"
            >
                OBJECTIVE
            </Label>
            <Label
                :padding="{Full:10}"
                :el-style="{
                    FontSize: 43,
                    FontName: 'Secondary',
                    Wrap: true,
                    TextColor: colors.base,
                    Alignment: 'End'
                }"
            >
              {{inputObjective}} 
            </Label>
        </Group>

        <Group :anchor="{Top:0,Right:80, Width:700,Height:220}" :background="'Img/top_right_shine.png'"/>
    </Group>



   <!--==HOTBAR==-->
    <Group :anchor="{Right:0}">
        <!--image asset-->
        <Group :anchor="{Right:-180, Top:-10, Bottom:1, Width:320}" :background="'Img/right.png'"/>

        <!--hotbar-->
        <!--weapons-->
        <Group :anchor="{Right:0, Top:0, Bottom:0, Width:120}" :background="contentBg" :layout-mode="'Top'">
            <Group v-for="(entry, index) in playerClass.hotbar" :key="index" :anchor="{Left:0, Top:22, Width: 100, Height: 100 }" :background="(inputHotbarIdx == index) ? 'Img/button_on_overlay.png' : 'Img/button_off_overlay.png'" :mask-texture-path="'Img/mask/button.png'">
                <Group  :background="{Color:`${colors.base}(0.5)`}"/>
                <Group :background="(inputHotbarIdx == index) ? 'Img/button_on_top_overlay.png' : 'Img/empty.png'"/>
                <Group :anchor="{Width:60,Height:60}" :background="(inputHotbarIdx == index) ? {Color:colors.dark} : {Color:colors.light}" :mask-texture-path="playerClass.hotbar[index]?.iconPath"/>
                <Group :anchor="{Left:8}">
                    <Label
                    :anchor="{Left:2}"
                    :el-style="{
                        FontName: 'Secondary'
                    }"
                    >{{ index + 1}}/{{playerClass.hotbar[index]?.name}}</Label>
                </Group>
            </Group>
        </Group>
        <!--abilities-->
        <Group :anchor="{Right:0, Top:280, Bottom:0, Width:120}" :background="contentBg" :layout-mode="'Top'">
            <Group v-for="(entry, index) in heldItem.o?.abilities" :key="index" :anchor="{Left:0, Top:22, Width: 100, Height: 100 }" :background="false ? 'Img/button_on_overlay.png' : 'Img/button_off_overlay.png'" :mask-texture-path="'Img/mask/button.png'">
                <Group  :background="{Color:`${colors.alt}(0.3)`}"/>
                <Group :background="false ? 'Img/button_on_top_overlay.png' : 'Img/empty.png'"/>
                <Group :anchor="{Width:60,Height:60}" :background="false ? {Color:colors.dark} : {Color:colors.light}" :mask-texture-path="heldItem.o?.abilities[index]?.iconPath"/>
                <Group :anchor="{Left:8}">
                    <Label
                    :anchor="{Left:2}"
                    :el-style="{
                        FontName: 'Secondary'
                    }"
                    >
                        {{heldItem.o?.abilities[index].name}}
                    </Label>
                    <Label
                        :el-style="{
                            RenderUppercase: true,
                            FontName:'Mono'
                        }"
                        :anchor="{Top:78}"
                    >
                        {{ heldItem.o?.abilities[index].keybind }}
                    </Label>
                </Group>
            </Group>
        </Group>
    </Group>



    <!--==WEAPON INFO==-->
    <Group :anchor="{  Right: -20, Bottom: -52}" >
        <!--image asset-->
        <Group :anchor="{Bottom:0,Right:-90, Width:800,Height:450}">
            <Group :background="'Img/bottom_right.png'"/>
        </Group>

        <!--Weapon graphic-->
        <Group :anchor="{ Right:250, Bottom:0,Width: 400, Height: 150 }" :background="contentBg">
        <Group :anchor="{Left:-20, Bottom:62, Width: 400, Height: 130 }" :background="{Color:colors.base}" :mask-texture-path="heldItem.o?.previewPath"></Group>

        </Group>
        <!--Ammo counter-->
        <Group :anchor="{ Width: 600, Height: 300, Right:-90, Bottom: 30}" :background="contentBg">
            <Group v-if="heldItem.o?.maxAmmo" :anchor="{Top:30,Left:(heldItem.ammo.value < 10) ? -15 : 40}">
                <Label
                    :anchor="{Top:10,Left:0,Right:0,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: (heldItem.ammo.value < 10) ? 200 : 110 ,
                        FontName: 'Secondary',
                        HorizontalAlignment: 'Center',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.base
                    }"
                >{{ heldItem.ammo }}</Label>
                <Label
                    :anchor="(heldItem.ammo.value < 10) ? 
                        {Top:-10,Right:80,Width:300,Height:200}
                        : {Top:60,Left:0,Right:0,Width:300,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: (heldItem.ammo.value < 10) ? 100 : 60,
                        FontName: 'Default',
                        RenderBold: true,
                        HorizontalAlignment: 'Center',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.base
                    }"
                >{{ heldItem.o?.maxAmmo}}
                
                </Label>
            </Group>
            <Group :anchor="{Top:-30}" :layout-mode="'Top'" v-else>
                <Label
                :anchor="{Top:30,Left:50,Right:0,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: 100,
                        FontName: 'Secondary',
                        HorizontalAlignment: 'Center',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.base
                    }""
                    >ME</Label>
                           <Label
                :anchor="{Top:-30,Left:50,Right:0,Height:10}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: 100,
                        FontName: 'Secondary',
                        HorizontalAlignment: 'Center',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.base
                    }""
                    >LEE</Label>         
            </Group>
        </Group>

        <Group :anchor="{Bottom:0,Right:-90, Width:800,Height:450}" :background="'Img/bottom_right_shine.png'"/>
    </Group>

    <!--All-purpose overlay-->
    <Group :anchor="{Full:1}">

    </Group>
</template>
<!--
-->
