import React, {useEffect} from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import styles from "./Component.module.css"
import "./Highlighting.css"

const CodeField = ({content, language, activeTab}) => {
    useEffect(() => {
        Prism.highlightAll();
    }, [content]);

    return (
        <pre className={`${styles.codeContainer} ${activeTab === language ? styles.active : ""}`}>
            <code className={`language-${language} match-braces`}>
                {content}
            </code>
        </pre>
    );
};

export default CodeField;