import styles from "./Component.module.css";


const ComponentTags = ({tags}) => {
    const tagObjects = JSON.parse(tags);
    return (
        <div className={styles.tagButton}>
            {Object.entries(tagObjects).map(([key, value]) => (
                <span className={styles.tag} key={key} style={{backgroundColor: `#${value}`}}>
                    {key}
                </span>
            ))}
        </div>
    );
};

export default ComponentTags