import { login } from '../../api/UserAPI/userAPI';

export const onChangeEmail = function (e, setEmail, setEmailMessage, setEmailBtn) {
  const currentEmail = e.target.value;
  setEmail(currentEmail);
  const emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

  if (!emailRegExp.test(currentEmail)) {
    setEmailMessage('이메일의 형식이 올바르지 않습니다.');
    setEmailBtn(false);
  } else {
    setEmailMessage('');
    setEmailBtn(true);
  }
};

export const onChangePassword = function (e, setPassword, setPasswordMessage, setIsPassword) {
  const currentPassword = e.target.value;
  setPassword(currentPassword);
  const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
  if (!passwordRegExp.test(currentPassword)) {
    setPasswordMessage('숫자, 영문, 특수문자 조합으로 8자리 이상 입력해주세요.');
    setIsPassword(false);
  } else {
    setPasswordMessage('');
    setIsPassword(true);
  }
};

export const onChangePasswordConfirm = function (
  e,
  password,
  setPasswordConfirm,
  setPasswordConfirmMessage,
  setIsPasswordConfirm
) {
  const currentPasswordConfirm = e.target.value;
  setPasswordConfirm(currentPasswordConfirm);
  if (password !== currentPasswordConfirm) {
    setPasswordConfirmMessage('같은 비밀번호를 입력해주세요.');
    setIsPasswordConfirm(false);
  } else {
    setPasswordConfirmMessage('');
    setPasswordConfirm(currentPasswordConfirm);
    setIsPasswordConfirm(true);
  }
};

export const clickLogin = async (e, data) => {
  e.preventDefault();
  const response = await login(data);
  if (response === 0) {
    alert('아이디와 비밀번호를 확인해주세요');
    return 0;
  } else {
    sessionStorage.clear();
    sessionStorage.setItem('token', response.token);
    return 1;
  }
};
