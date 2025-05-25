import {useForm} from '@tanstack/react-form';
import {Link, useNavigate} from "react-router-dom";
import {api} from '../main.jsx';
import styles from './Security.module.css';
import Field from './components/Field.jsx';
import useAuthStore from './AuthStore.js'
import {useMutation} from '@tanstack/react-query';

const Login = () => {
    const setAuth = useAuthStore(state => state.setAuth);
    const navigate = useNavigate();

    const loginMutation = useMutation({
        mutationFn: (data) => api.post("/login", data, {withCredentials: true}),
        onSuccess: (response) => {
            setAuth(response.data);
            navigate("/");
        },
        onError: (error) => {
            alert(error.response?.data?.message || 'Login failed');
        }
    });

    const form = useForm({
        defaultValues: {
            email: '',
            password: '',
        },
        onSubmit: async ({value}) => {
            loginMutation.mutate(value);
        },
    });

    return (
        <>
            <div className={styles.content}>
                <div className={styles.formDiv}>
                    <form
                        onSubmit={(e) => {
                            e.preventDefault();
                            e.stopPropagation();
                            form.handleSubmit();
                        }}
                        className={styles.form}
                    >
                        <form.Field
                            name="email"
                            children={(field) => <Field name="email" label="Email" field={field}/>}
                        />
                        <form.Field
                            name="password"
                            children={(field) => <Field name="password" label="Password" field={field} type="password"/>}
                        />
                        <form.Subscribe
                            selector={(state) => [state.canSubmit]}
                            children={([canSubmit]) => (
                                <button
                                    className={styles.submitButton}
                                    type="submit"
                                    disabled={!canSubmit || loginMutation.isPending}
                                >
                                    {loginMutation.isPending ? '...' : 'Log in'}
                                </button>
                            )}
                        />
                    </form>
                    <Link className={styles.link} to={"/register"}>Register?</Link>
                </div>
            </div>
        </>
    );
};

export default Login;