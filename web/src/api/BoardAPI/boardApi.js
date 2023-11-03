import { baseAxios } from '../Axios.jsx';

export const clickNotiRegister = async (e, data) => {
  e.preventDefault();
  const notiInfo = {
    title: data.title,
    content: data.content,
    isUsedComment: false,
    files: data.files
  };

  try {
    const response = await baseAxios.post(`/board-service/v1/schools/1/boards/communications`, notiInfo, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    return 0;
  }
};
