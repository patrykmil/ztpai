import styles from "./Component.module.css";
import useAuthStore from "../../store/authStore.js";
import {api} from "../../main.jsx";
import {useEffect, useState} from "react";

const ButtonLike = ({component}) => {
    const [liked, setLiked] = useState(false);
    const userInfo = useAuthStore();

    const checkLike = async () => {
        try {
            const response = await api.post(`/api/components/checkLike/${component.id}`)
            if (response.status === 200) {
                setLiked(await response.data);
            }
        }
        catch (e) {
            console.error(e);
        }
    }

    const switchLike = async () => {
        try {
            const response = await api.post(`/api/components/switchLike/${component.id}`)
            if (response.status === 200) {
                setLiked(!liked);
            }
        }
        catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        checkLike(component).catch(console.error);
    }, [checkLike, component]);

    if (userInfo.userId) {
        return (
            <button className={styles.interactionButton}
                    onClick={() => switchLike(component)}
            >
                <img src={`/icons/${ liked ? 'heart_fill' : 'heart_nofill' }.svg`} alt={"Like component"}/>
            </button>
        );
    }
    return null;
};

export default ButtonLike;