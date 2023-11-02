import { baseAxios } from '../Axios.jsx';
// 로그인

export const login = async (data) => {
  const userInfo = {
    email: data.id,
    password: data.password
  };
  console.log('로그인');

  try {
    const response = await baseAxios.post(`/user-service/login`, userInfo);
    console.log(response.headers);
    return response.headers;
  } catch (error) {
    return 0;
  }
};
