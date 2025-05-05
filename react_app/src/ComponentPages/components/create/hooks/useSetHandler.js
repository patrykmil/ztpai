import {api} from "../../../../main.jsx";

export const useSetHandler = (form, setIsPopupVisible, refetch) => {
    const handleSetCreate = async () => {
        const nameInput = document.getElementById('newSetName');
        const newSetName = nameInput.value;
        if (newSetName) {
            const response = await api.post('api/sets/add', {setName: newSetName});
            if (response.status === 200) {
                setIsPopupVisible(false);
                nameInput.value = '';
                await refetch();
                form.setFieldMeta('set', {isTouched: false, errors: []});
                form.setFieldValue('set', '');
            }
        }
    };

    return { handleSetCreate };
};