import {useQuery} from "@tanstack/react-query";
import axios from "axios";

const fetchComponents = async () => {
    const {data} = await axios.get("http://localhost:8080/components");
    return data;
};

const Components = () => {
    const {data, error, isLoading} = useQuery({queryKey: ["components"], queryFn: fetchComponents});

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div>
            {data.map((component) => (
                <div key={component.id}>
                    {component.name + "   "}
                    <div dangerouslySetInnerHTML={{__html: component.html}}/>
                </div>
            ))}
        </div>
    );
};

export default Components;