import React from "react";
import styles from "./StudentTable.module.css";

function Search({ onSubmit }) {
  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit(event.target.elements.filter.value);
  };

  return (
    <form className={styles.tableSearch} onSubmit={handleSubmit}>
      <input name="filter" />
      <button>Search</button>
    </form>
  );
}

export default Search;