import { useState } from 'react';
import axios from 'axios';
import styles from '../styles/Home.module.css';

export default function Home() {
    const [code, setCode] = useState('');
    const [errorOutput, setErrorOutput] = useState('');
    const [lexerOutput, setLexerOutput] = useState('');
    const [correctedOutput, setCorrectedOutput] = useState('');

    const handleRunJava = async () => {
        try {
            const response = await axios.post('http://localhost:5001/run-java', { code });
            setErrorOutput(response.data.errorOutput);
            setLexerOutput(response.data.lexerOutput);
            setCorrectedOutput(response.data.correctedOutput);
        } catch (error) {
            console.error('Error running Java program:', error);
            alert('Failed to connect to the backend. Please ensure the backend server is running.');
        }
    };

    return (
        <div className={styles.container}>
            <h1 className={styles.heading}>Compiler Debugger Tool</h1>
            <textarea
                value={code}
                onChange={(e) => setCode(e.target.value)}
                placeholder="Enter your code here..."
                className={styles.codeInput}
            />
            <button onClick={handleRunJava} className={styles.runButton}>
                Run Debugger
            </button>
            <div className={styles.outputContainer}>
                <div className={styles.correctedOutput}>
                    <h2 className={styles.sectionHeading}>Corrected Code</h2>
                    <pre className={styles.preStyle}>{correctedOutput}</pre>
                </div>
                <div className={styles.otherOutputs}>
                    <div className={styles.errorOutput}>
                        <h2 className={styles.sectionHeading}>Errors</h2>
                        <pre className={styles.preStyle}>{errorOutput}</pre>
                    </div>
                    <div className={styles.lexerOutput}>
                        <h2 className={styles.sectionHeading}>Lexer Output</h2>
                        <pre className={styles.preStyle}>{lexerOutput}</pre>
                    </div>
                </div>
            </div>
        </div>
    );
}