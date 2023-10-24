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
    const detailData = data
    navigate('/report/detail', {state: { detailData: detailData }})
  }

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
                  let cellValue = String(cell.value);
                  // 'parent' 열의 값 처리
                  if (cell.column.id === 'parent' || cell.column.id === 'status') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        {cell.value ? 'O' : 'X'}
                      </td>
                    );
                  }
                  // 'birth' 열의 값 처리
                  else if (cell.column.id === 'birth') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        {`${cellValue.slice(0, 4)}.${cellValue.slice(4, 6)}.${cellValue.slice(6)}`}
                      </td>
                    );
                  }
                  // 'tel' 열의 값 처리
                  else if (cell.column.id === 'tel') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        {`${cell.value.slice(0, 3)}-${cell.value.slice(3, 7)}-${cell.value.slice(7)}`}
                      </td>
                    );
                  }
                  // 'detail' 열의 값 처리
                  else if (cell.column.id === 'detail') {
                    return (
                      <td className={styles.tableEle} {...cell.getCellProps()}>
                        <div className={styles.row} style={{cursor: 'pointer'}} onClick={() => handleClick(cell.row.values.detail)}>
                            <span className={styles.detailText}>상세내역 확인</span>
                          <SvgIcon component={KeyboardArrowRightIcon} inheritViewBox style={{ color: '#449D87' }} />
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
