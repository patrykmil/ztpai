import styles from "../Component.module.css";
import useAuthStore from "../../../Authentication/AuthStore.js";
import {api} from "../../../main.jsx";
import {useNavigate} from "react-router-dom";
import {useMutation} from "@tanstack/react-query";

const ButtonDelete = ({component}) => {
    const navigate = useNavigate()
    const userInfo = useAuthStore();

    const deleteComponent = useMutation({
        mutationFn: async () => {
            const response = await api.delete(`/components/delete/${component.id}`);
            return response.data;
        },
        onSuccess: () => {
            navigate('/');
        }
    });
    if (userInfo.userId !== component.author.id) {
        return null;
    }
        return (
            <button className={styles.interactionButton} onClick={() => deleteComponent.mutate()} disabled={deleteComponent.isPending}>
                <img src={"/icons/delete.svg"} alt={"Delete component"}/>
            </button>
        );
};

export default ButtonDelete;