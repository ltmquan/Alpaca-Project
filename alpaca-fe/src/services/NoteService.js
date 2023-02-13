import axios from 'axios'
import authHeader from './auth-header'

const url = 'http://localhost:8080/note'

class NotebookService {
  findById(id) {
    return axios.get(`${url}/${id}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  findByTaskId(taskId) {
    return axios.get(`${url}/task/${taskId}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  create(note) {
    return axios.post(url, note, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  update(note) {
    return axios.put(url, note, { headers: authHeader() }).then(
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

export default new NotebookService();