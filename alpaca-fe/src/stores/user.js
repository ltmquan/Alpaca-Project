import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

let user = JSON.parse(localStorage.getItem('user'))

export const useUserStore = defineStore('user', () => {
  const id = ref(user?.id)
  const username = ref(user?.username)
  const role = ref(user?.role)

  const currUser = computed(() => { return {id: id, username: username, role: role}})
  const isLoggedIn = computed(() => { return user ? true : false })

  return { id, username, role, currUser, isLoggedIn }
})