<template>
  <Group :anchor="{ Full: 1, Left: 0, Right: 0 }">
    <Label>Example {{ props.test }}</Label>
  </Group>
</template>
<script lang="ts" setup>
interface Props {
  test: string
}

const props = defineProps<Props>()
</script>
