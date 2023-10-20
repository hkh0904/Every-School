import axios from 'axios';

export const baseAxios = axios.create({
  baseURL: 'https://localhost:8080/api/',
  // baseURL: '',
  headers: {
    'Content-Type': 'application/json'
    // Authorization: sessionStorage.getItem("token"),
  }
});
