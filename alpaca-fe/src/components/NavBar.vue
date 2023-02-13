<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router'
import { useUserStore } from '../stores/user'

import Menubar from 'primevue/menubar/sfc'
import { left_navbar_items, right_navbar_items } from '../constants/navbar'

/* LIFECYCLE HOOKS */
/* ROUTE & ROUTER */
/* STORES */
const userStore = useUserStore();

/* EMIT */
/* PROPS */
/* STATES */
const isLoggedIn = ref(userStore.isLoggedIn)

/* COMPUTED */
/* WATCHERS */
/* METHODS */
</script>

<template>
  <Menubar :model="left_navbar_items">
    <template #item="{ item }">
      <RouterLink v-if="item.loggedin != null ? (item.loggedin && isLoggedIn) || (!item.loggedin && !isLoggedIn) : true" :to="item.to" custom v-slot="{ href, route, navigate, isActive, isExactActive }">
        <a :href="href" @click="navigate"
          :class="{ 'active-link': isActive, 'active-link-exact': isExactActive, 'p-menuitem-link': true }">
          <span :class="`p-menuitem-icon ${item.icon}`"></span>
          <span class="p-menuitem-text">{{ item.label }}</span>
        </a>
      </RouterLink>
    </template>

    <template #end>
      <ul class="p-menubar-root-list">
        <li v-for="item in right_navbar_items" class="p-menuitem">
          <div class="p-menuitem-content">
            <RouterLink v-if="(item.loggedin && isLoggedIn) || (!item.loggedin && !isLoggedIn)" :to="item.to" custom v-slot="{ href, route, navigate, isActive, isExactActive }">
              <a :href="href" @click="navigate"
                :class="{ 'active-link': isActive, 'active-link-exact': isExactActive, 'p-menuitem-link': true }">
                <span :class="`p-menuitem-icon ${item.icon}`"></span>
                <span class="p-menuitem-text">{{ item.label }}</span>
              </a>
            </RouterLink>
          </div>
        </li>
      </ul>
    </template>
  </Menubar>
</template>

