import ComponentCard from "../list/ComponentCard.jsx";
import styles from "../Component.module.css"

const LikedList = ({liked}) => {
    return (
        <>
            {liked && liked.length > 0 ? (
                liked.map(component => (
                    <ComponentCard key={component.id} component={component}/>
                ))
            ) : (
                <div className={styles.emptyState}>
                    <p>No liked components found</p>
                </div>
            )}
        </>
    );
}

export default LikedList;