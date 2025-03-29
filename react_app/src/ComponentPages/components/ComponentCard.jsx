import styles from "./Component.module.css";
import ComponentHeader from "./ComponentHeader.jsx";
import ComponentDetails from "./ComponentDetails.jsx";
import ComponentPreview from "./ComponentPreview.jsx";
import {Link} from "react-router-dom";


const ComponentCard = ({component}) => (
    <div className={styles.listItem} key={component.id}>
        <Link to={`/components/${component.id}`} className={styles.listItemInside}>
            <ComponentHeader component={component}/>
            <ComponentDetails component={component}/>
            <ComponentPreview component={component}/>
        </Link>
    </div>
);

export default ComponentCard