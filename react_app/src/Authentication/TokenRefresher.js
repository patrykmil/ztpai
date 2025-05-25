import {useEffect, useRef} from 'react';
import {api} from '../main.jsx';
import useAuthStore from './AuthStore.js';

const TokenRefresher = () => {
    const token = useAuthStore(state => state.token);
    const setToken = useAuthStore(state => state.setToken);
    const timerRef = useRef(null);

    useEffect(() => {
        if (timerRef.current) {
            clearTimeout(timerRef.current);
        }

        if (!token) return;

        const refreshAfter = 1000 * 60 * 10;

        timerRef.current = setTimeout(async () => {
            try {
                const response = await api.post('/refresh');
                setToken(response.data.token);
            } catch (error) {
                console.error('Token refresh failed:', error);
            }
        }, refreshAfter);

        return () => {
            if (timerRef.current) {
                clearTimeout(timerRef.current);
            }
        };
    }, [token, setToken]);

    return null;
};

export default TokenRefresher;