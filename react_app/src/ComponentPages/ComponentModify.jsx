import useAuthStore from "../Authentication/AuthStore.js";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useForm} from "@tanstack/react-form";
import {api} from "../main.jsx";
import {useQuery} from "@tanstack/react-query";
import {fetchSupp, ValidationSchema} from "./components/create/ComponentProperties";
import ComponentForm from "./components/create/ComponentForm.jsx";
import {useTagHandlers} from './components/create/hooks/useTagHandlers';
import {useSetHandler} from './components/create/hooks/useSetHandler';
import CreateSetPopup from "./components/create/CreateSetPopup.jsx";
import Navigation from "../Navigation/Navigation.jsx";

const ComponentModify = () => {
    const {id} = useParams();
    const [tags, setTags] = useState([]);
    const userInfo = useAuthStore();
    const navigate = useNavigate();
    const [isPopupVisible, setIsPopupVisible] = useState(false);

    const {data: componentData, isLoading: isComponentLoading, error: componentError} = useQuery({
        queryKey: ["component", id],
        queryFn: async () => {
            const {data} = await api.get(`/components/${id}`);
            return data;
        },
    });

    const [previewData, setPreviewData] = useState({
        id: id,
        css: componentData?.css || "",
        html: componentData?.html || "",
    });

    const {data: suppData, isLoading: isSuppLoading, error: suppError} = useQuery({
        queryKey: ["supp", userInfo.userId],
        queryFn: () => fetchSupp(userInfo.userId),
        enabled: !!userInfo.userId,
    });

    useEffect(() => {
        if (componentData) {
            setPreviewData({
                id: componentData.id,
                css: componentData.css,
                html: componentData.html,
            });
            setTags(componentData.tags);
        }
    }, [componentData]);

    const form = useForm({
        defaultValues: {
            name: componentData?.name || "",
            type: componentData?.type?.name || "",
            set: componentData?.set?.name || "",
            hex: '#' + componentData?.color?.hex || "",
            tags: componentData?.tags || [],
            tag: "",
            html: componentData?.html || "",
            css: componentData?.css || "",
        },
        onSubmit: async ({value}) => {
            try {
                const submitData = {
                    id: componentData.id,
                    tag: undefined,
                    name: value.name,
                    color: {hex: value.hex},
                    type: {name: value.type},
                    set: {name: value.set},
                    html: value.html,
                    css: value.css,
                    tags: value.tags.map(tag => ({
                        id: tag.id,
                        name: tag.name,
                        color: {id: tag.color.id, hex: tag.color.hex}
                    }))
                };
                const response = await api.put(`/components`, submitData);
                if (response.status === 200) {
                    navigate(`/components/${id}`);
                }
            } catch (error) {
                alert(error.response?.data.message || "Failed to update component");
            }
        },
        validators: {
            onChange: ValidationSchema(suppData),
        },
    });

    const { handleTagAdd, handleTagRemove } = useTagHandlers(form, setTags);
    const { handleSetCreate } = useSetHandler(form, setIsPopupVisible);

    return (
        <>
            <Navigation/>
            <ComponentForm
                form={form}
                previewData={previewData}
                setPreviewData={setPreviewData}
                suppData={suppData}
                isLoading={isComponentLoading || isSuppLoading}
                error={componentError || suppError}
                tags={tags}
                handleTagAdd={handleTagAdd}
                handleTagRemove={handleTagRemove}
                setIsPopupVisible={setIsPopupVisible}
            />
            <CreateSetPopup
                isVisible={isPopupVisible}
                onClose={() => setIsPopupVisible(false)}
                onSubmit={handleSetCreate}
            />
        </>
    );
};

export default ComponentModify;