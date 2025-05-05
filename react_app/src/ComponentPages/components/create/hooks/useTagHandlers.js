export const useTagHandlers = (form, setTags) => {
    const handleTagAdd = (tag) => {
        const currentTags = form.getFieldValue("tags") || [];
        if (!currentTags.some((t) => t.name === tag.name)) {
            const updatedTags = [...currentTags, tag];
            form.setFieldValue("tags", updatedTags);
            setTags(updatedTags);
        }
    };

    const handleTagRemove = (tag) => {
        const currentTags = form.getFieldValue("tags") || [];
        const updatedTags = currentTags.filter((t) => t.name !== tag.name);
        form.setFieldValue("tags", updatedTags);
        setTags(updatedTags);
    };

    return { handleTagAdd, handleTagRemove };
};