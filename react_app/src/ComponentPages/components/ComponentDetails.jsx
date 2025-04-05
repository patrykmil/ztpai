import ComponentTags from "./ComponentTags.jsx";
import styles from "./Component.module.css"

const ComponentDetails = ({component}) => (
    <>
        <p className={styles.paragraph}>by {component.username}</p>
        <p className={styles.paragraph}>this item is part of <span
            style={{color: `#${component.hex}`, textDecoration: `underline`}}>{component.setName}</span> set</p>
        <ComponentTags tags={component.tags}/>
    </>
);

export default ComponentDetails;