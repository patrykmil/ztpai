import {useState} from 'react';
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

const fetchComponents = async (filter) => {
            const { data } = await api.post("/api/components/search", filter);
            return data;
        };

        const ComponentList = () => {
            const [searchQuery, setSearchQuery] = useState('');
            const [types, setTypes] = useState({
                button: true,
                input: true,
                checkbox: true,
                'radio button': true
            });
            const [sorting, setSorting] = useState('most likes');

            const [filter, setFilter] = useState({
                query: '',
                types: types,
                sorting: 'most likes'
            });

            const { data, error, isLoading } = useQuery({
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
                                <FilterButton
                                    searchQuery={searchQuery}
                                    types={types}
                                    sorting={sorting}
                                    setFilter={setFilter}
                                />
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