import useAuthStore from "../store/authStore.js";
import {useNavigate} from "react-router-dom";
import Navigation from "../Navigation/Navigation.jsx";
import {useEffect, useState} from "react";
import styles from "./components/Component.module.css";
import ComponentPreview from "./components/ComponentPreview.jsx";
import {useForm} from "@tanstack/react-form";
import {api} from "../main.jsx";
import {z} from "zod";
import SelectField from "./components/SelectField.jsx";
import {useQuery} from "@tanstack/react-query";
import InternalServer from "../ErrorPages/InternalServer.jsx";
import InputField from "./components/InputField.jsx";
import ChangeCodeButtons from "./components/ChangeCodeButtons.jsx";
import InputCodeField from "./components/InputCodeField.jsx";

const fetchSupp = async (userId) => {
    const [typesResponse, tagsResponse, setsResponse] = await Promise.all(
        [
                api.get("/api/types/get/all"),
                api.get("/api/tags/get/all"),
                api.get(`/api/sets/get/${userId}`)
        ]);
    return {
        types: typesResponse.data,
        tags: tagsResponse.data,
        sets: [...setsResponse.data, {name: '+Add new set', id: -1}],
    };
};

const ComponentCreate = () => {
    const [tags, setTags] = useState([]);
    const userInfo = useAuthStore();
    const navigate = useNavigate();
    const [activeTab, setActiveTab] = useState("html");
    const [previewData, setPreviewData] = useState(
        {
        id: 999,
        css: "p {\n\tfont-size:24px;\n\t" +
            "background: linear-gradient(to right, violet, green);\n\t" +
            "-webkit-background-clip: text;\n\t" +
            "background-clip: text;\n\t" +
            "color: transparent;\n}",
        html: "<p>Start creating!!!</p>",
    })
    const [isPopupVisible, setIsPopupVisible] = useState(false);

    const {
        data: suppData,
        isLoading,
        error,
        refetch
    } = useQuery({
        queryKey: ["supp", userInfo.userId],
        queryFn: () => fetchSupp(userInfo.userId),
        enabled: !!userInfo.userId,
    });


    const ValidationSchema = z.object({
        name: z.string().min(3, "Name must be longer than 2"),
        type: z.string().refine((val) => suppData?.types.some((t) => t.name === val), "Select valid type"),
        set: z.string().refine((val) => suppData?.sets.some((s) => s.name === val), "Select valid set"),
        hex: z.string().startsWith("#", "Start with #").length(7, "Length must be 7"),
    });

    const form = useForm({
        defaultValues: {
            name: "",
            type: "",
            set: "",
            hex: "",
            tags: [],
            tag: "",
            html: previewData.html,
            css: previewData.css,
        },
        onSubmit: async ({value}) => {
            try {
                const submitData = {
                    tag: undefined,
                    name: value.name,
                    color: {
                        hex: value.hex
                    },
                    type: {
                        name: value.type
                    },
                    set: {
                        name: value.set
                    },
                    html: value.html,
                    css: value.css,
                    tags: value.tags.map(tag => ({
                        id: tag.id,
                        name: tag.name,
                        color: {
                            id: tag.color.id,
                            hex: tag.color.hex
                        }
                    }))
                };
                const response = await api.post("/api/components/add", submitData);
                if (response.status === 201) {
                    navigate(`/components/${response.data.id}`)
                }
            } catch (error) {
                if (error.response?.data) {
                    alert(error.response.data);
                } else {
                    alert("Component added");
                }
            }
        },
        validators: {
            onChange: ValidationSchema,
        },
    });

    useEffect(() => {
        if (!userInfo.token) {
            navigate("/login");
        }
    }, [userInfo, navigate]);

    if (!userInfo.token) {
        return null;
    }

    if (isLoading) return <div>Loading...</div>;
    if (error) return <InternalServer/>;

    const handleAddTag = (tag) => {
        const currentTags = form.getFieldValue("tags") || [];
        if (!currentTags.some((t) => t.name === tag.name)) {
            form.setFieldValue("tags", [...currentTags, tag]);
        }
        setTags(form.getFieldValue("tags"));
        console.log(tags);
    };

    const handleTagRemove = (tag) => {
        const currentTags = form.getFieldValue("tags") || [];
        const updatedTags = currentTags.filter((t) => t.name !== tag.name);
        form.setFieldValue("tags", updatedTags);
        setTags(updatedTags);
        console.log(tags);
    }

    return (
        <>
            <Navigation/>
            <div className={styles.mainPage}>
                <form
                    onSubmit={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        form.handleSubmit();
                    }}
                    className={styles.content}
                >
                    <div className={styles.leftSide}>
                        <div className={styles.previewContainer}>
                            <ComponentPreview component={previewData}/>
                        </div>
                        <div className={styles.formFields}>
                            <form.Field name="name" children={(field) => <InputField name="name" placeholder="Name"
                                                                                     field={field}/>}/>
                            <form.Field name="type"
                                        children={(field) =>
                                            <SelectField name="type" placeholder="Type" field={field}
                                                         options={suppData.types}/>}/>
                            <form.Field name="set"
                                        children={(field) =>
                                            <SelectField
                                                name="set"
                                                placeholder="Set"
                                                field={field}
                                                options={suppData?.sets}
                                                onInput={(e) => {
                                                    const value = e.target.value;
                                                    const addNew = '+Add new set';
                                                    if (value === addNew) {
                                                        console.log('Add new');
                                                        setIsPopupVisible(true)
                                                        e.target.value = '';
                                                    }
                                                }}

                                            />}/>
                            <form.Field name="hex" children={(field) => <InputField name="hex" placeholder="Color HEX"
                                                                                    field={field}/>}/>
                            <form.Field
                                name="tag"
                                children={(field) => (
                                    <SelectField
                                        name="tag"
                                        placeholder="Tags"
                                        field={field}
                                        options={suppData.tags}
                                        onInput={(e) => {
                                            const value = e.target.value;
                                            const matchingTag = suppData.tags.find((t) => t.name === value);
                                            if (matchingTag) {
                                                handleAddTag(matchingTag);
                                                field.handleChange("");
                                                e.target.value = "";
                                            }
                                        }}
                                    />
                                )}
                            />
                        </div>
                        <div className={styles.selectedTags}>
                            {tags.map((tagData) => (
                                <button key={tagData.name}
                                        className={`${styles.tag} ${styles.tagButton}`}
                                        style={{backgroundColor: `#${tagData?.color.hex}`}}
                                        onClick={() => handleTagRemove(tagData)}
                                >
                                    {tagData.name}
                                </button>
                            ))}
                        </div>
                    </div>
                    <div className={styles.rightSide}>
                        <ChangeCodeButtons activeTab={activeTab} setActiveTab={setActiveTab}/>
                        <form.Field
                            name="html"
                            children={() => (
                                <InputCodeField
                                    start={previewData.html}
                                    language="html"
                                    activeTab={activeTab}
                                    onInput={(e) => {
                                        const value = e.target.value;
                                        form.setFieldValue('html', value);
                                        setPreviewData({
                                            ...previewData,
                                            html: value
                                        });
                                    }}
                                />
                            )}
                        />
                        <form.Field
                            name="css"
                            children={() => (
                                <InputCodeField
                                    start={previewData.css}
                                    language="css"
                                    activeTab={activeTab}
                                    onInput={(e) => {
                                        const value = e.target.value;
                                        form.setFieldValue('css', value);
                                        setPreviewData({
                                            ...previewData,
                                            css: value
                                        });
                                    }}
                                />
                            )}
                        />
                        <form.Subscribe
                            selector={(state) => [state.canSubmit, state.isSubmitting]}
                            children={([canSubmit, isSubmitting]) => (
                        <button className={styles.submitButton} type="submit" disabled={!canSubmit}>
                            {isSubmitting ? '...' : 'Submit'}
                        </button>
                            )}
                        />
                    </div>
                </form>
            </div>
            <div className={`${styles.popup} ${isPopupVisible ? styles.active : ''}`}>
                <div className={styles.popupContent}>
                    <span className={styles.close}
                          onClick={()=> setIsPopupVisible(false)}
                    >&times;
                    </span>
                    <h2>Create New Set</h2>
                    <input type="text" id="newSetName" placeholder="Enter new set name"/>
                    <button
                        onClick={async () => {
                            const newSetName = document.getElementById('newSetName').value;
                            if (newSetName) {
                                const nameInput = document.getElementById('newSetName');
                                const value = nameInput.value;
                                const response = await api.post('api/sets/add', {setName: value})
                                if (response.status === 200) {
                                    setIsPopupVisible(false);
                                    nameInput.value='';
                                    await refetch();
                                    form.setFieldMeta('set', {
                                        isTouched: false,
                                        errors: []
                                    });
                                    form.setFieldValue('set', newSetName);
                                }
                            }
                        }}
                        >Create
                    </button>
                </div>
            </div>
        </>
    );
};

export default ComponentCreate;
