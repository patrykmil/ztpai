import {useForm} from '@tanstack/react-form';
import {Link} from "react-router-dom";
import {api} from '../main.jsx';
import styles from './Security.module.css';
import Field from './components/Field.jsx';

const Login = () => {
    const form = useForm({
        defaultValues: {
            email: '',
            password: '',
        },
        onSubmit: async ({value}) => {
            try {
                console.log(value);
                const {data} = await api.post("/users/login", value);
                alert(data.username + " has signed in!")
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
        <div className={styles.content}>
            <div className={styles.formDiv}>
                <form
                    onSubmit={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        form.handleSubmit();
                    }}
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

    );
};

export default Login;