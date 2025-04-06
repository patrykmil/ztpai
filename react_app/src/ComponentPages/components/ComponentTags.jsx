import styles from "./Component.module.css";


const ComponentTags = ({tags}) => {
    return (
        <div className={styles.tagButton}>
            {tags.map(tag => (
                <span
                    className={styles.tag}
                    key={tag.name}
                    style={{backgroundColor: `#${tag.color.hex}`}}
                >
                    {tag.name}
                </span>
            ))}
        </div>
    );
};

export default ComponentTags