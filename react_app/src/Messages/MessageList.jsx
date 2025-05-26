import {useQuery} from "@tanstack/react-query";
import {api} from '../main.jsx'
import Navigation from "../Navigation/Navigation.jsx";
import InternalServer from "../ErrorPages/InternalServer.jsx";
import styles from "./Message.module.css"
import useAuthStore from "../Authentication/AuthStore.js";
import {useNavigate} from "react-router-dom";

const fetchMessages = async () => {
    const { data } = await api.get("/messages");
    return data;
};

const MessageList = () => {
    const userInfo = useAuthStore();
    const navigate = useNavigate();

    const { data, error, isLoading } = useQuery({
        queryKey: ['messages'],
        queryFn: fetchMessages,
        enabled: true
    });

    if (!userInfo?.username) {
        navigate("/login");
        return;
    }

    if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    const sortedMessages = [...data].sort((a, b) =>
        new Date(b.createdAt) - new Date(a.createdAt)
    );


    const protocol = window.location.protocol;
    const host = window.location.host;

    return (
        <>
            <Navigation/>
            <div className={styles.mainPage}>
                    {sortedMessages.map(message => (
                        <div key={message.messageId} className={styles.messageCard}>
                            <div className={styles.left}>
                                <p className={styles.title}>{message.title}</p>
                                <p>{message.body}</p>
                            </div>
                            <div className={styles.right}>
                                <span className={styles.date}>
                                    {new Date(message.createdAt).toLocaleDateString()}
                                </span>
                                {message.link && (
                                    <a href={`${protocol}//${host}${message.link}`} className={styles.link}>
                                        More info
                                    </a>
                                )}
                            </div>
                        </div>
                    ))}
            </div>
        </>
    );
};

export default MessageList;