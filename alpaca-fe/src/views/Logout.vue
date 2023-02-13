<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';

import userService from '../services/UserService'

/* LIFECYCLE HOOKS */
onMounted(() => {
  if (!userStore.isLoggedIn) {
    router.push('/')
  }

  userService.logout().then(
    data => {
      router.go()
    }
  )
})

/* ROUTE & ROUTER */
const router = useRouter()

/* STORES */
const userStore = useUserStore()
</script>