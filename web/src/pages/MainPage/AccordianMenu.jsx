import { useState } from 'react';
import styles from './AccordianMenu.module.css';
import { FaUserCog } from 'react-icons/fa';
import { MdKeyboardArrowUp, MdKeyboardArrowDown } from 'react-icons/md';
import { IoMdChatbubbles, IoMdSettings } from 'react-icons/io';
import { AiFillNotification } from 'react-icons/ai';
import { TbReport } from 'react-icons/tb';
import { NavLink } from 'react-router-dom';

export default function AccordianMenu() {
  const MenuList = [
    {
      title: '학급 관리',
      list: ['우리반 관리', '학부모 관리', '학급 승인 관리'],
      icon: FaUserCog,
      address: ['/manage/myclass', '/manage/parents', '/manage/class/']
    },
    {
      title: '상담 관리',
      list: ['상담 확인', '상담 내역', '상담 설정'],
      icon: IoMdChatbubbles,
      address: ['/consult/approve', '/consult/history', '/consult/setting']
    },
    {
      title: '안내문 등록',
      list: ['가정통신문 등록', '고지서 등록'],
      icon: AiFillNotification,
      address: ['/docs/register-noti', '/docs/register-payment']
    },
    {
      title: '접수된 신고',
      list: [],
      icon: TbReport,
      address: '/report/history'
    },
    {
      title: '악성 민원 신고',
      list: [],
      icon: TbReport,
      address: '/badcomplain/history'
    },
    {
      title: '마이페이지',
      list: [],
      icon: IoMdSettings,
      address: '/mypage'
    }
  ];

  const [expandedIndex, setExpandedIndex] = useState(null);

  const handleToggle = (index) => {
    if (expandedIndex === index) {
      setExpandedIndex(null);
    } else {
      setExpandedIndex(index);
    }
  };

  return (
    <div className={styles.AccordianMenu}>
      {MenuList.map((menu, index) => (
        <div key={index}>
          <div className={styles.accordianTitle} onClick={() => handleToggle(index)}>
            {index === 5 || index === 4  || index === 3 ? ( // 3은 "접수된 신고"의 인덱스입니다.
              <NavLink to={menu.address} className={({ isActive }) => [isActive ? styles.isActive : styles.titleLink]}>
                {menu.icon()}
                <p>{menu.title}</p>
              </NavLink>
            ) : (
              <div className={styles.accordianMain}>
                {menu.icon()}
                <p>{menu.title}</p>
              </div>
            )}
            <p className={styles.accordianExpand}>
              {menu.list.length > 0 ? expandedIndex === index ? <MdKeyboardArrowUp /> : <MdKeyboardArrowDown /> : null}
            </p>
          </div>
          {expandedIndex === index && menu.list.length > 0 && (
            <ul className={styles.accordionList}>
              {menu.list.map((item, itemIndex) => (
                <li key={itemIndex}>
                  <NavLink
                    to={menu.address[itemIndex]}
                    className={({ isActive }) => [isActive ? styles.itemActive : styles.itemInActive]}
                  >
                    {item}
                  </NavLink>
                </li>
              ))}
            </ul>
          )}
        </div>
      ))}
    </div>
  );
}
