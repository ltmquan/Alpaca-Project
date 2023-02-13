export const left_navbar_items = [
  {
    label: 'Home',
    icon: 'pi pi-home',
    to: '/'
  },
  {
    label: 'User',
    icon: 'pi pi-user',
    to: '/user',
    loggedin: true
  },
  {
    label: 'Tasks',
    icon: 'pi pi-list',
    to: '/tasks',
    loggedin: true
  },
]

export const right_navbar_items = [
  {
    label: 'Log in',
    icon: 'pi pi-sign-in',
    to: '/login',
    loggedin: false
  },
  {
    label: 'Sign up',
    icon: 'pi pi-user-plus',
    to: '/signup',
    loggedin: false
  },
  {
    label: 'Log out',
    icon: 'pi pi-sign-out',
    to: '/logout',
    loggedin: true
  },
]