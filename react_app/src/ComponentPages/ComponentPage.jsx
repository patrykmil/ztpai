import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import InternalServer from "../ErrorPages/InternalServer.jsx";
import ComponentPreview from "./components/ComponentPreview.jsx";
import ComponentDetails from "./components/list/ComponentDetails.jsx";
import ComponentHeader from "./components/list/ComponentHeader.jsx";
import styles from "./components/Component.module.css"
import PrismCodeField from './components/single/PrismCodeField.jsx';
import {useState} from "react";
import ChangeCodeButtons from "./components/create/ChangeCodeButtons.jsx";
import Navigation from "../Navigation/Navigation.jsx";
import ButtonCopy from "./components/interaction/ButtonCopy.jsx";
import ButtonShare from "./components/interaction/ButtonShare.jsx";
import ButtonDelete from "./components/interaction/ButtonDelete.jsx";
import ButtonLike from "./components/interaction/ButtonLike.jsx";

const fetchComponent = async (id) => {
    const {data} = await api.get(`/api/components/get/${id}`);
    return data;
};

const ComponentPage = () => {
    const {id} = useParams();
    const {data, error, isLoading} = useQuery({
        queryKey: ["component", id],
        queryFn: () => fetchComponent(id),
    });

    const [activeTab, setActiveTab] = useState("html");

   if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <>
            <Navigation/>
            <div className={styles.mainPage}>
                <div className={styles.content}>
                    <div className={styles.leftSide}>
                        <div className={styles.previewContainer}>
                            <ComponentPreview component={data}/>
                        </div>
                        <div className={styles.interactionButtonsContainer}>
                            <ButtonCopy component={data}/>
                            <ButtonShare component={data}/>
                            <ButtonDelete component={data}/>
                            <ButtonLike component={data}/>
                        </div>
                        <ComponentHeader component={data}/>
                        <ComponentDetails maxTags={33} component={data}/>
                    </div>
                    <div className={styles.rightSide}>
                        <ChangeCodeButtons activeTab={activeTab} setActiveTab={setActiveTab}/>
                        <PrismCodeField content={data.html} language="html" activeTab={activeTab}/>
                        <PrismCodeField content={data.css} language="css" activeTab={activeTab}/>
                    </div>
                </div>
            </div>
        </>
    );
};

export default ComponentPage;