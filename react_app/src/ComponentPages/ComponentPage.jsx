import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import InternalServer from "../ErrorPages/InternalServer.jsx";
import ComponentPreview from "./components/ComponentPreview.jsx";
import ComponentDetails from "./components/ComponentDetails.jsx";
import ComponentHeader from "./components/ComponentHeader.jsx";
import styles from "./components/Component.module.css"
import CodeField from './components/CodeField.jsx';
import {useState} from "react";
import ChangeCodeButtons from "./components/ChangeCodeButtons.jsx";
import Navigation from "../Navigation/Navigation.jsx";
import {Helmet} from "react-helmet";

const fetchComponent = async (id) => {
    const {data} = await api.get(`/components/get/${id}`);
    return data;
};

const ComponentPage = () => {
    const {id} = useParams();
    const {data, error, isLoading} = useQuery({
        queryKey: ["component", id],
        queryFn: () => fetchComponent(id),
    });

    const [activeTab, setActiveTab] = useState("html");

    if (isLoading) return <div>Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <>
            <Helmet>
                <title>Component</title>
            </Helmet>
            <Navigation/>
            <div className={styles.mainPage}>
                <div className={styles.content}>
                    <div className={styles.leftSide}>
                        <div className={styles.previewContainer}>
                            <ComponentPreview component={data}/>
                        </div>
                        <ComponentHeader component={data}/>
                        <ComponentDetails component={data}/>
                    </div>
                    <div className={styles.rightSide}>
                        <ChangeCodeButtons activeTab={activeTab} setActiveTab={setActiveTab}/>
                        <CodeField content={data.html} language="html" activeTab={activeTab}/>
                        <CodeField content={data.css} language="css" activeTab={activeTab}/>
                    </div>
                </div>
            </div>
        </>
    );
};

export default ComponentPage;