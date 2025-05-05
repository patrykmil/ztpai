import styles from "../Component.module.css";

const ComponentTags = ({max, tags}) => {
    return (
        <div className={styles.tagBackground}>
            {tags.slice(0, max).map(tag => {
                return (
                    <span
                        className={styles.tag}
                        key={tag.name}
                        style={{backgroundColor: `#${tag.color.hex}`}}
                    >
                        {tag.name}
                    </span>
                );
            })}
        </div>
    );
};

export default ComponentTags;