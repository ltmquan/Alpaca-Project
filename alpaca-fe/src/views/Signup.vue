<script setup>
import { reactive } from 'vue'

import Form from '../components/Form.vue'

import { signup_form_data } from '../constants/form'
import userService from '../services/UserService'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
/* EMIT */
/* PROPS */
/* STATES */
const data = reactive({...signup_form_data})

/* COMPUTED */
/* WATCHERS */
/* METHODS */
function signup(user) {
  console.log(userService.create(user))
}
</script>

<template>
  <Form v-bind="data" @submit="signup" />
</template>