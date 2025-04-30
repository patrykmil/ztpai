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

const fetchSupp = async (userId) => {
    const [typesResponse, tagsResponse, setsResponse] = await Promise.all([api.get("/api/types/get/all"), api.get("/api/tags/get/all"), api.get(`/api/sets/get/${userId}`)]);
    return {
        types: typesResponse.data,
        tags: tagsResponse.data,
        sets: setsResponse.data,
    };
};

const ComponentCreate = () => {
    const [tags, setTags] = useState([]);
    const userInfo = useAuthStore();
    const navigate = useNavigate();

    useEffect(() => {
        if (!userInfo.token) {
            navigate("/login");
        }
    }, [userInfo, navigate]);

    const {
        data: suppData,
        isLoading,
        error,
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
            html: "",
            css: "",
        },
        onSubmit: async ({value}) => {
            try {
                const submitData = {
                    ...value,
                    tag: undefined,
                };
                console.log(value);
                await api.post("/api/components/add", submitData);
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

    if (isLoading) return <div>Loading...</div>;
    if (error) return <InternalServer/>;

    const previewData = {
        id: 999,
        css: "button {" + "background-color: yellow;" + "padding: 2rem;" + "color: black;" + "}",
        html: "<button>aha</button>",
    };

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
                <div className={styles.content}>
                    <div className={styles.leftSide}>
                        <div className={styles.previewContainer}>
                            <ComponentPreview component={previewData}/>
                        </div>
                        <div className={styles.formFields}>
                            <form.Field name="name" children={(field) => <InputField name="name" placeholder="Name"
                                                                                     field={field}/>}/>
                            <form.Field name="type"
                                        children={(field) => <SelectField name="type" placeholder="Type" field={field}
                                                                          options={suppData.types}/>}/>
                            <form.Field name="set"
                                        children={(field) => <SelectField name="set" placeholder="Set" field={field}
                                                                          options={suppData.sets}/>}/>
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
                </div>
            </div>
        </>
    );
};

export default ComponentCreate;
