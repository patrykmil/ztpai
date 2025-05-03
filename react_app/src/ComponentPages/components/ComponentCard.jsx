import styles from "./Component.module.css";
import ComponentHeader from "./ComponentHeader.jsx";
import ComponentDetails from "./ComponentDetails.jsx";
import ComponentPreview from "./ComponentPreview.jsx";
import {Link} from "react-router-dom";
import ButtonCopy from "./ButtonCopy.jsx";
import ButtonLike from "./ButtonLike.jsx";
import ButtonShare from "./ButtonShare.jsx";


const ComponentCard = ({component}) => (
        <div className={styles.listItem} key={component.id}>
            <Link to={`/components/${component.id}`} className={styles.listItemInside}>
                <ComponentPreview component={component}/>
                <ComponentHeader component={component}/>
                <ComponentDetails component={component}/>
            </Link>
            <div className={styles.interactionButtonsContainerList}>
                <ButtonCopy component={component}/>
                <ButtonShare component={component}/>
                <ButtonLike component={component}/>
            </div>
        </div>
);

export default ComponentCard