import styles from "./Component.module.css";
import useAuthStore from "../../store/authStore.js";
import {api} from "../../main.jsx";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";

const ButtonLike = ({component}) => {
    const userInfo = useAuthStore();
    const queryClient = useQueryClient();

    const {data: liked = false} = useQuery({
        queryKey: ['like', component.id],
        queryFn: async () => {
            const response = await api.post(`/api/components/checkLike/${component.id}`);
            return response.data;
        },
        enabled: !!userInfo.userId
    });

    const switchLikeMutation = useMutation({
        mutationFn: async () => {
            const response = await api.post(`/api/components/switchLike/${component.id}`);
            return response.data;
        },
        onSuccess: () => {
            queryClient.invalidateQueries(['like', component.id]);
        }
    });

    if (userInfo.userId) {
        return (
            <button
                className={styles.interactionButton}
                onClick={() => switchLikeMutation.mutate()}
                disabled={switchLikeMutation.isPending}
            >
                <img
                    src={`/icons/${liked ? 'heart_fill' : 'heart_nofill'}.svg`}
                    alt="Like component"
                />
            </button>
        );
    }
    return null;
};

export default ButtonLike;