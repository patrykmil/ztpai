import {create} from 'zustand'
import {persist} from 'zustand/middleware'

const useAuthStore = create(
    persist(
        (set) => ({
            token: null,
            userId: null,
            username: null,
            avatar: null,
            admin: null,
            setAuth: (data) => set({
                token: data.token,
                userId: data.id,
                username: data.name,
                avatar: data.avatarPath,
                admin: data.admin
            }),
            logout: () => set({
                token: null,
                userId: null,
                username: null,
                avatar: null,
                admin: null
            })
        }),
        {
            name: 'auth-storage',
        }
    )
)

export default useAuthStore