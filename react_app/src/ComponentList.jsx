import {useQuery} from "@tanstack/react-query";
import {api} from './main.jsx'
import styles from "./ComponentList.module.css"

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
    if (error) return <div>Error: {error.message}</div>;
    return (
        <div>
            {data.map((component) => (
                <div className={styles.listItem} key={component.id}>
                    <span className={styles.componentName} style={{color: `#${component.hex}`}}>
                        {component.name}
                    </span>
                    <p>author: {component.username}</p>
                    <p>set: {component.setName}</p>
                    {renderTags(component.tags)}
                    <style>
                        {`.component-${component.id} ${component.css}`}
                    </style>
                    <div
                        className={`component-${component.id} ${styles.componentPreview}`}
                        dangerouslySetInnerHTML={{__html: component.html}}
                    />
                </div>
            ))}
        </div>
    );
};

export default ComponentList;