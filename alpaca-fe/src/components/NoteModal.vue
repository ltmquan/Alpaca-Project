<script setup>
import { ref, watch, reactive } from 'vue';
import { useUserStore } from '../stores/user';

import Dialog from 'primevue/dialog/sfc'
import TabView from 'primevue/tabview/sfc'
import TabPanel from 'primevue/tabpanel/sfc'
import Textarea from 'primevue/textarea/sfc'
import Button from 'primevue/button/sfc'
import InputText from 'primevue/inputtext/sfc'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
const userStore = useUserStore()

/* EMIT */
const emit = defineEmits(['close', 'create', 'edit', 'delete'])

/* PROPS */
const props = defineProps({
  show: Boolean,
  data: Array,
  taskId: Number
})

/* STATES */
const display = ref(props.show)
const addingNote = ref(false)
const newNote = reactive({
  name: '',
  content: '',
  taskId: null,
  userId: null
})
const updateNote = reactive({
  name: '',
  content: '',
  taskId: null,
  userId: null
})
const updateNoteId = ref()

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
function addNote(event) {
  addingNote.value = true
}

function cancelAddNote(event) {
  addingNote.value = false
}

function create(event) {
  newNote.taskId = props.taskId
  newNote.userId = userStore.id
  emit('create', newNote)
}

function edit(note) {
  updateNoteId.value = note.id
  updateNote.name = note.name
  updateNote.content = note.content
  updateNote.taskId = note.taskId
  updateNote.userId = note.userId
}

function cancelEditNote(event) {
  updateNoteId.value = null
  updateNote.name = ''
  updateNote.content = ''
  updateNote.taskId = null
  updateNote.userId = null
}

function saveEdit(event) {
  emit('edit', {
    ...updateNote,
    id: updateNoteId.value
  })
}

function _delete(id) {
  emit('delete', id)
}
</script>

<template>
  <Dialog class="w-6" v-model:visible="display" :modal="true">
    <div class="px-2 font-bold text-2xl my-3">
      Notes
      <Button icon="pi pi-plus" class="p-button-secondary p-button-outlined p-button-sm w-1 h-2rem mx-1"
        @click="addNote" />
    </div>

    <TabView>
      <TabPanel v-for="note in data">
        <template #header>
          <span>{{ note.id == updateNoteId? updateNote.name : note.name }}</span>
        </template>

        <div v-if="note.id == updateNoteId" class="flex justify-content-center">
          <div class="w-full">
            <div class="font-bold pr-2 py-2">Name: </div>
            <InputText class="opacity-100 w-full" v-model="updateNote.name" />
          </div>
        </div>

        <div class="flex justify-content-center">
          <div class="w-full">
            <div v-if="note.id == updateNoteId" class="font-bold pr-2 py-2">Content: </div>
            <Textarea v-if="note.id != updateNoteId" class="opacity-100 w-full" disabled rows="5" cols="30" :value="note.content" />
            <Textarea v-else class="opacity-100 w-full" rows="5" cols="30" v-model="updateNote.content" />
          </div>
        </div>

        <div v-if="note.id != updateNoteId" class="grid my-2">
          <Button label="Edit" class="p-button-secondary col m-2" @click="event => edit(note)" />
          <Button label="Delete" class="p-button-danger col m-2" @click="event => _delete(note.id)" />
        </div>
        <div v-else class="grid my-2">
          <Button label="Save" class="col m-2" @click="saveEdit" />
          <Button label="Cancel" class="p-button-danger col m-2" @click="cancelEditNote" />
        </div>
      </TabPanel>

      <TabPanel v-if="addingNote">
        <template #header>
          <span>{{ newNote.name? newNote.name : "New note" }}</span>
        </template>

        <div class="flex justify-content-center">
          <div class="w-full">
            <div class="font-bold pr-2 py-2">Name: </div>
            <InputText class="opacity-100 w-full" v-model="newNote.name" />
          </div>
        </div>

        <div class="flex justify-content-center">
          <div class="w-full">
            <div class="font-bold pr-2 py-2">Content: </div>
            <Textarea class="opacity-100 w-full" rows="5" cols="30" v-model="newNote.content" />
          </div>
        </div>

        <div class="grid my-2">
          <Button label="Save" class="col m-2" @click="create" />
          <Button label="Cancel" class="p-button-secondary col m-2" @click="cancelAddNote" />
        </div>
      </TabPanel>
    </TabView>
  </Dialog>
</template>
