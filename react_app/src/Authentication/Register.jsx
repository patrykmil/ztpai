import {useForm} from '@tanstack/react-form';
import {Link, useNavigate} from "react-router-dom";
import {api} from '../main.jsx';
import {z} from 'zod';
import styles from './Security.module.css';
import Field from './components/Field.jsx';

const ValidationSchema = z.object({
    email: z.string().email("Email is not correct"),
    username: z.string().min(3, "Username must be longer than 3"),
    password: z.string().min(8, "Password must be longer than 8")
});

const Register = () => {
    const navigate = useNavigate();
    const form = useForm({
        defaultValues: {
            email: '',
            username: '',
            password: '',
        },
        onSubmit: async ({value}) => {
            try {
                console.log(value);
                await api.post("/register", value);
                navigate('/login');
            } catch (error) {
                if (error.response?.data?.message) {
                    alert(error.response.data.message);
                } else {
                    alert('Registration failed');
                }
            }
        },
        validators: {
            onChange: ValidationSchema
        }
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
                            name="username"
                            children={(field) => <Field name="username" label="Username" field={field}/>}
                        />
                        <form.Field
                            name="password"
                            children={(field) => <Field name="password" label="Password" field={field}/>}
                        />
                        <form.Subscribe
                            selector={(state) => [state.canSubmit, state.isSubmitting]}
                            children={([canSubmit, isSubmitting]) => (
                                <button className={styles.submitButton} type="submit" disabled={!canSubmit}>
                                    {isSubmitting ? '...' : 'Register'}
                                </button>
                            )}
                        />
                    </form>
                    <Link className={styles.link} to={"/login"}>Login?</Link>
                </div>
            </div>
        </>
    );
};

export default Register;