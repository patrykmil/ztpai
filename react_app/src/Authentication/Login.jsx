import {useForm} from '@tanstack/react-form';
import {Link, useNavigate} from "react-router-dom";
import {api} from '../main.jsx';
import styles from './Security.module.css';
import Field from './components/Field.jsx';
import useAuthStore from '../store/authStore.js'

const Login = () => {
    const setAuth = useAuthStore(state => state.setAuth);
    const navigate = useNavigate();

    const form = useForm({
        defaultValues: {
            email: '',
            password: '',
        },
        onSubmit: async ({value}) => {
            try {
                console.log(value);
                const {data} = await api.post("/login", value);
                console.log(data)
                setAuth(data)
                navigate('/')
            } catch (error) {
                if (error.response?.data?.message) {
                    alert(error.response.data.message);
                } else {
                    alert('Login failed');
                }
            }
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
                            children={(field) => <Field name="password" label="Password" field={field}/>}
                        />
                        <form.Subscribe
                            selector={(state) => [state.canSubmit, state.isSubmitting]}
                            children={([canSubmit, isSubmitting]) => (
                                <button className={styles.submitButton} type="submit" disabled={!canSubmit}>
                                    {isSubmitting ? '...' : 'Submit'}
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