<script setup lang="ts">
import { Common } from 'vt:@core/components/Common';
import { Core } from 'vt:@core/components/core';
import { useData } from 'vt:@core/composables/useData';
import { onMounted, ref } from 'vue';
import { testfn, peeb } from './diesel'

//dummy args



console.log("INIT VUE HUDs")

//console.log(gsap)
const message = ref({
    actor:'peeb',
    text: `not fast enough! chop chop!`,
    voice: `/file/path`
})

const ammo = ref(5);
const heldWeapon = ref<string>('');
const weapon = {
    ammo: 6,
    maxAmmo: 120,
    image: '/file/path'
}
const objective = ref({
    text: 'breach the paalais'
});
const playerClass = ref<string>('');

const colors = ref({
    textPrimary: '#FB0004',
    textSecondary: '#fc7b03',
    activeTextPrimary: '#FB0004'
})

const test = useData<string>("test", "default value")
setTimeout(()=>{
    
    damageFlicker();
    
    
},500)
let a = 0
setInterval(()=>{
    //
    a++;
})
let flickering = false;
function damageFlicker(){
    flickering = true;
    const colorArray = [
        '#FB0004',
        '#DF2E00',
        '#FB0004',
        '#AF3D00',
        '#9A3D00',
    ];
    let index = 0;
        colorshift();
        function colorshift() {
            index++; 
            if (index>=colorArray.length-1){
                colors.value.activeTextPrimary = colors.value.textPrimary;
                index = 0;
            }
            if(colorArray[index]){
                colors.value.activeTextPrimary = colorArray[index] as string;
            }
        }
}
</script>
<template>
    <!--Honestly I'm lazy and it's not like this is getting reused anywhere so who needs components really-->

    <!--Health-->
    <Group :anchor="{  Left: -20, Top: -20, Width: 350, Height: 500 }" :background="'Common/DropdownBox.png'">
        <!--Underlying progress bar-->
        <ProgressBar
            :anchor="{Top:0, Left:0, Width: 350, Height: 500}"
            :background="'Img/progress_back.png'"
            :bar-texture-path="'Img/progress_front.png'"
            :effect-texture-path="'Img/progress_effect.png'"
            :effect-height="100"
            :effect-width="100"
            :value="0.5"
            :alignment="'Vertical'"
            :direction="'Start'"
        ></ProgressBar>
        <!--3d model-->
        <Group :background="'Common/DropdownBox.png'">
        </Group>
        <!--Class-based overlay image-->
        <Group :background="'Common/DropdownBox.png'">
        </Group>
    </Group>

    <!--Hotbar-->
    <Group :anchor="{Right:-20, Top:0, Bottom:0, Width:120}" :background="'Common/DropdownBox.png'">

    </Group>

    <!--Dialogue-->
    <Group :anchor="{Left:-20, Bottom: -20,}">
        <!--Text-->
        <Group :anchor="{ Left:250,Bottom:0,Width: 700, Height: 200 }" :background="'Common/DropdownBox.png'">
            <Label
                :anchor="{Left:50, Width:600}"
                :padding="{Full:20}"
                :el-style="{
                    FontSize: 43,
                    FontName: 'Secondary',
                    RenderUppercase:true,
                    RenderBold: true,
                    Wrap: true,
                    TextColor: colors.activeTextPrimary
                }"
            >{{ message.text }} {{ test }}</Label>
        </Group>
        <!--Portrait-->
        <Group :anchor="{ Left:0,Bottom:0,Width: 300, Height: 300 }" :background="'Common/DropdownBox.png'">

        </Group>

    </Group>

    <!--Weapon info-->
    <Group :anchor="{  Right: -20, Bottom: -20}" >
        <!--Weapon graphic-->
        <Group :anchor="{ Right:250, Bottom:0,Width: 400, Height: 150 }" :background="'Common/DropdownBox.png'">

        </Group>
        <!--Ammo counter-->
        <Group :anchor="{ Width: 300, Height: 300, Right:0, Bottom: 0}" :background="'Common/DropdownBox.png'">
            <Group :anchor="{Top:20,Left:5}">
                <Label
                    :anchor="{Top:10,Left:0,Width:300,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: 210,
                        FontName: 'Default',
                        HorizontalAlignment: 'Start',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.activeTextPrimary
                    }"
                >{{ weapon.ammo }}</Label>
                <Label
                    :anchor="{Top:100,Left:80,Width:200,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: 60,
                        FontName: 'Default',
                        HorizontalAlignment: 'End',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.activeTextPrimary
                    }"
                >{{ weapon.maxAmmo }}</Label>
            </Group>

        </Group>
    </Group>

    <!--Objectives-->
    <Group :anchor="{Top: -20,Right:-20, Width:800, Height:120}" :background="'Common/DropdownBox.png'">
            <Label
                :anchor="{Top:20, Width:600, Left: 20}"
                :padding="{Full:20}"
                :el-style="{
                    FontSize: 43,
                    FontName: 'Secondary',
                    RenderUppercase:true,
                    RenderBold: true,
                    Wrap: true,
                    TextColor: colors.activeTextPrimary
                }"
            >{{ objective.text }}</Label>
    </Group>

    <!--All-purpose overlay-->
    <Group :anchor="{Full:1}">
        
    </Group>
</template>
<!--
-->