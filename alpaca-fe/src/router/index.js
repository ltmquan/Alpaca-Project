import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import SignUp from '../views/Signup.vue'
import Tasks from '../views/Tasks.vue'
import Logout from '../views/Logout.vue'
import TaskCreate from '../views/TaskCreate.vue'
import TaskEdit from '../views/TaskEdit.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      component: Login
    },
    {
      path: '/signup',
      component: SignUp
    },
    {
      path: '/tasks',
      component: Tasks
    },
    {
      path: '/logout',
      component: Logout
    },
    {
      path: '/task/create',
      component: TaskCreate
    },
    {
      path: '/task/create/:parentId',
      component: TaskCreate
    }, 
    {
      path: '/task/edit/:id',
      component: TaskEdit
    }
  ]
})

export default router
