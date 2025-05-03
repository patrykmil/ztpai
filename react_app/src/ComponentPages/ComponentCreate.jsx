// react_app/src/ComponentPages/ComponentCreate.jsx
import useAuthStore from "../store/authStore.js";
import {useNavigate} from "react-router-dom";
import Navigation from "../Navigation/Navigation.jsx";
import {useEffect, useState} from "react";
import styles from "./components/Component.module.css";
import ComponentPreview from "./components/ComponentPreview.jsx";
import {useForm} from "@tanstack/react-form";
import {api} from "../main.jsx";
import {useQuery} from "@tanstack/react-query";
import InternalServer from "../ErrorPages/InternalServer.jsx";
import {fetchSupp, ValidationSchema} from "./components/create/ComponentProperties";
import FormFields from "./components/create/FormFields";
import CodeEditor from "./components/create/CodeEditor";
import CreateSetPopup from "./components/create/CreateSetPopup";

const ComponentCreate = () => {
                                const [tags, setTags] = useState([]);
                                const userInfo = useAuthStore();
                                const navigate = useNavigate();
                                const [activeTab, setActiveTab] = useState("html");
                                const [isPopupVisible, setIsPopupVisible] = useState(false);
                                const [previewData, setPreviewData] = useState({
                                    id: 999,
                                    css: "p {\n\tfont-size:24px;\n\tbackground: linear-gradient(to right, violet, green);\n\t-webkit-background-clip: text;\n\tbackground-clip: text;\n\tcolor: transparent;\n}",
                                    html: "<p>Start creating!!!</p>",
                                });

                                const { data: suppData, isLoading, error, refetch } = useQuery({
                                    queryKey: ["supp", userInfo.userId],
                                    queryFn: () => fetchSupp(userInfo.userId),
                                    enabled: !!userInfo.userId,
                                });

                                const form = useForm({
                                    defaultValues: {
                                        name: "", type: "", set: "", hex: "", tags: [], tag: "",
                                        html: previewData.html, css: previewData.css,
                                    },
                                    onSubmit: async ({value}) => {
                                        try {
                                            const submitData = {
                                                tag: undefined,
                                                name: value.name,
                                                color: { hex: value.hex },
                                                type: { name: value.type },
                                                set: { name: value.set },
                                                html: value.html,
                                                css: value.css,
                                                tags: value.tags.map(tag => ({
                                                    id: tag.id,
                                                    name: tag.name,
                                                    color: { id: tag.color.id, hex: tag.color.hex }
                                                }))
                                            };
                                            const response = await api.post("/api/components/add", submitData);
                                            if (response.status === 201) {
                                                navigate(`/components/${response.data.id}`);
                                            }
                                        } catch (error) {
                                            alert(error.response?.data || "Component added");
                                        }
                                    },
                                    validators: {
                                        onChange: ValidationSchema(suppData),
                                    },
                                });

                                useEffect(() => {
                                    if (!userInfo.token) navigate("/login");
                                }, [userInfo, navigate]);

                                if (!userInfo.token) return null;
                                if (isLoading) return <div className="loadingText">Loading...</div>;
                                if (error) return <InternalServer/>;

                                const handleAddTag = (tag) => {
                                    const currentTags = form.getFieldValue("tags") || [];
                                    if (!currentTags.some((t) => t.name === tag.name)) {
                                        const updatedTags = [...currentTags, tag];
                                        form.setFieldValue("tags", updatedTags);
                                        setTags(updatedTags);
                                    }
                                };

                                const handleTagRemove = (tag) => {
                                    const currentTags = form.getFieldValue("tags") || [];
                                    const updatedTags = currentTags.filter((t) => t.name !== tag.name);
                                    form.setFieldValue("tags", updatedTags);
                                    setTags(updatedTags);
                                };

                                const handleSetCreate = async () => {
                                    const nameInput = document.getElementById('newSetName');
                                    const newSetName = nameInput.value;
                                    if (newSetName) {
                                        const response = await api.post('api/sets/add', {setName: newSetName});
                                        if (response.status === 200) {
                                            setIsPopupVisible(false);
                                            nameInput.value = '';
                                            await refetch();
                                            form.setFieldMeta('set', { isTouched: false, errors: [] });
                                            form.setFieldValue('set', newSetName);
                                        }
                                    }
                                };

                                return (
                                    <>
                                        <Navigation/>
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
                                                        handleAddTag={handleAddTag}
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
                                        <CreateSetPopup
                                            isVisible={isPopupVisible}
                                            onClose={() => setIsPopupVisible(false)}
                                            onSubmit={handleSetCreate}
                                        />
                                    </>
                                );
                            };

                            export default ComponentCreate;