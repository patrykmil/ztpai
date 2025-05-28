import useAuthStore from "../Authentication/AuthStore.js";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {useForm} from "@tanstack/react-form";
import {api} from "../main.jsx";
import {useQuery} from "@tanstack/react-query";
import {fetchSupp, ValidationSchema} from "./components/create/ComponentProperties";
import ComponentForm from "./components/create/ComponentForm.jsx";
import {useTagHandlers} from './components/create/hooks/useTagHandlers';
import {useSetHandler} from './components/create/hooks/useSetHandler';
import CreateSetPopup from "./components/create/CreateSetPopup.jsx";
import Navigation from "../Navigation/Navigation.jsx";

const ComponentCreate = () => {
    const [tags, setTags] = useState([]);
    const userInfo = useAuthStore();
    const navigate = useNavigate();
    const [isPopupVisible, setIsPopupVisible] = useState(false);

    const [previewData, setPreviewData] = useState({
        id: 999,
        css: "p {\n\tfont-size:24px;\n\tbackground: linear-gradient(to right, violet, green);\n\t-webkit-background-clip: text;\n\tbackground-clip: text;\n\tcolor: transparent;\n}",
        html: "<p>Start creating!!!</p>",
    });

    const {data: suppData, isLoading, error} = useQuery({
        queryKey: ["supp", userInfo.userId],
        queryFn: () => fetchSupp(userInfo.userId),
        refetchOnMount: false,
        refetchOnWindowFocus: false,
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
                const response = await api.post("/components", submitData);
                if (response.status === 201) {
                    navigate(`/components/${response.data.id}`);
                }
            } catch (error) {
                alert(error.response?.data.message || "Failed to add component");
            }
        },
        validators: {
            onChange: ValidationSchema(suppData),
        },
    });

    const {handleTagAdd, handleTagRemove} = useTagHandlers(form, setTags);
    const {handleSetCreate} = useSetHandler(form, setIsPopupVisible);

    return (
        <>
            <Navigation/>
            <ComponentForm
                form={form}
                previewData={previewData}
                setPreviewData={setPreviewData}
                suppData={suppData}
                isLoading={isLoading}
                error={error}
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

export default ComponentCreate;