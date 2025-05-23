import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from "../../../../main.jsx";

export const useSetHandler = (form, setIsPopupVisible) => {
    const queryClient = useQueryClient();

    const setMutation = useMutation({
        mutationFn: (setName) => api.post('sets/add', { setName }),
        onSuccess: () => {
            setIsPopupVisible(false);
            const nameInput = document.getElementById('newSetName');
            if (nameInput) nameInput.value = '';

            queryClient.invalidateQueries({ queryKey: ['supp'] });

            form.setFieldMeta('set', { isTouched: false, errors: [] });
            form.setFieldValue('set', '');
        },
        onError: (error) => {
            alert(error.response?.data?.message || 'Failed to create set');
        }
    });

    const handleSetCreate = async () => {
        const nameInput = document.getElementById('newSetName');
        const newSetName = nameInput.value;
        if (newSetName) {
            setMutation.mutate(newSetName);
        }
    };

    return { handleSetCreate };
};