import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import ComponentCard from "./components/list/ComponentCard.jsx"
import InternalServer from "../ErrorPages/InternalServer.jsx";
import Navigation from "../Navigation/Navigation.jsx";
import styles from "./components/Component.module.css"
import SearchBar from "./components/list/filters/SearchBar.jsx";
import Sorting from "./components/list/filters/Sorting.jsx";
import Types from "./components/list/filters/Types.jsx";
import FilterButton from "./components/list/filters/FilterButton.jsx";
import useComponentFilterStore from "./components/list/filters/FilterStore.js";

const fetchComponents = async (filter) => {
    const {data} = await api.post("/components/search", filter);
    return data;
};

const ComponentList = () => {
    const {
        searchQuery, setSearchQuery,
        types, setTypes,
        sorting, setSorting,
        filter, setFilter
    } = useComponentFilterStore();

    const {data, error, isLoading} = useQuery({
        queryKey: ['components', filter],
        queryFn: () => fetchComponents(filter),
        refetchOnWindowFocus: false,
        refetchOnMount: false,
        enabled: true
    });

    if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <>
            <Navigation/>
            <div className={styles.mainPage}>
                <SearchBar
                    searchQuery={searchQuery}
                    setSearchQuery={setSearchQuery}
                />
                <div className={styles.bottom}>
                    <div className={styles.filter}>
                        <Sorting
                            sorting={sorting}
                            setSorting={setSorting}
                        />
                        <Types
                            types={types}
                            setTypes={setTypes}
                        />
                        <FilterButton/>
                    </div>
                    {data && data.length > 0 ? (
                        data.map(component => (
                            <ComponentCard key={component.id} component={component}/>
                        ))
                    ) : (
                        <div className={styles.emptyState}>
                            <p>No components found</p>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
};

export default ComponentList;