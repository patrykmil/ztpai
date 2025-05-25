import styles from "../Component.module.css";
import useAuthStore from "../../../Authentication/AuthStore.js";
import {useNavigate} from "react-router-dom";

const ButtonModify = ({component}) => {
    const navigate = useNavigate()
    const userInfo = useAuthStore();

    const destination = window.location.pathname.replace("components", "modify");

    if (userInfo.userId !== component.author.id) {
        return null;
    }

    return (
        <button className={styles.interactionButton} onClick={() => navigate(destination)}>
            <img src={"/icons/modify.svg"} alt={"Modify component"}/>
        </button>
    );
};

export default ButtonModify;