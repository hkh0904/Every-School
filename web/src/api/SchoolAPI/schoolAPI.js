import { baseAxios } from '../Axios.jsx';

export const getParentList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  const classNum = sessionStorage.getItem('classNum');
  try {
    const response = await baseAxios.get(
      `/school-service/v1/web/${schoolYear}/schools/${schoolNum}/classes/${classNum}/parents`,
      {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    return response.data.data;
  } catch (error) {
    return 0;
  }
};

export const getStudentList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  const classNum = sessionStorage.getItem('classNum');
  try {
    const response = await baseAxios.get(
      `/school-service/v1/web/${schoolYear}/schools/${schoolNum}/classes/${classNum}/students`,
      {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    return response.data.data;
  } catch (error) {
    return 0;
  }
};

export const getApplies = async (pageIdx) => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  try {
    const response = await baseAxios.get(`/school-service/v1/web/${schoolYear}/schools/${schoolNum}/applies?status=${pageIdx}`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });

    return response.data.data;
  } catch (error) {
    return 0;
  }
};

export const getClassAccess = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  try {
    const response = await baseAxios.get(`/school-service/v1/web/${schoolYear}/schools/${schoolNum}/wait-apply`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });

    return response.data.data;
  } catch (error) {
    return 0;
  }
};
export const getCpltAccessClassList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  try {
    const response = await baseAxios.get(`/school-service/v1/web/${schoolYear}/schools/${schoolNum}/approve-apply`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });

    return response.data.data;
  } catch (error) {
    return 0;
  }
};
