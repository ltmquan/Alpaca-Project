<script setup>
import { reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

import Form from '../components/Form.vue'

import { login_form_data } from '../constants/form'
import userService from '../services/UserService'

/* LIFECYCLE HOOKS */
onMounted(() => {
  if (userStore.isLoggedIn) {
    router.push('/')
  }
})

/* ROUTE & ROUTER */
const router = useRouter()

/* STORES */
const userStore = useUserStore()

/* EMIT */
/* PROPS */
/* STATES */
const data = reactive({...login_form_data})

/* COMPUTED */
/* WATCHERS */
/* METHODS */
function login(user) {
  userService.login(user).then(
    data => {
      router.go()
    }
  )
}
</script>

<template>
  <Form v-bind="data" @submit="login" />
</template>