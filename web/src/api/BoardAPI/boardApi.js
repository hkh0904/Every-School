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

  try {
    const response = await baseAxios.post(`/board-service/v1/web/2023/schools/100000/boards/communications`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    return 0;
  }
};
