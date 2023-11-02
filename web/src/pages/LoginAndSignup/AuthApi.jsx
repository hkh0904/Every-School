// 로그인, 회원가입 요청

import axios from 'axios';

export const AuthApi = axios.create({
  baseURL: '/'
});

export const clickSignup = async function (e, data) {
  e.preventDefault();
  const userInfo = {
    userName: data.userName,
    userEmail: data.email,
    userPhone: data.phoneNumber,
    userId: data.id,
    userPwd: data.password,
    userGrade: 'USER'
  };
  try {
    const response = await AuthApi.post(`account/signup`, userInfo);
    return response;
  } catch (err) {
    alert('정보를 다시 한번 확인해주세요');
    return err.message;
  }
};

export const login = async (data) => {
  const userInfo = {
    userId: data.id,
    userPwd: data.password
  };

  try {
    const response = await AuthApi.post(`account/login`, userInfo);
    return response.data;
  } catch (error) {
    return 0;
  }
};

export const checkIdApi = function (e, id, setIdMessage, setIsId) {
  e.preventDefault();
  axios
    .get('/account/checkid', { params: { userId: id } })
    .then((res) => {
      if (res.data === 'success') {
        setIdMessage('사용 가능한 아이디입니다.');
        setIsId(true);
      } else {
        setIdMessage('이미 존재하는 아이디입니다.');
        setIsId(false);
      }
    })
    .catch((err) => {
      // console.log(err)
    });
};

export const checkEmailAuth = function (e, email, emailAuth, setEmailMessage, setAuthorizedEmail, setAuthMessage) {
  e.preventDefault();
  axios
    .post(`/account/auth/checkEmail/${emailAuth}`, { userEmail: email })
    .then((res) => {
      if (res.data) {
        setAuthMessage('인증에 성공하였습니다.');
        setEmailMessage(''); // Correct way to update setEmailMessage state
        setAuthorizedEmail(true);
      } else {
        setAuthMessage('인증번호를 다시 확인해주세요');
        setAuthorizedEmail(false);
      }
    })
    .catch((err) => {
      // console.log(err);
    });
};
