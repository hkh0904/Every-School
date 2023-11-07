import { baseAxios } from '../Axios.jsx';

export const receivedReportInfo = async () => {
  try {
    // 상태 코드 배열
    const statusCodes = ['7001', '7002', '7003'];
    // 모든 상태 코드에 대한 요청을 병렬로 실행
    const requests = statusCodes.map((code) =>
      baseAxios.get(`/report-service/v1/web/2023/schools/100000/reports?status=${code}`, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      })
    );
    // 모든 요청이 완료될 때까지 기다림
    const responses = await Promise.all(requests);
    // 모든 응답의 'content'를 하나의 배열로 합침
    const allContents = responses.flatMap((response) => response.data.data.content);
    return allContents;
  } catch (error) {
    console.log(error);
    return []; // 에러 발생 시 빈 배열을 리턴할 수 있습니다.
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
