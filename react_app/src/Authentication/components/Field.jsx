import React from 'react';
import FieldInfo from './FieldInfo.jsx';
import styles from '../Security.module.css';

const Field = ({label, field}) => (
    <div className={styles.inputDiv}>
        <label className={styles.inputLabel} htmlFor={field.name}>{label}</label>
        <input className={styles.input}
               id={field.name}
               name={field.name}
               value={field.state.value}
               onBlur={field.handleBlur}
               onChange={(e) => field.handleChange(e.target.value)}
        />
        <FieldInfo field={field}/>
    </div>
);

export default Field;