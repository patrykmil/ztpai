import React from 'react';
import styles from './Security.module.css';

const FieldInfo = ({field}) => (
    <>
        {field.state.meta.isTouched && field.state.meta.errors.length ? (
            <em className={styles.validationError}>{field.state.meta.errors.map((err) => err.message).join(',')}</em>
        ) : <div className={styles.errorMessagePlaceholder}></div>}
        {field.state.meta.isValidating ? 'Validating...' : null}
    </>
);

export default FieldInfo;