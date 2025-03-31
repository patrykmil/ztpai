import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import ComponentCard from "./components/ComponentCard.jsx"
import RefreshButton from "./components/RefreshButton.jsx"
import InternalServer from "../ErrorPages/InternalServer.jsx";


const fetchComponents = async () => {
    const {data} = await api.get("/components/get/all");
    return data;
};

const ComponentList = () => {
    const {data, error, isLoading, refetch} = useQuery({
        queryKey: ["components"], queryFn: fetchComponents,
        refetchOnWindowFocus: false,
        refetchOnMount: false
    });

    if (isLoading) return <div>Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <div>
            <RefreshButton refetch={refetch}/>
            {data.map(component => (
                <ComponentCard key={component.id} component={component}/>
            ))}
        </div>
    );
};

export default ComponentList;