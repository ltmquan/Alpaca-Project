<script setup>
import { isProxy, computed } from 'vue'

import Card from 'primevue/card/sfc'
import InputText from 'primevue/inputtext/sfc'
import Button from 'primevue/button/sfc'
import Dropdown from 'primevue/dropdown/sfc'
import Textarea from 'primevue/textarea/sfc'
import Calendar from 'primevue/calendar/sfc'
import TreeSelect from 'primevue/treeselect/sfc'
import Checkbox from 'primevue/checkbox/sfc'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
/* EMIT */
const emit = defineEmits(['submit', 'cancel'])

/* PROPS */
const props = defineProps({
  title: String,
  data: Array,
  submitBtn: Object,
  cancelBtn: Object,
})

/* STATES */
/* COMPUTED */
/* WATCHERS */
/* METHODS */
function maxDate(limit) {
  return new Date(new Date(limit).getTime() + (24 * 60 * 60 * 1000))
}

function submit(event) {
  let emitData = {}

  props.data.forEach(item => {
    if (isProxy(item.value)) {
      emitData[item.id] = parseInt(Object.keys(item.value)[0])
    } else {
      emitData[item.id] = item.value
    }
  })

  emit('submit', emitData)
}

function cancel() {
  emit('cancel')
}
</script>

<template>
  <div class="flex justify-content-center p-fluid m-4">
    <Card class="w-4" v-focustrap>
      <template #title>
        <div class="flex justify-content-center my-2">
          {{ title }}
        </div>
      </template>
      <template #content>
        <div v-for="(i, index) in data" class="field my-4">
          <span class="p-float-label">
            <Dropdown v-if="i.type == 'select'" :id="i.id" :options="i.options" optionLabel="label" optionValue="value" v-model="i.value" :autofocus="index == 0" />
            <Checkbox v-else-if="i.type == 'checkbox'" :id="i.id" v-model="i.value" :binary="true" :autofocus="index == 0" />
            <TreeSelect v-else-if="i.type == 'tree'" :id="i.id" :options="i.options" optionLabel="label" optionValue="value" v-model="i.value" :autofocus="index == 0" />
            <Textarea v-else-if="i.type == 'textarea'" :id="i.id" rows="5" cols="30" v-model="i.value" :autofocus="index == 0" />
            <Calendar v-else-if="i.type == 'date'" :id="i.id" v-model="i.value" :autofocus="index == 0" :minDate="new Date()" :maxDate="maxDate(i.limit)" />
            <InputText v-else :id="i.id" :type="i.type" v-model="i.value" :autofocus="index == 0" />
            <label v-if="i.type != 'checkbox'" :for="i.id">{{ i.label }}</label>
            <label v-else class="mx-3" :for="i.id">{{ i.label }}</label>
          </span>
        </div>
      </template>
      <template #footer>
        <div class="grid">
          <Button v-if="submitBtn.isUsed" :label="submitBtn.label" class="col m-2" @click="submit" />
          <Button v-if="cancelBtn.isUsed" :label="cancelBtn.label" class="p-button-secondary col m-2" @click="cancel" />
        </div>
      </template>
    </Card>
  </div>
</template>