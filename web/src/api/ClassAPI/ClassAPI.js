import { baseAxios } from '../Axios.jsx';

export const accessClass = async (e, data) => {
  e.preventDefault();
  const notiInfo = {
    title: data.title,
    content: data.content,
    isUsedComment: false,
    files: data.files
  };

  try {
    const response = await baseAxios.get(
      ``,
      {},
      {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    return 0;
  }
};
