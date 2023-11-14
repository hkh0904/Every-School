import { baseAxios } from '../Axios.jsx';

export const clickNotiRegister = async (e, data) => {
  e.preventDefault();
  const formData = new FormData();
  formData.append('title', data.title);
  formData.append('content', data.content);
  formData.append('isUsedComment', false);
  // formData.append('files', data.fileName);

  data.fileName.forEach((image) => {
    formData.append('files', image);
  });

  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.post(
      `/board-service/v1/web/${schoolYear}/schools/${schoolNum}/communication-boards`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    console.log(response.data);
    return 1;
  } catch (error) {
    return 0;
  }
};

export const clickPayRegister = async (e, data) => {
  e.preventDefault();
  const formData = new FormData();
  formData.append('title', data.title);
  formData.append('content', data.content);
  formData.append('isUsedComment', false);
  // formData.append('files', data.fileName);

  data.fileName.forEach((image) => {
    formData.append('files', image);
  });

  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.post(
      `/board-service/v1/web/${schoolYear}/schools/${schoolNum}/notice-boards`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    console.log(response.data);
    return 1;
  } catch (error) {
    return 0;
  }
};

export const getNotices = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.get(
      `/board-service/v1/web/${schoolYear}/schools/${schoolNum}/communication-boards`,
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

export const getNotice = async (boardId) => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.get(
      `/board-service/v1/app/${schoolYear}/schools/${schoolNum}/communication-boards/${boardId}`,
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
