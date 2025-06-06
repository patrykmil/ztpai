import ComponentTags from "../single/ComponentTags.jsx";
import styles from "../Component.module.css";

const ComponentDetails = ({maxTags, component}) => (
    <>
        <div className={styles.userInfo}>
            <p className={`${styles.paragraph} ${styles.inline}`}>by {component.author.name}</p>
            <img src={`/avatars/${component.author.avatar.avatarPath}`} alt={'Avatar'} className={styles.avatar}></img>
        </div>
        <p className={styles.paragraph}>this item is part of <span
            style={{color: `#${component.color.hex}`, textDecoration: `underline`}}>{component.set.name}</span> set</p>
        <ComponentTags max={maxTags} tags={component.tags}/>
    </>
);

export default ComponentDetails;