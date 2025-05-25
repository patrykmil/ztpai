import {useEffect, useState} from "react";
import styles from "./../Component.module.css";
import ComponentPreview from "./../ComponentPreview.jsx";
import FormFields from "./FormFields";
import CodeEditor from "./CodeEditor";
import InternalServer from "../../../ErrorPages/InternalServer.jsx";
import useAuthStore from "../../../Authentication/AuthStore.js";
import {useNavigate} from "react-router-dom";

const ComponentForm = ({
                           form,
                           previewData,
                           setPreviewData,
                           suppData,
                           isLoading,
                           error,
                           tags,
                           handleTagAdd,
                           handleTagRemove,
                           setIsPopupVisible
                       }) => {
    const [activeTab, setActiveTab] = useState("html");
    const userInfo = useAuthStore();
    const navigate = useNavigate();

    useEffect(() => {
        if (!userInfo.token) navigate("/login");
    }, []);

    if (!userInfo.token) return null;
    if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <div className={styles.mainPage}>
            <form onSubmit={(e) => {
                e.preventDefault();
                e.stopPropagation();
                form.handleSubmit();
            }} className={styles.content}>
                <div className={styles.leftSide}>
                    <div className={styles.previewContainer}>
                        <ComponentPreview component={previewData}/>
                    </div>
                    <FormFields
                        form={form}
                        suppData={suppData}
                        handleTagAdd={handleTagAdd}
                        tags={tags}
                        handleTagRemove={handleTagRemove}
                        setIsPopupVisible={setIsPopupVisible}
                    />
                </div>
                <CodeEditor
                    form={form}
                    activeTab={activeTab}
                    setActiveTab={setActiveTab}
                    previewData={previewData}
                    setPreviewData={setPreviewData}
                />
            </form>
        </div>
    );
};

export default ComponentForm;