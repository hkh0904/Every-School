import { baseAxios } from '../Axios.jsx';

export const getConsultingList = async () => {
  try {
    const response = await baseAxios.get(`/consult-service/v1/web/2023/schools/21617/consults?status=5001`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    });
    return response.data.data.content;
  } catch (error) {
    return 0;
  }
};

export const approveConsulting = async (consultId) => {
  try {
    const response = await baseAxios.patch(`/consult-service/v1/web/2023/schools/21617/consults/${consultId}/approve`);
    console.log(response.data);
    if (response.data.message === 'SUCCESS') {
      alert('상담 승인이 완료되었습니다.');
      window.location.reload();
    }
    return response.data;
  } catch (error) {
    return 0;
  }
};

export const rejectConsulting = async (consultId, reason) => {
  const rejectedReason = {
    rejectedReason: reason
  };

  try {
    const response = await baseAxios.patch(
      `/consult-service/v1/web/2023/schools/21617/consults/${consultId}/reject`,
      rejectedReason
    );
    console.log(response.data);
    if (response.data.message === 'SUCCESS') {
      alert('거절되었습니다.');
      window.location.reload();
    }
    return response.data;
  } catch (error) {
    return 0;
  }
};
