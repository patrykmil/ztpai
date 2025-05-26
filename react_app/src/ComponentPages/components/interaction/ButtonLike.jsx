import styles from "../Component.module.css";
import useAuthStore from "../../../Authentication/AuthStore.js";
import {api} from "../../../main.jsx";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";

const ButtonLike = ({component}) => {
    const userInfo = useAuthStore();
    const queryClient = useQueryClient();

    const {data: liked = false} = useQuery({
        queryKey: ['like', component.id],
        queryFn: async () => {
            const response = await api.get(`/components/${component.id}/like`);
            return response.data;
        },
        enabled: !!userInfo.userId
    });

    const switchLikeMutation = useMutation({
        mutationFn: async () => {
            const response = await api.put(`/components/${component.id}/like`);
            return response.data;
        },
        onSuccess: () => {
            queryClient.setQueryData(['like', component.id], (oldData) => !oldData);

            queryClient.setQueriesData(['components'], (oldData) => {
                if (!oldData) return oldData;
                return oldData.map(comp => {
                    if (comp.id === component.id) {
                        return {
                            ...comp,
                            likes: liked ? comp.likes - 1 : comp.likes + 1
                        };
                    }
                    return comp;
                });
            });
        }
    });

    if (userInfo.userId && userInfo.userId !== component.author.id) {
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