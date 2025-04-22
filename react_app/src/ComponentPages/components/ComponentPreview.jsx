import styles from "./Component.module.css";


const ComponentPreview = ({component}) => (
    <>
        <style>
            {`.component-${component.id} { ${component.css} }`}
        </style>
        <div
            className={`component-${component.id} ${styles.componentPreview}`}
            dangerouslySetInnerHTML={{__html: component.html}}
            onClick={(e) => {
                e.preventDefault();
                e.stopPropagation();
            }}
        />
    </>
);

export default ComponentPreview