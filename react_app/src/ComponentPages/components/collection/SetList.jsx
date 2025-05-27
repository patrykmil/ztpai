import ComponentCard from "../list/ComponentCard.jsx";
import styles from "../Component.module.css"

const SetList = ({ setComponents }) => {
    const hasComponents = setComponents &&
        Object.values(setComponents).some(components => components && components.length > 0);

    return (
        <div className={styles.setList}>
            {hasComponents ? (
                Object.entries(setComponents || {})
                    .filter(([_, components]) => components && components.length > 0)
                    .map(([setName, components]) => (
                        <div key={setName} className={styles.setContainer}>
                            <p>{setName}</p>
                            <div className={styles.setContainerList}>
                                {components.map(component => (
                                    <ComponentCard key={component.id} component={component}/>
                                ))}
                            </div>
                        </div>
                    ))
            ) : (
                <div className={styles.emptyState}>
                    <p>No sets found</p>
                </div>
            )}
        </div>
    );
}

export default SetList;