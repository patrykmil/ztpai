import ComponentTags from "./ComponentTags.jsx";


const ComponentDetails = ({component}) => (
    <>
        <p>author: {component.username}</p>
        <p>set: {component.setName}</p>
        <ComponentTags tags={component.tags}/>
    </>
);

export default ComponentDetails