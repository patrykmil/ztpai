import styles from "./../Component.module.css";
import ChangeCodeButtons from "./ChangeCodeButtons.jsx";
import InputCodeField from "./InputCodeField.jsx";

const CodeEditor = ({ form, activeTab, setActiveTab, previewData, setPreviewData }) => {
    return (
        <div className={styles.rightSide}>
            <ChangeCodeButtons activeTab={activeTab} setActiveTab={setActiveTab}/>
            <form.Field name="html" children={() => (
                <InputCodeField
                    start={previewData.html}
                    language="html"
                    activeTab={activeTab}
                    onInput={(e) => {
                        form.setFieldValue('html', e.target.value);
                        setPreviewData({...previewData, html: e.target.value});
                    }}
                />
            )}/>
            <form.Field name="css" children={() => (
                <InputCodeField
                    start={previewData.css}
                    language="css"
                    activeTab={activeTab}
                    onInput={(e) => {
                        form.setFieldValue('css', e.target.value);
                        setPreviewData({...previewData, css: e.target.value});
                    }}
                />
            )}/>
            <form.Subscribe
                selector={(state) => [state.canSubmit, state.isSubmitting]}
                children={([canSubmit, isSubmitting]) => (
                    <button className={styles.submitButton} type="submit" disabled={!canSubmit}>
                        {isSubmitting ? '...' : 'Submit'}
                    </button>
                )}
            />
        </div>
    );
};

export default CodeEditor;