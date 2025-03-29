import styles from "./Component.module.css";


const ComponentTags = ({tags}) => {
    const tagObjects = JSON.parse(tags);
    return (
        <div style={{display: 'flex', gap: '0.5rem'}}>
            tags:
            {Object.entries(tagObjects).map(([key, value]) => (
                <span className={styles.tag} key={key} style={{backgroundColor: `#${value}`}}>
                    {key}
                </span>
            ))}
        </div>
    );
};

export default ComponentTags