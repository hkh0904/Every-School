import { useEffect, useMemo, useState } from 'react';
import styles from './ConsultHistory.module.css';

import ConsultCompleteCard from './ConsultCompleteCard';
import ConsultRejectCard from './ConsultRejectCard';
import { getCompliteConsulting, getConsults } from '../../api/ConsultingAPI/consultingAPI';
import styles2 from '../ManageClassPage/ManageMyclassPage.module.css';
import Table from '../../component/Table/Table';
import { getApplies } from '../../api/SchoolAPI/schoolAPI';

export default function ConsultHistory() {
  const [pageIdx, setPageIdx] = useState(5002);
  const [pageText, setPageText] = useState('상담 예정');
  const [consults, setConsults] = useState([]);
  const [totalConsults, setTotalConsults] = useState(0);

  useEffect(() => {
    fetchConsults(pageIdx);
  }, [pageIdx]);

  const fetchConsults = async (pageIdx) => {
    try {
      const data = await getConsults(pageIdx);
      console.log(data);
      if (data && Array.isArray(data.content)) {
        const transformedData = data.content.map((consult) => ({
          type: consult.type,
          grade: consult.parentInfo.split(' ')[0].replace('학년', ''),
          class: consult.parentInfo.split(' ')[1].replace('반', ''),

          name: consult.parentInfo.split(' ')[3],
          relationship: consult.parentInfo.split(' ')[4] === '아버님' ? '부' : '모',
          lastModifiedDate: consult.lastModifiedDate.split('T')[0] + ' ' + consult.lastModifiedDate.split('T')[1],
          consultReason: '진로상담',
          rejectReason: '수업으로 인한 거절'
          // add other fields if necessary
        }));
        setConsults(transformedData);
        setTotalConsults(data.count);
      } else {
        // handleRetry();
      }
    } catch (error) {
      console.error('Failed to fetch students:', error);
    }
  };

  const columns = [
    [
      {
        accessor: 'type',
        Header: '상담 유형'
      },
      {
        accessor: 'grade',
        Header: '학년'
      },
      {
        accessor: 'class',
        Header: '반'
      },
      {
        accessor: 'name',
        Header: '학생 이름'
      },
      {
        accessor: 'relationship',
        Header: '관계'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '신청 시간'
      },
      {
        accessor: 'complainDetail',
        Header: '상담 내용'
      }
    ],
    [
      {
        accessor: 'type',
        Header: '상담 유형'
      },
      {
        accessor: 'grade',
        Header: '학년'
      },
      {
        accessor: 'class',
        Header: '반'
      },
      {
        accessor: 'name',
        Header: '학생 이름'
      },
      {
        accessor: 'relationship',
        Header: '관계'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '상담 완료 시간'
      },
      {
        accessor: 'consultReason',
        Header: '상담 내용'
      }
    ],
    [
      {
        accessor: 'type',
        Header: '상담 유형'
      },
      {
        accessor: 'grade',
        Header: '학년'
      },
      {
        accessor: 'class',
        Header: '반'
      },

      {
        accessor: 'name',
        Header: '학생 이름'
      },
      {
        accessor: 'relationship',
        Header: '관계'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '거절 시간'
      },
      {
        accessor: 'rejectReason',
        Header: '거절 사유'
      }
    ]
  ];

  return (
    <div className={styles.consultHistory}>
      <div className={styles.title}>
        <p>상담 내역</p>
        <p>
          {pageText} : {totalConsults}건{' '}
        </p>
      </div>
      <div className={styles.consultClass}>
        <div className={styles.consultTab}>
          <div
            onClick={() => {
              setPageIdx(5002);
            }}
          >
            <p style={5002 === pageIdx ? { backgroundColor: 'white' } : null}>
              <b>상담 예정</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(5003);
            }}
          >
            <p style={5003 === pageIdx ? { backgroundColor: 'white' } : null}>
              <b>상담 완료</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(5004);
            }}
          >
            <p style={5004 === pageIdx ? { backgroundColor: 'white' } : null}>
              <b>상담 거절</b>
            </p>
          </div>
        </div>
        <div className={styles2.scrollContainer}>
          <Table columns={columns[pageIdx - 5002]} data={consults} />
        </div>
      </div>
    </div>
  );
}
