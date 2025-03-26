import {useQuery} from "@tanstack/react-query";
import {api} from './main.jsx'
import ComponentCard from "./ComponentListPage/ComponentCard.jsx"
import InternalServer from "./ErrorPages/InternalServer.jsx";


const fetchComponents = async () => {
    const {data} = await api.get("/components");
    return data;
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