<script setup>
import { ref, watch } from 'vue';

import Dialog from 'primevue/dialog/sfc'
import Button from 'primevue/button/sfc'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
/* EMIT */
const emit = defineEmits(['close', 'confirm'])

/* PROPS */
const props = defineProps({
  show: Boolean,
  header: String,
  content: String,
  submitBtn: Object,
  cancelBtn: Object
})

/* STATES */
const display = ref(props.show)

/* COMPUTED */
/* WATCHERS */
watch(() => props.show, (newShow, oldShow) => {
  display.value = newShow
})

watch(display, (newDisplay, oldDisplay) => {
  if (!newDisplay) {
    emit('close')
  }
})

/* METHODS */
</script>

<template>
  <Dialog class="w-6" v-model:visible="display" :modal="true">
    <template #header>
      <div class="font-bold text-2xl">
        {{ props.header }}
      </div>
    </template>

    {{ props.content }}

    <div class="grid my-3">
      <Button :class="`col m-2 ${props.submitBtn.style}`" :label="props.submitBtn.label" @click="event => { emit('confirm') }" />
      <Button :class="`col m-2 ${props.cancelBtn.style}`" :label="props.cancelBtn.label" @click="event => { emit('close') }" />
    </div>
  </Dialog>
</template>