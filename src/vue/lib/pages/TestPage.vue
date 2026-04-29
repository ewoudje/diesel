<script lang="ts" setup>
import {Common} from 'vt:@core/components/Common';
import {Core} from 'vt:@core/components/core';
import {useData} from 'vt:@core/composables/useData';
import {ref} from 'vue';

console.log("WORKS!")

function clickTest() {
  console.log("CLICKED ME")
  someState.value = !someState.value
}

const textFieldInput = ref("Hello!")

const someState = ref(false)
const text = ref("nothing yet")

const test = useData<string>("test", "default value")

</script>
<template>
  <Common.DecoratedContainer :anchor="{ Width: 500, Height: 300 }">
    <template #title>
      <Common.Title text="Vuetale Starter!"></Common.Title>
    </template>
    <template #content>
      <Group :anchor="{ Full: 1 }" layout-mode="TopScrolling">
        <Label>Custom! {{ text }} | {{ test }}</Label>
        <Label>{{ textFieldInput }}</Label>
        <Common.TextButton text="CLICK ME" @activating="clickTest"></Common.TextButton>
        <Core.TextField v-if="someState" v-model="textFieldInput"></Core.TextField>
      </Group>
    </template>

  </Common.DecoratedContainer>
</template>
