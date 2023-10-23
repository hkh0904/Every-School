import React from "react";
import { useTable, useGlobalFilter, useSortBy } from "react-table";
import Search from "./Search";
import styles from "./StudentTable.module.css";

function StudentTable({ columns, data }) {
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    setGlobalFilter,
  } = useTable({ columns, data }, useGlobalFilter, useSortBy);

  return (
    <>
      <Search onSubmit={setGlobalFilter} />
      <table className={styles.studentTable} {...getTableProps()}>
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr
              className={styles.tableEle}
              {...headerGroup.getHeaderGroupProps()}
            >
              {headerGroup.headers.map((column) => (
                <th
                  className={styles.tableEle}
                  {...column.getHeaderProps(column.getSortByToggleProps())}
                >
                  {column.render("Header")}
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
                  return (
                    <td className={styles.tableEle} {...cell.getCellProps()}>
                      {cell.column.id === "parent"
                        ? cell.value
                          ? "O"
                          : "X"
                        : cell.render("Cell")}
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
    </>
  );
}

export default StudentTable;
