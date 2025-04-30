import styles from "./Component.module.css"

const TagSelector = ({ field, options, tags, onTagAdd }) => {
    return (
        <div className={styles.field}>
            <div className={styles.tagInput}>
                <input
                    list="tags-list"
                    id="tag"
                    value={field.state.value}
                    onChange={(e) => field.handleChange(e.target.value)}
                    className={styles.input}
                    placeholder="Tags"
                />
                <button
                    type="button"
                    onClick={() => {
                        if (field.state.value && !tags.includes(field.state.value)) {
                            onTagAdd(field.state.value);
                            field.handleChange('');
                        }
                    }}
                    className={styles.addTagButton}
                >
                    Add
                </button>
            </div>
            <datalist id="tags-list">
                {options.map((option) => (
                    <option key={option.id} value={option.name} />
                ))}
            </datalist>
            <div className={styles.selectedTags}>
                {tags.map(tag => {
                    const tagData = options.find(t => t.name === tag);
                    return (
                        <span
                            key={tag}
                            className={styles.tag}
                            style={{backgroundColor: `#${tagData?.color.hex}`}}
                        >
                            {tag}
                        </span>
                    );
                })}
            </div>
        </div>
    );
};

export default TagSelector