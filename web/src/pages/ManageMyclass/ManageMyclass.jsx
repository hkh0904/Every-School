import React from "react";
import { useMemo } from "react";
import StudentTable from "./StudentTable";
import styles from "./ManageMyclass.module.css";

const ManageMyclass = () => {
  const columns = useMemo(
    () => [
      {
        accessor: "grade",
        Header: "학년",
      },
      {
        accessor: "class",
        Header: "반",
      },
      {
        accessor: "number",
        Header: "번호",
      },
      {
        accessor: "name",
        Header: "이름",
      },
      {
        accessor: "birth",
        Header: "생년월일",
      },
      {
        accessor: "tel",
        Header: "연락처",
      },
      {
        accessor: "parent",
        Header: "학부모 연락처",
      },
    ],
    []
  );

  // const data = useMemo(
  //   () =>
  //     Array(53)
  //       .fill()
  //       .map(() => ({
  //         name: faker.name.lastName() + faker.name.firstName(),
  //         email: faker.internet.email(),
  //         phone: faker.phone.phoneNumber(),
  //       })),
  //   []
  // );

  const data = [
    {
      grade: 1,
      class: 3,
      number: 1,
      name: "신성주",
      birth: 19970101,
      tel: "01012345678",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 2,
      name: "임우택",
      birth: 19970104,
      tel: "01022223333",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 3,
      name: "이지혁",
      birth: 19970223,
      tel: "01033334444",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      name: "홍경환",
      birth: 19970302,
      tel: "01055556666",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      name: "홍경환",
      birth: 19970302,
      tel: "01055556666",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      name: "홍경환",
      birth: 19970302,
      tel: "01055556666",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      name: "홍경환",
      birth: 19970302,
      tel: "01055556666",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      name: "홍경환",
      birth: 19970302,
      tel: "01055556666",
      parent: true,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      name: "홍경환",
      birth: 19970302,
      tel: "01055556666",
      parent: true,
    },
  ];

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div>우리반 보기</div>
          <div>총원 : OO명</div>
        </div>
        <div>
          <button>추가</button>
        </div>
      </div>
      <hr />
      <br />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <StudentTable columns={columns} data={data} />
        </div>
      </div>
    </div>
  );
};

export default ManageMyclass;
