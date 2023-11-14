import { useEffect, useMemo, useState } from 'react';
import styles from './ManageClassPage.module.css';
import styles2 from './ManageMyclassPage.module.css';
import ApproveModal from './ApproveModal';
import { getApplies } from '../../api/SchoolAPI/schoolAPI';
import Table from '../../component/Table/Table';

//axios 요청하기
export default function ManageClassPage() {
  //SPA
  const [pageIdx, setPageIdx] = useState(0);
  const [pageText, setPageText] = useState('승인 대기');
  const [active, setActive] = useState(0);
  //.모달
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [totalApplies, setTotalApplies] = useState(0);

  useEffect(() => {
    if (pageIdx === 0) {
      setPageText('승인 대기');
    } else if (pageIdx === 1) {
      setPageText('승인 완료');
    } else {
      setPageText('승인 거절');
    }
    fetchApplies(pageIdx);
  }, [pageIdx]);

  const fetchApplies = async (pageIdx) => {
    try {
      const data = await getApplies(pageIdx);
      console.log(data);
      if (data && Array.isArray(data.content)) {
        const transformedData = data.content.map((apply) => ({
          rejectedReason: '정보 불일치',
          parentName: apply.parentName,
          studentBirth: apply.studentBirth,
          parentBirth: apply.parentBirth,
          schoolApplyId: apply.schoolApplyId,
          type: apply.applyType,
          grade: apply.studentInfo.split(' ')[0].replace('학년', ''),
          class: apply.studentInfo.split(' ')[1].replace('반', ''),

          name: apply.studentInfo.split(' ')[2],
          lastModifiedDate: apply.lastModifiedDate.split('T')[0] + ' ' + apply.lastModifiedDate.split('T')[1]
          // add other fields if necessary
        }));
        setApplies(transformedData);
        setTotalApplies(data.count);
      } else {
        // handleRetry();
      }
      console.log(data);
    } catch (error) {
      console.error('Failed to fetch students:', error);
    }
  };

  const columns = [
    [
      {
        accessor: 'type',
        Header: '신청 유형'
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
        accessor: 'studentBirth',
        Header: '학생 생일'
      },
      {
        accessor: 'parentName',
        Header: '부모님 이름'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '신청 시간'
      },
      {
        accessor: 'approve',
        Header: '상세내역'
      }
    ],
    [
      {
        accessor: 'type',
        Header: '신청 유형'
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
        accessor: 'studentBirth',
        Header: '학생 생일'
      },
      {
        accessor: 'parentName',
        Header: '부모님 이름'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '승인 시간'
      }
    ],
    [
      {
        accessor: 'type',
        Header: '신청 유형'
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
        accessor: 'studentBirth',
        Header: '학생 생일'
      },
      {
        accessor: 'parentName',
        Header: '부모님 이름'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '거절 시간'
      },
      {
        accessor: 'rejectedReason',
        Header: '거절 사유'
      }
    ]
  ];

  const [applies, setApplies] = useState([]);
  return (
    <div className={styles.manageClass}>
      <div className={styles.title}>
        <p>승인 관리</p>
        <p>
          {pageText} : {totalApplies}건{' '}
        </p>
      </div>
      <div className={styles.approveClass}>
        <div className={styles.approveTab}>
          <div
            onClick={() => {
              setPageIdx(0);
              setActive(0);
            }}
          >
            <p style={active === 0 ? { backgroundColor: 'white' } : null}>
              <b>승인 대기</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(1);
              setActive(1);
            }}
          >
            <p style={active === 1 ? { backgroundColor: 'white' } : null}>
              <b>승인 완료</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(2);
              setActive(2);
            }}
          >
            <p style={active === 2 ? { backgroundColor: 'white' } : null}>
              <b>승인 거절</b>
            </p>
          </div>
        </div>
        <div className={styles2.scrollContainer}>
          <Table columns={columns[pageIdx]} data={applies} />
        </div>
      </div>
      {isModalOpen ? (
        <>
          <ApproveModal setIsModalOpen={setIsModalOpen} />
        </>
      ) : null}
    </div>
  );
}
