import styles from "./Component.module.css";

const ButtonShare = ({component}) => {
    const copyLink = async () => {
        const string = `iu.iu/components/${component.id}`;
        try {
            await navigator.clipboard.writeText(string);
        }catch (e) {
            console.error('Failed to copy: ', e);
        }
    };

    return (
        <button className={styles.interactionButton} onClick={copyLink}>
            <img src={"/icons/share.svg"} alt={"Copy link"}/>
        </button>
    );
};

export default ButtonShare;