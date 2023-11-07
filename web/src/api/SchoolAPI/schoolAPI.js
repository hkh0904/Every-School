import { baseAxios } from '../Axios.jsx';

export const getParentList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.get(`/school-service/v1/schools/${schoolNum}/classes/${schoolYear}/parents`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    return 0;
  }
};
