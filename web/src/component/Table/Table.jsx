import { useTable, useGlobalFilter, useSortBy } from 'react-table';
import { useNavigate } from 'react-router-dom';
import Search from './Search';
import styles from './Table.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';
import { accessClass, rejectAccessClass } from '../../api/SchoolAPI/schoolAPI';

export default function Table({ columns, data }) {
  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, setGlobalFilter } = useTable(
    { columns, data },
    useGlobalFilter,
    useSortBy
  );

  const navigate = useNavigate();

  const handleClick = async (row) => {
    const applyId = await row.original.schoolApplyId;

    const response = await accessClass(applyId);
    console.log(response);

    if (response['message'] === 'SUCCESS') {
      alert('학급 신청을 승인하셨습니다.');
    } else {
      alert('서버에 오류가 생겼습니다. 관리자에게 문의하세요.');
    }
  };

  const handleComplain = (complainId, reportedDate) => {
    if (typeof complainId === 'string') {
      navigate('/badcomplain/chat/detail', { state: { complainId, reportedDate } });
    } else if (typeof complainId === 'number') {
      navigate('/badcomplain/call/detail', { state: { complainId } });
    }
    const handleRejcetClick = async (row) => {
      const applyId = await row.original.schoolApplyId;

      const response = await rejectAccessClass(applyId);
      console.log(response);
      if (response['message'] === 'SUCCESS') {
        alert('학급 신청을 거절하셨습니다.');
      } else {
        alert('서버에 오류가 생겼습니다. 관리자에게 문의하세요.');
      }
    };

    const handleComplain = (data) => {};

    const handleNotiDetail = (data) => {};

    return (
      <>
        <Search onSubmit={setGlobalFilter} />
        <table className={styles.table} {...getTableProps()}>
          <thead>
            {headerGroups.map((headerGroup) => (
              <tr className={styles.tableEle} {...headerGroup.getHeaderGroupProps()}>
                {headerGroup.headers.map((column) => (
                  <th className={styles.tableEle} {...column.getHeaderProps(column.getSortByToggleProps())}>
                    {column.render('Header')}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody {...getTableBodyProps()}>
            {rows.map((row) => {
              prepareRow(row);
              return (
                <tr className={styles.tableEle} {...row.getRowProps()}>
                  {row.cells.map((cell) => {
                    if (cell.column.id === 'approve') {
                      return (
                        <td className={styles.tableEle} {...cell.getCellProps()}>
                          <div className={styles.row} style={{ cursor: 'pointer' }}>
                            <span className={styles.detailText}>
                              <button style={{ backgroundColor: 'blue' }} onClick={() => handleClick(row)}>
                                <p>승인</p>
                              </button>
                              <button style={{ backgroundColor: 'red' }} onClick={() => handleRejcetClick(row)}>
                                <p>거절</p>
                              </button>
                            </span>
                          </div>
                        </td>
                      );
                    } else if (cell.column.id === 'approveday') {
                      return (
                        <td className={styles.tableEle} {...cell.getCellProps()}>
                          <div
                            className={styles.row}
                            style={{ cursor: 'pointer' }}
                            onClick={() => handleComplain(cell.row.values.complainId, cell.row.values.reportedDate)}
                          >
                            <span className={styles.detailText}>승인 날짜</span>
                            <SvgIcon component={KeyboardArrowRightIcon} inheritViewBox style={{ color: '#449D87' }} />
                          </div>
                        </td>
                      );
                    } else if (cell.column.id === 'rejectday') {
                      return (
                        <td className={styles.tableEle} {...cell.getCellProps()}>
                          <div
                            className={styles.row}
                            style={{ cursor: 'pointer' }}
                            onClick={() => handleNotiDetail(cell.row.values.boardId)}
                          >
                            {cell.render('Cell')}
                            {/*<span className={styles.detailText}>상세내역 확인</span>*/}
                            {/*<SvgIcon component={KeyboardArrowRightIcon} inheritViewBox style={{ color: '#449D87' }} />*/}
                          </div>
                        </td>
                      );
                    }
                    // 그 외의 열 처리
                    else {
                      return (
                        <td className={styles.tableEle} {...cell.getCellProps()}>
                          {cell.render('Cell')}
                        </td>
                      );
                    }
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </>
    );
  };
}
