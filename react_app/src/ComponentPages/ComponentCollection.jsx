import {useParams} from "react-router-dom";
import {api} from "../main.jsx";
import {useQueries, useQuery} from "@tanstack/react-query";
import Navigation from "../Navigation/Navigation.jsx";
import InternalServer from "../ErrorPages/InternalServer.jsx";
import LikedList from "./components/collection/LikedList.jsx";
import styles from "./components/Component.module.css"
import SetList from "./components/collection/SetList.jsx";
import RadioButton from "./components/collection/RadioButton.jsx";
import {useState} from "react";


const fetchLiked = async (username) => {
    const {data} = await api.get(`/components/users/${username}/liked`);
    return data;
}

const fetchSets = async (username) => {
    const {data} = await api.get(`/sets/users/name/${username}`);
    return data;
}

const fetchSetComponents = async (setID) => {
    const {data} = await api.get(`/components/sets/${setID}`)
    return data;
}

const ComponentCollection = () => {
    const {username} = useParams();
    const [activeView, setActiveView] = useState('sets');

    const {data: likedComponents, isLoading: isLikedLoading, error: likedError,} = useQuery({
        queryKey: ['likedComponents', username],
        queryFn: () => fetchLiked(username)
    });

    const {data: userSets, isLoading: isSetsLoading, error: setsError} = useQuery({
        queryKey: ['userSets', username],
        queryFn: () => fetchSets(username)
    });

    const setQueriesResults = useQueries({
        queries: userSets?.map(set => ({
            queryKey: ['setComponents', set.id],
            queryFn: () => fetchSetComponents(set.id)
        })) || []
    });

    const setComponents = userSets?.reduce((acc, set, index) => {
        acc[set.name] = setQueriesResults[index]?.data;
        return acc;
    }, {});

    const isLoading = isLikedLoading || isSetsLoading || setQueriesResults.some(query => query.isLoading);
    const error = likedError || setsError || setQueriesResults.some(query => query.error);

    if (isLoading) return <div className="loadingText">Loading...</div>;
    if (error) return <InternalServer/>;

    return (
        <>
            <Navigation/>
            <div className={`${styles.mainPage} ${styles.column}`}>
                <RadioButton activeView={activeView} setActiveView={setActiveView}/>
                <div className={styles.list}>
                    {activeView === 'liked' ? (
                        <LikedList liked={likedComponents}/>
                    ) : (
                        <SetList setComponents={setComponents}/>
                    )}
                </div>
            </div>
        </>
    )

}

export default ComponentCollection;