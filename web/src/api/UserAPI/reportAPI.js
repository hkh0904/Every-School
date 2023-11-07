import { baseAxios } from '../Axios.jsx';

// 모든 신고 내역 조회
export const receivedReportInfo = async () => {
  try {
    const statusCodes = ['7001', '7002', '7003'];
    const requests = statusCodes.map((code) =>
      baseAxios.get(`/report-service/v1/web/2023/schools/100000/reports?status=${code}`, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      })
    );
    const responses = await Promise.all(requests);
    const allContents = responses.flatMap((response) => response.data.data.content);
    return allContents;
  } catch (error) {
    console.log(error);
    return [];
  }
};

// 신고 상세내역 조회
export const reportDetailInfo = async (reportId) => {
  console.log('dddd', reportId)
  try {
    const response = await baseAxios.get(`/report-service/v1/web/2023/schools/100000/reports/${reportId}`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    console.log('dddd');
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
};
