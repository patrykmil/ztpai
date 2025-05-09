import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import ComponentCard from "./components/list/ComponentCard.jsx"
import RefreshButton from "./components/list/RefreshButton.jsx"
import InternalServer from "../ErrorPages/InternalServer.jsx";
import Navigation from "../Navigation/Navigation.jsx";
import styles from "./components/Component.module.css"

const fetchComponents = async () => {
    const {data} = await api.get("/api/components/get/all");
    return data;
};

const ComponentList = () => {
    const {data, error, isLoading, refetch} = useQuery({
        queryKey: ["components"], queryFn: fetchComponents,
        refetchOnWindowFocus: false,
        refetchOnMount: false
    });

    if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <>
            <Navigation/>
            <div className={styles.mainPage}>
                <RefreshButton refetch={refetch}/>
                {data.map(component => (
                    <ComponentCard key={component.id} component={component}/>
                ))}
            </div>
        </>
    );
};

export default ComponentList;