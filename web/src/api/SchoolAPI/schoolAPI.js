import { baseAxios } from '../Axios.jsx';

export const getParentList = async () => {
  try {
    const response = await baseAxios.get(`/school-service/v1/schools/1/classes/2023/parents`, {
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
