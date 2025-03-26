import styles from "./ComponentList.module.css";
import ComponentHeader from "./ComponentHeader.jsx";
import ComponentDetails from "./ComponentDetails.jsx";
import ComponentPreview from "./ComponentPreview.jsx";


const ComponentCard = ({component}) => (
    <div className={styles.listItem} key={component.id}>
        <ComponentHeader component={component}/>
        <ComponentDetails component={component}/>
        <ComponentPreview component={component}/>
    </div>
);

export default ComponentCard