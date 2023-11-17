import axios from 'axios';
// const APPLICATION_SERVER_URL =
// process.env.NODE_ENV === 'production' ? 'https://every-school.com/api' : 'http://localhost:8080';

export const baseAxios = axios.create({
  baseURL: '/api',

  headers: {
    'Content-Type': 'application/json'
    // Authorization: sessionStorage.getItem('token')
  }
});
