import { baseAxios } from '../Axios.jsx';

export const getParentList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.get(`/school-service/v1/web/${schoolYear}/schools/${schoolNum}/parents`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    console.log(response.data);
    return response.data.data;
  } catch (error) {
    return 0;
  }
};

export const getStudentList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  try {
    const response = await baseAxios.get(`/school-service/v1/web/${schoolYear}/schools/${schoolNum}/students`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    // console.log(response.data);
    return response.data.data;
  } catch (error) {
    return 0;
  }
};
