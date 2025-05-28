import styles from "./../Component.module.css";
import InputField from "./InputField.jsx";
import SelectField from "./SelectField.jsx";

const ComponentFormFields = ({form, suppData, handleTagAdd, tags, handleTagRemove, setIsPopupVisible}) => {
    return (
        <>
            <div className={styles.formFields}>
                <form.Field name="name" children={(field) =>
                    <InputField name="name" placeholder="Name" field={field}/>}
                />
                <form.Field name="type" children={(field) =>
                    <SelectField name="type" placeholder="Type" field={field} options={suppData.types}/>}
                />
                <form.Field name="set" children={(field) =>
                    <SelectField
                        name="set"
                        placeholder="Set"
                        field={field}
                        options={suppData?.sets}
                        onInput={(e) => {
                            if (e.target.value === '+Add new set') {
                                setIsPopupVisible(true);
                                e.target.value = '';
                            }
                        }}
                    />}
                />
                <form.Field name="hex" children={(field) =>
                    <InputField name="hex" placeholder="Color HEX" field={field}/>}
                />
                <form.Field name="tag" children={(field) => (
                    <SelectField
                        name="tag"
                        placeholder="Tags"
                        field={field}
                        options={suppData.tags}
                        onInput={(e) => {
                            const matchingTag = suppData.tags.find((t) => t.name === e.target.value);
                            if (matchingTag) {
                                handleTagAdd(matchingTag);
                                field.handleChange("");
                                e.target.value = "";
                            }
                        }}
                    />
                )}/>
            </div>
            <div className={styles.selectedTags}>
                {tags.map((tagData) => (
                    <button key={tagData.name}
                            className={`${styles.tag} ${styles.tagButton}`}
                            style={{backgroundColor: `#${tagData?.color.hex}`}}
                            onClick={() => handleTagRemove(tagData)}
                    >
                        {tagData.name}
                    </button>
                ))}
            </div>
        </>
    );
};

export default ComponentFormFields;