import {create} from 'zustand'

const useAuthStore = create((set) => ({
    token: null,
    userId: null,
    username: null,
    setAuth: (data) => set({
        token: data.token,
        userId: data.id,
        username: data.name
    }),
    logout: () => set({
        token: null,
        userId: null,
        username: null
    })
}))

export default useAuthStore