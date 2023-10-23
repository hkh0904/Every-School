import styles from './PeopleTable.module.css'
import { useState } from 'react';

function PeopleTable() {
  const headers = [
    {
      text: '번호',
      value: 'number'
    },
    {
      text: '이름',
      value: 'name'
    },
    {
      text: '학부모 이름',
      value: 'par_name'
    },
    {
      text: '관계',
      value: 'relationship'
    },
    {
      text: '선택',
      value: 'select'
    }
  ];

  const items = [
    {
      number: 1,
      name: '하하',
      par_name: '호호',
      relationship: '모',
    },
    {
      number: 2,
      name: '아오',
      par_name: '귀찮아',
      relationship: '모',
    },
    {
      number: 3,
      name: '진짜',
      par_name: '귀찮아',
      relationship: '모',
    },
    {
      number: 4,
      name: '데이터를',
      par_name: '만들기가',
      relationship: '모',
    },
    {
      number: 5,
      name: '제일귀',
      par_name: '찮음',
      relationship: '모',
    },
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
    { number: 5, name: '제일귀', par_name: '찮음', relationship: '모'},
  ];

  const selectable = true;
  const headerKey = headers.map((header) => header.value);

  const [selectedItems, setSelectedItems] = useState([]);

  const handleCheckboxChange = (index) => {
    const updatedSelectedItems = [...selectedItems];
    updatedSelectedItems[index] = !updatedSelectedItems[index];
    setSelectedItems(updatedSelectedItems);
  };

  const handleSelectAll = () => {
    const allSelected = selectedItems.every(item => item);
    const updatedSelectedItems = items.map(() => !allSelected);
    setSelectedItems(updatedSelectedItems);
  };

  const handleSaveSelectedNames = () => {
    const selectedNames = items
      .filter((item, index) => selectedItems[index])
      .map((item) => item.name);
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
        <tbody>
          {items.map((item, index) => (
            <tr key={index}>
              {headerKey.slice(0, 4).map((key) => (
                <td key={key + index}>{item[key]}</td>
              ))}
              {selectable && (
                <td>
                  <input
                    type="checkbox"
                    checked={selectedItems[index] || false}
                    onChange={() => handleCheckboxChange(index)}
                  />
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={handleSaveSelectedNames}>선택된 이름 저장</button>
    </div>
  );
}

export default PeopleTable;