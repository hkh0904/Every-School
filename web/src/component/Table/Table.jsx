import { useTable, useGlobalFilter, useSortBy } from 'react-table';
import { useNavigate } from 'react-router-dom';
import Search from './Search';
import styles from './Table.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';

export default function Table({ columns, data }) {
  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, setGlobalFilter } = useTable(
    { columns, data },
    useGlobalFilter,
    useSortBy
  );

  const navigate = useNavigate();
  const handleClick = (data) => {
    const reportId = data;
    navigate('/report/detail', { state: { reportId: reportId } });
  };

  const handleComplain = (complainId, reportedDate) => {
    if (typeof complainId === 'string') {
      navigate('/badcomplain/chat/detail', { state: { complainId, reportedDate } });
    } else if (typeof complainId === 'number') {
      navigate('/badcomplain/call/detail', { state: { complainId } });
    }
  };

  const handleNotiDetail = (data) => {
    const boardId = data;
    navigate('/docs/register-noti/detail', { state: { boardId: boardId } });
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
          {rows.map((row) => {
            prepareRow(row);
            return (
              <tr className={styles.tableEle} {...row.getRowProps()}>
                {row.cells.map((cell) => {
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
                  } else if (cell.column.id === 'complainDetail') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        <div
                          className={styles.row}
                          style={{ cursor: 'pointer' }}
                          onClick={() => handleComplain(cell.row.values.complainId, cell.row.values.reportedDate)}
                        >
                          <span className={styles.detailText}>상세내역 확인</span>
                          <SvgIcon component={KeyboardArrowRightIcon} inheritViewBox style={{ color: '#449D87' }} />
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
    </>
  );
}
