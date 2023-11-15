import React from 'react';
import { useTable, useGlobalFilter, useSortBy, usePagination } from 'react-table';
import { useNavigate } from 'react-router-dom';
import Search from './Search';
import styles from './Table.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import FirstPage from '@mui/icons-material/FirstPage';
import LastPage from '@mui/icons-material/LastPage';
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';
import { accessClass, rejectAccessClass } from '../../api/SchoolAPI/schoolAPI';
import { approveConsulting, rejectConsulting } from '../../api/ConsultingAPI/consultingAPI';
import ConsultDetailModal from '../../pages/ConsultHistory/ConsultDetailModal';
import { useState } from 'react';

export default function Table({ columns, data }) {
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    page,
    prepareRow,
    setGlobalFilter,
    canPreviousPage,
    canNextPage,
    pageCount,
    gotoPage,
    state: { pageIndex }
  } = useTable(
    {
      columns,
      data,
      initialState: { pageSize: 15 } // 페이지당 15개의 행을 보여줍니다.
    },
    useGlobalFilter,
    useSortBy,
    usePagination // usePagination 훅 추가
  );
  const navigate = useNavigate();

  // 핸들러 함수들

  const handleComplain = (complainId, reportedDate) => {
    if (typeof complainId === 'string') {
      navigate('/badcomplain/chat/detail', { state: { complainId, reportedDate } });
    } else if (typeof complainId === 'number') {
      navigate('/badcomplain/call/detail', { state: { complainId } });
    }
  };

  const handleNotiDetail = (data) => {
    const boardId = data;
    navigate('/docs/register-noti/detail', { state: { boardId } });
  };
  const handlePayNotiDetail = (data) => {
    const boardId = data;
    navigate('/docs/register-payment/detail', { state: { boardId } });
  };

  const handleClick = (data) => {
    const reportId = data;
    navigate('/report/detail', { state: { reportId: reportId } });
  };

  //핛급 신청 승인
  const handleAccessClick = async (row) => {
    const applyId = await row.original.schoolApplyId;

    const response = await accessClass(applyId);
    console.log(response);

    if (response['message'] === 'SUCCESS') {
      alert('학급 신청을 승인하셨습니다.');
    } else {
      alert('서버에 오류가 생겼습니다. 관리자에게 문의하세요.');
    }
  };
  //핛급 신청 거절
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

  //상담 신청 승인
  const consultAccessClick = async (row) => {
    const applyId = await row.original.consultId;
    console.log(applyId);

    const response = await approveConsulting(applyId);
    console.log(response);

    if (response['message'] === 'SUCCESS') {
      alert('상담을 승인하셨습니다.');
    } else {
      alert('서버에 오류가 생겼습니다. 관리자에게 문의하세요.');
    }
  };
  //상담 신청 거절
  const consultRejcetClick = async (row) => {
    const applyId = await row.original.consultId;
    console.log(applyId);
    const response = await rejectConsulting(applyId);
    console.log(response);
    if (response['message'] === 'SUCCESS') {
      alert('상담을 거절하셨습니다.');
    } else {
      alert('서버에 오류가 생겼습니다. 관리자에게 문의하세요.');
    }
  };

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
          {page.map((row) => {
            prepareRow(row);

            return (
              <tr className={styles.tableEle} {...row.getRowProps()}>
                {row.cells.map((cell) => {
                  // 그냥 상담
                  if (cell.column.id === 'detail') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        <div
                          className={styles.row}
                          style={{ cursor: 'pointer' }}
                          onClick={() => handleClick(cell.row.values.reportId)}
                        >
                          <span className={styles.detailText}>상세내역 확인</span>
                          <SvgIcon component={KeyboardArrowRightIcon} inheritViewBox style={{ color: '#449D87' }} />
                        </div>
                      </td>
                    );

                    //상담 디테일
                  } else if (cell.column.id === 'approvereject') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        <div className={styles.row} style={{ cursor: 'pointer' }}>
                          <span className={styles.detailText}>
                            <button style={{ backgroundColor: 'blue' }} onClick={() => consultAccessClick(row)}>
                              <p>승인</p>
                            </button>
                            <button style={{ backgroundColor: 'red' }} onClick={() => consultRejcetClick(row)}>
                              <p>거절</p>
                            </button>
                          </span>
                        </div>
                      </td>
                    );
                    //학급승인
                  } else if (cell.column.id === 'approve') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        <div className={styles.row} style={{ cursor: 'pointer' }}>
                          <span className={styles.detailText}>
                            <button style={{ backgroundColor: 'blue' }} onClick={() => handleAccessClick(row)}>
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
                  } else if (cell.column.id === 'titlepay') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        <div
                          className={styles.row}
                          style={{ cursor: 'pointer' }}
                          onClick={() => handlePayNotiDetail(cell.row.values.boardId)}
                        >
                          {cell.render('Cell')}
                          {/*<span className={styles.detailText}>상세내역 확인</span>*/}
                          {/*<SvgIcon component={KeyboardArrowRightIcon} inheritViewBox style={{ color: '#449D87' }} />*/}
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
                  } else if (cell.column.id === 'title') {
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
      {/* 페이지네이션 컨트롤 */}
      <div className={styles.pagination}>
        <div className={styles.pageButton} onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
          <SvgIcon component={FirstPage} inheritViewBox />
        </div>
        {Array.from({ length: pageCount }, (_, i) => i).map((pageNum) => (
          <div
            key={pageNum}
            onClick={() => gotoPage(pageNum)}
            disabled={pageIndex === pageNum}
            className={styles.pageButton}
          >
            {pageNum + 1}
          </div>
        ))}
        <div className={styles.pageButton} onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
          <SvgIcon component={LastPage} inheritViewBox />
        </div>
      </div>
    </>
  );
}
