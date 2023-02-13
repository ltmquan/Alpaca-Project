import axios from 'axios'
import authHeader from './auth-header'

const url = 'http://localhost:8080/user'

class UserService {
  login(user) {
    return axios.post(`${url}/login`, user).then(
      res => {
        if (res.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(res.data))
        }

        return res.data
      },
      err => {
        console.log(err)
      }
    )
  }

  logout() {
    return axios.get(`${url}/logout`, { headers: authHeader() }).then(
      res => {
        localStorage.removeItem('user')

        console.log("Logging out")
        console.log(res.data)
        return res.data
      },
      err => {
        console.log(err)
      }
    )
  }

  findAll() {
    return axios.get(url, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  findById(id) {
    return axios.get(`${url}/${id}`, { headers: authHeader() }).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  create(user) {
    return axios.post(url, user).then(
      res => res.data,
      err => {
        console.log(err)
      }
    )
  }

  update(user) {
    return axios.put(url, user, { headers: authHeader() }).then(
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

export default new UserService();