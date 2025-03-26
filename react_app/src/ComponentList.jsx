import {useQuery} from "@tanstack/react-query";
import {api} from './main.jsx'
import ComponentCard from "./ComponentListPage/ComponentCard.jsx"
import styles from "./ComponentListPage/ComponentList.module.css";
import NotFound from "./ErrorPages/NotFound.jsx";
import InternalServer from "./ErrorPages/InternalServer.jsx";


const fetchComponents = async () => {
    const {data} = await api.get("/components");
    return data;
};

const renderTags = (tagsString) => {
    const tags = JSON.parse(tagsString);
    return (
        <div style={{display: 'flex', gap: '0.5rem'}}>
            tags:
            {Object.entries(tags).map(([key, value]) => (
                <span className={styles.tag} key={key} style={{backgroundColor: `#${value}`}}>
                        {key}
                    </span>
            ))}
        </div>
    );
};

const ComponentList = () => {
    const {data, error, isLoading} = useQuery({queryKey: ["components"], queryFn: fetchComponents});

    if (isLoading) return <div>Loading...</div>;
    if (error) return <InternalServer/>;
    
    return (
        <div>
            {data.map(component => (
                <ComponentCard key={component.id} component={component}/>
            ))}
        </div>
    );
};

export default ComponentList;