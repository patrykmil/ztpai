import styles from "../Component.module.css";
import useAuthStore from "../../../store/authStore.js";
import {api} from "../../../main.jsx";
import {useNavigate} from "react-router-dom";
import {useMutation} from "@tanstack/react-query";

const ButtonDelete = ({component}) => {
    const navigate = useNavigate()
    const userInfo = useAuthStore();

    const deleteComponent = useMutation({
        mutationFn: async () => {
            const response = await api.delete(`/api/components/delete/${component.id}`);
            return response.data;
        },
        onSuccess: () => {
            console.log('Deleted');
            navigate('/');
        }
    });
    if (userInfo.userId === component.author.id) {
        return (
            <button className={styles.interactionButton} onClick={() => deleteComponent.mutate()} disabled={deleteComponent.isPending}>
                <img src={"/icons/delete.svg"} alt={"Delete component"}/>
            </button>
        );
    }
    return null;
};

export default ButtonDelete;