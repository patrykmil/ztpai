import ComponentCard from "../list/ComponentCard.jsx";

const LikedList = ({ liked }) => {
    return (
        <>
            {liked?.map(component => (
                <ComponentCard key={component.id} component={component}/>
            ))}
        </>
    );
}

export default LikedList;