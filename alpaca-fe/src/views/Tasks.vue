<script setup>
import { ref, onMounted, reactive, toRaw } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

import '@fullcalendar/core'
import FullCalendar from '@fullcalendar/vue3'
import TaskTree from '../components/TaskTree.vue'
import TaskModal from '../components/TaskModal.vue'
import NoteModal from '../components/NoteModal.vue'

import { options } from '../constants/calendar'
import { delete_task_modal_data, delete_note_modal_data } from '../constants/modal'
import taskService from '../services/TaskService'
import noteService from '../services/NoteService'
import ConfirmModal from '../components/ConfirmModal.vue'

/* LIFECYCLE HOOKS */
onMounted(() => {
  taskService.findByUserId(userStore.id).then(
    data => {
      taskTreeData.value = toTaskTreeData(data)
      calendarData.value = toCalendarData(data, 0)
      console.log(calendarData.value)
    }
  )
})

/* ROUTE & ROUTER */
const router = useRouter()

/* STORES */
const userStore = useUserStore()

/* EMIT */
/* PROPS */
/* STATES */
const taskTreeData = ref()
const calendarData = ref()
const calendarOptions = reactive({
  ...options,
  events: calendarData,
  eventClick: onEventClick
})
const displayView = ref()
const taskView = ref()
const displayNote = ref()
const noteList = ref()
const selectedTaskId = ref()
const displayConfirm = ref()
const confirmModalData = ref()
const deleteType = ref()
const deleteId = ref()

/* COMPUTED */
/* WATCHERS */
/* METHODS */
function toTaskTreeData(data) {
  return data.map((item) => {
    return {
      key: item.id,
      name: item.name,
      deadline: item.deadline,
      priorityLevel: item.priorityLevel,
      progress: item.progress,
      children: toTaskTreeData(item.children)
    }
  })
}

function toCalendarData(data, lvl) {
  let ret = data.map((item) => {
    let bgc = ''
    if (item.priorityLevel == 1) {
      bgc = '#22C55E'
    } else if (item.priorityLevel == 2) {
      bgc = '#F59E0B'
    } else {
      bgc = '#EF4444'
    }

    return {
      id: item.id,
      title: item.name,
      date: item.deadline,
      priorityLevel: item.priorityLevel*(lvl+1) + 2*lvl,
      backgroundColor: bgc
    }
  })

  for (let i = 0; i < data.length; i++) {
    ret = ret.concat(toCalendarData(data[i].children, lvl+1))
  }

  return ret
}

function createTask(parentId) {
  if (parentId) {
    router.push(`task/create/${parentId}`)
  } else {
    router.push(`task/create`)
  }
}

function editTask(id) {
  router.push(`task/edit/${id}`)
}

function deleteTask(id) {
  taskService.deleteById(id).then(
    res => {
      router.go()
    }
  )
}

function showView(id) {
  taskService.findById(id).then(
    task => {
      taskView.value = task
      displayView.value = true
    }
  )
}

function closeView() {
  displayView.value = false
}

function showNote(taskId) {
  selectedTaskId.value = taskId
  noteService.findByTaskId(taskId).then(
    note => {
      noteList.value = note
    }
  )
  displayNote.value = true
}

function closeNote() {
  displayNote.value = false
}

function createNote(note) {
  noteService.create(note).then(
    res => {
      router.go()
    }
  )
}

function editNote(note) {
  noteService.update(note).then(
    res => {
      router.go()
    }
  )
}

function deleteNote(id) {
  noteService.deleteById(id).then(
    res => {
      router.go()
    }
  )
}

function showConfirm(type, data) {
  deleteType.value = type
  deleteId.value = data
  if (type === 'task') {
    confirmModalData.value = { ...delete_task_modal_data }
  } else {
    confirmModalData.value = { ...delete_note_modal_data }
  }
  displayConfirm.value = true
}

function closeConfirm(event) {
  displayConfirm.value = false
}

function confirmModal(event) {
  if (deleteType.value === 'task') {
    deleteTask(deleteId.value)
  } else {
    deleteNote(deleteId.value)
  }
}

function markFinished(taskId) {
  taskService.markFinished(taskId).then(
    res => {
      router.go()
    }
  )
}

function markUnfinished(taskId) {
  taskService.markUnfinished(taskId).then(
    res => {
      router.go()
    }
  )
}

function onEventClick(info) {
  showView(info.event.id)
}
</script>

<template>
  <div class="grid p-4">
    <div class="col-7">
      <div class="card">
        <TaskTree :data="taskTreeData" @create="createTask" @edit="editTask"
          @delete="taskId => { showConfirm('task', taskId) }" @view="showView" @note="showNote" @finish="markFinished"
          @unfinish="markUnfinished" />
      </div>
    </div>
    <div class="col-5">
      <FullCalendar :options="calendarOptions" class="p-component text-xs" />
    </div>
  </div>
  <TaskModal :show="displayView" :data="taskView" @close="closeView" />
  <NoteModal :show="displayNote" :data="noteList" :task-id="selectedTaskId" @close="closeNote" @create="createNote"
    @edit="editNote" @delete="noteId => { showConfirm('note', noteId) }" />
  <ConfirmModal :show="displayConfirm" v-bind="confirmModalData" @close="closeConfirm" @confirm="confirmModal" />
</template>
