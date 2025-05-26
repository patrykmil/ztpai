import useAuthStore from "../../../Authentication/AuthStore.js";
import AdminPopup from "./AdminPopup.jsx";
import {useState} from "react";
import styles from "../Component.module.css";
import {api} from "../../../main.jsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";

const AdminPanel = ({component}) => {
    const userInfo = useAuthStore();
    const queryClient = useQueryClient();
    const [isPopupVisible, setIsPopupVisible] = useState(false);

    const banMutation = useMutation({
        mutationFn: (userId) => api.delete(`/admin/users/${userId}`),
        onSuccess: () => {
            queryClient.invalidateQueries(['component']);
            setIsPopupVisible(false);
        }
    });

    const deleteMutation = useMutation({
        mutationFn: (data) => api.delete(`/admin/components/${data.componentId}`, data),
        onSuccess: () => {
            queryClient.invalidateQueries(['component']);
            setIsPopupVisible(false);
        }
    });

    const handleBan = () => {
        banMutation.mutate(component.author.id);
    };

    const handleDelete = () => {
        deleteMutation.mutate({
            componentId: component.id,
            reason: document.querySelector("#reason").value
        });
    };

    if (!userInfo.admin)
        return null;

    return (
        <>
            <button className={`${styles.interactionButton} ${styles.adminButton}`}
                    onClick={() => setIsPopupVisible(true)}>
                Admin actions
            </button>
            <AdminPopup
                isVisible={isPopupVisible}
                onClose={() => setIsPopupVisible(false)}
                onBan={handleBan}
                onDelete={handleDelete}
            />
        </>
    );
};

export default AdminPanel;