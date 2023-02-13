<script setup>
import { ref, watch } from 'vue'

import Dialog from 'primevue/dialog/sfc'
import InputText from 'primevue/inputtext/sfc'
import ProgressBar from 'primevue/progressbar/sfc'

import { priority_level } from '../constants/priority_level'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
/* EMIT */
const emit = defineEmits(['close'])

/* PROPS */
const props = defineProps({
  'show': Boolean,
  'data': Object
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
  <Dialog class="w-4" v-model:visible="display" :modal="true" >
    <template #header>
      <div class="px-5 font-bold text-2xl">
        {{ props.data.name }}
      </div>
    </template>

    <div class="flex justify-content-center">
      <div class="w-10">
        <div class="font-bold pr-2 py-2">Deadline: </div>
        <InputText class="opacity-100 w-full" type="text" disabled :value="props.data.deadline" />
      </div>
    </div>

    <div class="flex justify-content-center">
      <div class="w-10">
        <div class="font-bold pr-2 py-2">Priority Level: </div>
        <InputText class="opacity-100 w-full" type="text" disabled
          :value="priority_level[props.data.priorityLevel-1].label" />
      </div>
    </div>

    <div class="flex justify-content-center">
      <div class="w-10">
        <div class="font-bold pr-2 py-2">Description: </div>
        <InputText class="opacity-100 w-full" type="text" disabled :value="props.data.description" />
      </div>
    </div>

    <div class="flex justify-content-center">
      <div class="w-10">
        <div class="font-bold pr-2 py-2">Progress: </div>
        <ProgressBar :value="props.data.progress" />
      </div>
    </div>

  </Dialog>
</template>
