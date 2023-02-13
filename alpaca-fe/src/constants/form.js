import { priority_level } from "./priority_level"

// LOGIN FORM
export const login_form_data = {
  title: 'Login',
  data: [
    {
      id: 'username',
      type: 'text',
      label: 'Username',
      value: ''
    },
    {
      id: 'password',
      type: 'password',
      label: 'Password',
      value: ''
    }
  ],
  submitBtn: {
    isUsed: true,
    label: 'Log in'
  },
  cancelBtn: {
    isUsed: false,
    label: 'Cancel'
  }
}

// SIGNUP FORM
export const signup_form_data = {
  title: 'Signup',
  data: [
    {
      id: 'username',
      type: 'text',
      label: 'Username',
      value: ''
    },
    {
      id: 'password',
      type: 'password',
      label: 'Password',
      value: ''
    },
    {
      id: 'confirmPassword',
      type: 'password',
      label: 'Confirm Password',
      value: ''
    }
  ],
  submitBtn: {
    isUsed: true,
    label: 'Sign up'
  },
  cancelBtn: {
    isUsed: false,
    label: 'Cancel'
  }
}

// TASK CREATE FORM
export const task_create_form = {
  title: 'Create Task',
  data: [
    {
      id: 'name',
      type: 'text',
      label: 'Name',
      value: ''
    },
    {
      id: 'deadline',
      type: 'date',
      label: 'Deadline',
      value: ''
    },
    {
      id: 'priorityLevel',
      type: 'select',
      options: priority_level,
      label: 'Priority Level',
      value: ''
     },
     {
      id: 'description',
      type: 'textarea',
      label: 'Description',
      value: ''
     },
  ],
  submitBtn: {
    isUsed: true,
    label: 'Create'
  },
  cancelBtn: {
    isUsed: true,
    label: 'Cancel'
  }
}

// TASK EDIT FORM
export const task_edit_form = {
  title: 'Edit Task',
  data: [
    {
      id: 'name',
      type: 'text',
      label: 'Name',
      value: ''
    },
    {
      id: 'deadline',
      type: 'date',
      label: 'Deadline',
      value: ''
    },
    {
      id: 'priorityLevel',
      type: 'select',
      options: priority_level,
      label: 'Priority Level',
      value: ''
     },
     {
      id: 'description',
      type: 'textarea',
      label: 'Description',
      value: ''
     },
     {
      id: 'parentId',
      type: 'tree',
      options: [],
      label: 'Parent',
      value: ''
     },
     {
      id: 'finished',
      type: 'checkbox',
      label: 'Finished',
      value: false
     }
  ],
  submitBtn: {
    isUsed: true,
    label: 'Save'
  },
  cancelBtn: {
    isUsed: true,
    label: 'Cancel'
  }
}
