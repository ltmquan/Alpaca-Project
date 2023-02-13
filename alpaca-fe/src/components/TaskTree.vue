<script setup>
import { ref, computed } from 'vue'

import TreeTable from 'primevue/treetable/sfc'
import Column from 'primevue/column/sfc'
import Badge from 'primevue/badge/sfc'
import ProgressBar from 'primevue/progressbar/sfc'
import Button from 'primevue/button/sfc'

import { priority_level } from '../constants/priority_level'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
/* EMIT */
const emit = defineEmits(['create', 'edit', 'delete', 'view', 'note', 'finish', 'unfinish'])

/* PROPS */
const props = defineProps({
  data: Array
})

/* STATES */
const taskId = ref()
const hoverId = ref(-1)

/* COMPUTED */
/* WATCHERS */
/* METHODS */
function remainingDays(deadline) {
  let diff = new Date(deadline).getTime() - new Date().getTime()

  return Math.ceil(diff / (1000 * 3600 * 24))
}

function create(parentId) {
  emit('create', parentId)
}

function edit() {
  emit('edit', Object.keys(taskId.value)[0])
}

function _delete() {
  emit('delete', Object.keys(taskId.value)[0])
}

function view() {
  emit('view', Object.keys(taskId.value)[0])
}

function note(noteId) {
  emit('note', noteId)
}

function markFinished(id) {
  emit('finish', id)
}

function hoverFinish(id) {
  hoverId.value = id
}

function stopHoverFinish() {
  hoverId.value = -1
}

function markUnfinished(id) {
  emit('unfinish', id)
}
</script>

<template>
  <TreeTable :value="data" class="p-treetable-sm" selectionMode="single" v-model:selectionKeys="taskId">
    <Column field="name" :expander="true">
      <template #header>
        <div class="px-5">
          TASKS
        </div>
      </template>
      <template #body="slotProps">
        <span>{{ slotProps.node.name }}</span>
      </template>
    </Column>
    <Column class="w-2">
      <template #body="slotProps">
        <span>{{ slotProps.node.deadline }}</span>
      </template>
    </Column>
    <Column class="w-1">
      <template #body="slotProps">
        <Badge :severity="priority_level[slotProps.node.priorityLevel - 1].severity"
          :value="remainingDays(slotProps.node.deadline)">
        </Badge>
      </template>
    </Column>
    <Column class="w-2">
      <template #body="slotProps">
        <ProgressBar :value="slotProps.node.progress" />
      </template>
    </Column>
    <Column class="w-1" bodyClass="text-center">
      <template #body="slotProps">
        <Button type="button" icon="pi pi-comment" class="p-button-text p-button-rounded" style="margin-right: .5em"
          @click="event => note(slotProps.node.key)"></Button>
      </template>
    </Column>
    <Column class="w-2" headerClass="text-center" bodyClass="text-center">
      <template #header>
        <Button type="button" icon="pi pi-plus" class="p-button-outlined w-5 h-2rem m-1" style="margin-right: .5em"
          @click="event => create(null)"></Button>
        <Button v-if="taskId" type="button" icon="pi pi-file-edit"
          class="p-button-warning p-button-outlined w-5 h-2rem m-1" style="margin-right: .5em"
          @click="event => edit(null)"></Button>
        <Button v-if="taskId" type="button" icon="pi pi-trash" class="p-button-danger p-button-outlined w-5 h-2rem m-1"
          style="margin-right: .5em" @click="event => _delete()"></Button>
        <Button v-if="taskId" type="button" icon="pi pi-external-link"
          class="p-button-secondary p-button-outlined w-5 h-2rem m-1" style="margin-right: .5em"
          @click="event => view()"></Button>
      </template>
      <template #body="slotProps">
        <div v-if="slotProps.node.progress < 100" class="grid">
          <Button icon="pi pi-plus" class="p-button-outlined w-5 h-2rem m-2 col"
            @click="event => create(slotProps.node.key)"></Button>
          <Button icon="pi pi-check" class="p-button-outlined p-button-success w-5 h-2rem m-2 col"
            @click="event => markFinished(slotProps.node.key)"></Button>
        </div>
        <div v-else @mouseover.stop="hoverFinish(slotProps.node.key)" @mouseleave.stop="stopHoverFinish">
          <Button :icon="`pi ${hoverId == slotProps.node.key ? 'pi-times' : 'pi-check'}`"
            :class="`p-button-rounded p-button-text ${hoverId == slotProps.node.key ? 'p-button-danger' : 'p-button-success'} opacity-100`"
            :disabled="hoverId != slotProps.node.key" @click="markUnfinished(slotProps.node.key)" />
        </div>
      </template>
    </Column>
  </TreeTable>
</template>