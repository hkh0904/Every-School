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

export const getUserInfo = async () => {
  try {
    const response = await baseAxios.get(`/user-service/v1/info`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
};

export const emailAuthNum = async (email) => {
  try {
    const response = await baseAxios.post(`/user-service/auth/email`, {
      email: email
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
