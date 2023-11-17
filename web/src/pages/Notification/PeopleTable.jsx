import { getParentList } from '../../api/SchoolAPI/schoolAPI';
import styles from './PeopleTable.module.css';
import { useEffect, useState } from 'react';

export default function PeopleTable({ selectAll }) {
  const [patentList, setPatentList] = useState([]);

  useEffect(() => {
    const fetchParentList = async () => {
      try {
        const newList = await getParentList();
        console.log(newList);
        setPatentList(newList.content);
      } catch (error) {
        console.error(error);
      }
    };
    fetchParentList();
  }, []);

  const headers = [
    {
      text: '번호',
      value: 'studentNumber'
    },
    {
      text: '이름',
      value: 'studentName'
    },
    {
      text: '학부모 이름',
      value: 'name'
    },
    {
      text: '관계',
      value: 'parentType'
    },
    {
      text: '선택',
      value: 'select'
    }
  ];

  const selectable = true;
  const headerKey = headers.map((header) => header.value);

  const [selectedItems, setSelectedItems] = useState([]);

  const handleCheckboxChange = (index) => {
    const updatedSelectedItems = [...selectedItems];
    updatedSelectedItems[index] = !updatedSelectedItems[index];
    setSelectedItems(updatedSelectedItems);
    handleSaveSelectedNames();
  };

  useEffect(() => {
    const updatedSelectedItems = patentList.map(() => selectAll);
    setSelectedItems(updatedSelectedItems);
    const selectedNames = patentList.filter((item, index) => updatedSelectedItems[index]).map((item) => item.name);
    console.log(selectedNames);
  }, [selectAll]);

  const handleSaveSelectedNames = () => {
    const selectedNames = patentList.filter((item, index) => selectedItems[index]).map((item) => item.userId);
    console.log(selectedNames);
  };

  return (
    <div>
      <table className={styles.Peopletable}>
        <thead className={styles.tableHeader}>
          <tr>
            {headers.map((header) => (
              <th key={header.value}>{header.text}</th>
            ))}
          </tr>
        </thead>
        <tbody className={styles.tableBody}>
          {patentList.map((item, index) => (
            <tr key={index}>
              {headerKey.slice(0, 4).map((key) => (
                <td key={key + index}>
                  {key === 'studentNumber' ? String(item[key]).slice(-2).replace(/^0+/, '') || '0' : item[key]}
                </td>
              ))}
              {selectable && (
                <td>
                  <input
                    type='checkbox'
                    checked={selectedItems[index] || false}
                    onChange={() => handleCheckboxChange(index)}
                  />
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
