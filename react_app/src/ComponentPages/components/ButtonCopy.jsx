import styles from "./Component.module.css";

const ButtonCopy = ({component}) => {
    const copy = async () => {
        const string = `//${component.name} by ${component.author.name}\n//iu.iu/components/${component.id}\n\n//----HTML----\n${component.html}\n\n//----CSS----\n${component.css}\n`;
        try {
            await navigator.clipboard.writeText(string);
        }catch (e) {
            console.error('Failed to copy: ', e);
        }
    };

    return (
        <button className={styles.interactionButton} onClick={copy}>
            <img src={"/icons/copy.svg"} alt={"Copy"}/>
        </button>
    );
};

export default ButtonCopy;