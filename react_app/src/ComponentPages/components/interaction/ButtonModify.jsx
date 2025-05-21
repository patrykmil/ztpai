import styles from "../Component.module.css";
import useAuthStore from "../../../store/authStore.js";
import {useNavigate} from "react-router-dom";

const ButtonModify = ({component}) => {
    const navigate = useNavigate()
    const userInfo = useAuthStore();

    const link = window.location.href;

    if (userInfo.userId === component.author.id) {
        return (
            <button className={styles.interactionButton} onClick={() => console.log(link)}>
                <img src={"/icons/modify.svg"} alt={"Modify component"}/>
            </button>
        );
    }
    return null;
};

export default ButtonModify;