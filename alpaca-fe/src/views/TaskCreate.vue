<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

import Button from 'primevue/button/sfc'
import Form from '../components/Form.vue'
import TaskModal from '../components/TaskModal.vue'

import { task_create_form } from '../constants/form'
import taskService from '../services/TaskService'

/* LIFECYCLE HOOKS */
onMounted(() => {
  if (route.params.parentId) {
    taskService.findById(route.params.parentId).then(
      task => {
        parentTask.value = task

        data.data[1].limit = task.deadline
      }
    )
  }
})

/* ROUTE & ROUTER */
const route = useRoute()
const router = useRouter()

/* STORES */
const userStore = useUserStore()

/* EMIT */
/* PROPS */
/* STATES */
const data = reactive({...task_create_form})
const parentTask = ref()
const show = ref(false)

/* COMPUTED */
/* WATCHERS */
/* METHODS */
function showParent(event) {
  show.value = true
}

function closeParent(event) {
  show.value = false
}

function create(task) {
  task.userId = userStore.id
  task.finished = false
  if (route.params.parentId) {
    task.parentId = route.params.parentId
  }

  taskService.create(task).then(
    res => {
      router.push('/tasks').then(
        () => {
          router.go()
        }
      )
    }
  )
}

function cancel() {
  router.push('/tasks')
}
</script>

<template>
  <div v-if="route.params.parentId" class="flex justify-content-center p-fluid m-3">
    <Button class="w-2 flex justify-content-center" @click="showParent">
      Parent Task
    </Button>
  </div>
  <Form v-bind="data" @submit="create" @cancel="cancel" />
  <TaskModal :show="show" :data="parentTask" @close="closeParent" />
</template>
