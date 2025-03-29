import styles from "./Component.module.css";


const ComponentHeader = ({component}) => (
    <span className={styles.componentName} style={{color: `#${component.hex}`}}>
        {component.name}
    </span>
);

export default ComponentHeader