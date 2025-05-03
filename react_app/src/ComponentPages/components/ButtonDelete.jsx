import styles from "./Component.module.css";
import useAuthStore from "../../store/authStore.js";
import {api} from "../../main.jsx";
import {useNavigate} from "react-router-dom";

const ButtonDelete = ({component}) => {
    const navigate = useNavigate()
    const userInfo = useAuthStore();

    const deleteComponent = async () => {
        const response = await api.delete(`/api/components/delete/${component.id}`)
        if (response.status === 200) {
            console.log('Deleted');
            navigate('/');
        }
    }

    if (userInfo.userId === component.author.id) {
        return (
            <button className={styles.interactionButton} onClick={() => deleteComponent(component)}>
                <img src={"/icons/delete.svg"} alt={"Delete component"}/>
            </button>
        );
    }
    return null;
};

export default ButtonDelete;