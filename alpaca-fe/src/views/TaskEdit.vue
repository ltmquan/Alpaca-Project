<script setup>
import { onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';

import Form from '../components/Form.vue';

import { task_edit_form } from '../constants/form'
import taskService from '../services/TaskService';

/* LIFECYCLE HOOKS */
onMounted(() => {
  taskService.findById(route.params.id).then(
    task => {
      data.data[0].value = task.name
      data.data[1].value = task.deadline
      data.data[2].value = task.priorityLevel
      data.data[3].value = task.description
      data.data[4].value = {}
      data.data[4].value[`${task.parentId? task.parentId : -1}`] = true
      data.data[5].value = task.finished

      if (task.parentId) {
        taskService.findById(task.parentId).then(
          parent => {
            data.data[1].limit = parent.deadline
          }
        )
      }
    }
  )

  taskService.findByUserId(userStore.id).then(
    taskList => {
      data.data[4].options = toParentTreeSelectData(taskList)
      data.data[4].options.unshift({ key: -1, label: 'No parent', value: -1  })
    }
  )

})

/* ROUTE & ROUTER */
const route = useRoute()
const router = useRouter()

/* STORES */
const userStore = useUserStore()

/* EMIT */
/* PROPS */
/* STATES */
const data = reactive({...task_edit_form})

/* COMPUTED */
/* WATCHERS */
/* METHODS */
function toParentTreeSelectData(taskList) {
  return taskList.map((item) => {
    return {
      key: item.id,
      label: item.name,
      value: item.id,
      children: toParentTreeSelectData(item.children)
    }
  })
}

function edit(task) {
  if (task.parentId == -1) {
    task.parentId = null
  }
  task.id = parseInt(route.params.id)
  task.userId = userStore.id
  taskService.update(task).then(
    res => {
      router.push('/tasks')
    }
  )
}

function cancel() {
  router.push('/tasks')
}
</script>

<template>
  <Form v-bind="data" @submit="edit" @cancel="cancel" />
</template>