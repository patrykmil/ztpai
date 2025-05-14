import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import ComponentCard from "./components/list/ComponentCard.jsx"
import InternalServer from "../ErrorPages/InternalServer.jsx";
import Navigation from "../Navigation/Navigation.jsx";
import styles from "./components/Component.module.css"
import SearchBar from "./components/list/filters/SearchBar.jsx";
import Sorting from "./components/list/filters/Sorting.jsx";
import FilterButton from "./components/list/filters/FilterButton.jsx";
import Types from "./components/list/filters/Types.jsx";

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
                <SearchBar/>
                {/*<RefreshButton refetch={refetch}/>*/}
                <div className={styles.bottom}>
                    <div className={styles.filter}>
                        <Sorting/>
                        <Types/>
                        <FilterButton/>
                    </div>
                    {data.map(component => (
                        <ComponentCard key={component.id} component={component}/>
                    ))}
                </div>
            </div>
        </>
    );
};

export default ComponentList;