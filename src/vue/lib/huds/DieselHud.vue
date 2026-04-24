<script setup lang="ts">
import { Common } from 'vt:@core/components/Common';
import { Core } from 'vt:@core/components/core';
import { useData } from 'vt:@core/composables/useData';
import { onMounted, ref } from 'vue';
import { testfn, peeb } from './diesel'
console.log("[DIESELGAME] INIT VUE HUD")
//dummy args

//UI Colors
const colors = ref({
    textPrimary: '#FB0004',
    textSecondary: '#fc7b03',
    activeTextPrimary: '#FB0004'
})
//Current message
const message = ref({
    actor:'peeb',
    text: `not fast enough! chop chop!`,
    voice: `/file/path`
})
//Debug background
const contentBg = ref('Img/empty.png')
//Weapons
const heldWeapon = ref<string>('');
const weapon = {
    ammo: 6,
    maxAmmo: 120,
    image: '/file/path'
}
//Current objective
const objective = ref({
    text: 'breach the palais'
});
//Player class
const playerClass = ref<string>('');
//Hotbar
const hotbar = ref([
    {
        name: '1',
        active:true,
        type: 'item',
        detailImage: '',
        color:null
    },
        {
        name: '2',
        active:false,
        type: 'item',
        detailImage: '',
        color:null
    },
        {
        name: '3',
        active:false,
        type: 'ability',
        detailImage: '',
        color:null
    },
        {
        name: '4',
        active:false,
        type: 'ability',
        detailImage: '',
        color:null
    }
])


const test = useData<string>("test", "default value")
setTimeout(()=>{
    //damageFlicker();
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

    <!--===MISC UNDERLAYS===-->
    <Group :anchor="{ Left:-50,Bottom:-35,Top:-25,Width: 200, Height: 440 }" :background="'Img/left.png'"/>


    <!--==HEALTH===-->
    <Group :anchor="{Left:0,Top:0}">
        <!--Image asset-->
        <Group :anchor="{ Left:-100,Top:-90,Width: 640, Height: 500 }">
            <Group :background="'Img/top_left.png'"/>
        </Group>
            <Group :anchor="{Left:0, Top:0, Width: 340, Height: 380 }" :background="{Color:colors.activeTextPrimary}" :mask-texture-path="'Img/health/scout1.png'"></Group>

        <Group :anchor="{  Left: -20, Top: -20, Width: 350, Height: 500 }" :background="contentBg">
            <!--Underlying progress bar
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
            ></ProgressBar>-->
            <!--3d model-->
            <!--Class-based overlay image-->
            <Group :background="contentBg">
            </Group>
        </Group>

        <!--Shine overlay-->
        <Group :anchor="{ Left:-100,Top:-90,Width: 640, Height: 500 }" :background="'Img/top_left_shine.png'"/>
    </Group>
    


    <!--==DIALOGUE===-->
    <Group :anchor="{Left:-20, Bottom: -50,}">
        <!--Image asset-->
        <Group :anchor="{ Left:-30,Bottom:0,Width: 1000, Height: 440 }">
            <Group :background="'Img/bottom_left_wide.png'"/>    
            <!--portrait-->
            <Group :anchor="{Left:60, Bottom:22, Width: 320, Height: 320 }" :background="{Color:colors.activeTextPrimary}" :mask-texture-path="'Img/portrait/prole1.png'"></Group>
        </Group> 
  
        <!--Text-->
        <Group :anchor="{ Left:400,Bottom:100,Width: 530, Height: 100 }" :background="contentBg">
            <Label
                :el-style="{
                    FontSize: 35,
                    FontName: 'Secondary',
                    RenderUppercase:true,
                    RenderBold: true,
                    Wrap: true,
                    TextColor: colors.activeTextPrimary
                }"
            >{{ message.text }}</Label>
        </Group>


        <!--Shine overlay-->
        <Group :anchor="{ Left:-30,Bottom:0,Width: 1000, Height: 440 }" :background="'Img/bottom_left_wide_shine.png'"></Group>
    </Group>



    <!--==OBJECTIVES==-->
    <Group :anchor="{Top:0,Right:-35}">
        <!--Image asset-->
        <Group :anchor="{Top:0,Right:80, Width:700,Height:220}">
            <Group :background="'Img/top_right.png'"/>
        </Group>
        <!--Text-->
        <Group :anchor="{Top: 10, Right:170, Width:580, Height:90}" :background="contentBg">
                <Label
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

        <Group :anchor="{Top:0,Right:80, Width:700,Height:220}" :background="'Img/top_right_shine.png'"/>
    </Group>



   <!--==HOTBAR==-->
    <Group :anchor="{Right:0}">
        <!--image asset-->
        <Group :anchor="{Right:-180, Top:-10, Bottom:1, Width:320}" :background="'Img/right.png'"/>

        <!--hotbar-->
        <Group :anchor="{Right:0, Top:0, Bottom:0, Width:120}" :background="contentBg" :layout-mode="'Top'">
            <Group v-for="entry in hotbar" :anchor="{Left:0, Top:22, Width: 100, Height: 100 }" :background="entry.active ? 'Img/button_on_overlay.png' : 'Img/button_off_overlay.png'" :mask-texture-path="'Img/masks/button.png'">
                <Group  :background="{Color:`${colors.activeTextPrimary}(0.5)`}"/>
                <Group :background="entry.active ? 'Img/button_on_top_overlay.png' : 'Img/empty.png'"/>
                <Label>{{ entry.name }}</Label>
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
        <Group :anchor="{Left:-20, Bottom:62, Width: 400, Height: 130 }" :background="{Color:colors.activeTextPrimary}" :mask-texture-path="'Img/item/knife.png'"></Group>

        </Group>
        <!--Ammo counter-->
        <Group :anchor="{ Width: 300, Height: 300, Right:33, Bottom: 30}" :background="contentBg">
            <Group :anchor="{Top:10,Left:40}">
                <Label
                    :anchor="{Top:10,Left:0,Width:300,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: 170,
                        FontName: 'Default',
                        HorizontalAlignment: 'Start',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.activeTextPrimary
                    }"
                >{{ weapon.ammo }}</Label>
                <Label
                    :anchor="{Top:100,Left:10,Width:200,Height:200}"
                    :padding="{Full:20}"
                    :el-style="{
                        FontSize: 50,
                        FontName: 'Default',
                        HorizontalAlignment: 'End',
                        VerticalAlignment: 'Center',
                        Wrap: false,
                        TextColor: colors.activeTextPrimary
                    }"
                >{{ weapon.maxAmmo }}</Label>
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