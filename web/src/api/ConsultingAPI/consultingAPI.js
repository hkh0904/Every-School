import { baseAxios } from '../Axios.jsx';

//상담 예정 목록
export const getConsultingList = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  try {
    const response = await baseAxios.get(
      `/consult-service/v1/web/${schoolYear}/schools/${schoolNum}/consults?status=5001`,
      {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    return response.data.data.content;
  } catch (error) {
    return 0;
  }
};
//상담 승인
export const approveConsulting = async (consultId) => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.patch(
      `/consult-service/v1/web/${schoolYear}/schools/${schoolNum}/consults/${consultId}/approve`
    );
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
//상담 거절
export const rejectConsulting = async (consultId, reason) => {
  const rejectedReason = {
    rejectedReason: reason
  };
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');

  try {
    const response = await baseAxios.patch(
      `/consult-service/v1/web/${schoolYear}/schools/${schoolNum}/consults/${consultId}/reject`,
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

export const getConsultingMessage = async () => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  try {
    const response = await baseAxios.get(
      `/consult-service/v1/app/${schoolYear}/schools/${schoolNum}/consult-schedules`,
      {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    console.log(response.data.data);
    return response.data.data;
  } catch (error) {
    return 0;
  }
};

export const modifyConsultMsg = async (consultScheduleId, description) => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  const info = {
    description: description
  };

  try {
    const response = await baseAxios.patch(
      `/consult-service/v1/app/${schoolYear}/schools/${schoolNum}/consult-schedules/${consultScheduleId}/description`,
      info,
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

export const modifyConsultTime = async (consultScheduleId, times) => {
  const schoolNum = sessionStorage.getItem('schoolId');
  const schoolYear = sessionStorage.getItem('year');
  console.log(times.monday);
  const info = {
    monday: times.monday,
    tuesday: times.tuesday,
    wednesday: times.wednesday,
    thursday: times.thursday,
    friday: times.friday
  };

  try {
    const response = await baseAxios.patch(
      `/consult-service/v1/app/${schoolYear}/schools/${schoolNum}/consult-schedules/${consultScheduleId}/schedules`,
      info,
      {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }
    );
    console.log(response.data);
    return response.data.data;
  } catch (error) {
    return 0;
  }
};
