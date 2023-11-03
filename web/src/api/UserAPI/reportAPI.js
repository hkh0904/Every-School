import { baseAxios } from '../Axios.jsx';

export const receivedReportInfo = async () => {
  try {
    const response = await baseAxios.get(`/report-service/v1/schools/1/received-reports`, {
      // headers: {
      //   Authorization: `Bearer ${sessionStorage.getItem('token')}`
      // }
    });
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
};

export const processedReportInfo = async () => {
  try {
    const response = await baseAxios.get(`/report-service/v1/schools/1/processed-reports`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
};


