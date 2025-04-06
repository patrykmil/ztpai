import ComponentTags from "./ComponentTags.jsx";
import styles from "./Component.module.css"

const ComponentDetails = ({component}) => (
    <>
        <p className={styles.paragraph}>by {component.author.username}</p>
        <p className={styles.paragraph}>this item is part of <span
            style={{color: `#${component.color.hex}`, textDecoration: `underline`}}>{component.set.name}</span> set</p>
        <ComponentTags tags={component.tags}/>
    </>
);

export default ComponentDetails;