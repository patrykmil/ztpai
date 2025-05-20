import { useQuery } from "@tanstack/react-query";
import { api } from '../main.jsx'
import Navigation from "../Navigation/Navigation.jsx";
import InternalServer from "../ErrorPages/InternalServer.jsx";
import styles from "./Message.module.css"

const fetchMessages = async () => {
    const { data } = await api.get("/messages/get/my");
    return data;
};

const MessageList = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['messages'],
        queryFn: fetchMessages,
        refetchOnWindowFocus: false,
        refetchOnMount: false,
        enabled: true
    });

    if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    const sortedMessages = [...data].sort((a, b) =>
        new Date(b.createdAt) - new Date(a.createdAt)
    );

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
                                    <a href={message.link} className={styles.link}>
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