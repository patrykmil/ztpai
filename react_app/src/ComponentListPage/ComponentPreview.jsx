import styles from "./ComponentList.module.css";


const ComponentPreview = ({component}) => (
    <>
        <style>
            {`.component-${component.id} ${component.css}`}
        </style>
        <div
            className={`component-${component.id} ${styles.componentPreview}`}
            dangerouslySetInnerHTML={{__html: component.html}}
        />
    </>
);

export default ComponentPreview