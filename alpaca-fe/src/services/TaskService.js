import axios from 'axios'
import authHeader from './auth-header'

const url = 'http://localhost:8080/task'

class TaskService {
  findById(id) {
    return axios.get(`${url}/${id}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  findByUserId(userId) {
    return axios.get(`${url}/user/${userId}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  findByParentId(parentId) {
    return axios.get(`${url}/parent/${parentId}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  create(task) {
    return axios.post(url, task, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  update(task) {
    return axios.put(url, task, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  markFinished(id) {
    return axios.put(`${url}/finished/${id}`, null, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  markUnfinished(id) {
    return axios.put(`${url}/unfinished/${id}`, null, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  deleteById(id) {
    return axios.delete(`${url}/${id}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }
}

export default new TaskService();