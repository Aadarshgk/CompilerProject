const express = require('express');
const cors = require('cors');
const { exec } = require('child_process');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 5001;

app.use(cors());
app.use(express.json());

// Endpoint to save user input and run Java program
app.post('/run-java', (req, res) => {
    const { code } = req.body;

    // Save user input to input.txt
    fs.writeFileSync(path.join(__dirname, 'java-program/input.txt'), code);

    // Execute the Java program
    exec('cd java-program && javac CompilerTool.java && java CompilerTool', (error, stdout, stderr) => {
        if (error) {
            console.error(`Error executing Java program: ${error}`);
            return res.status(500).json({ error: 'Failed to execute Java program' });
        }

        // Add a delay to ensure files are written
        setTimeout(() => {
            const errorPath = path.join(__dirname, 'java-program/errors.txt');
            const lexerPath = path.join(__dirname, 'java-program/lexer.txt');
            const correctedPath = path.join(__dirname, 'java-program/correctedcode.txt');

            const errorOutput = fs.existsSync(errorPath)
                ? fs.readFileSync(errorPath, 'utf8')
                : 'No errors found.';

            const lexerOutput = fs.existsSync(lexerPath)
                ? fs.readFileSync(lexerPath, 'utf8')
                : 'No lexer output found.';

            const correctedOutput = fs.existsSync(correctedPath)
                ? fs.readFileSync(correctedPath, 'utf8')
                : 'No corrected code found.';

            res.json({ errorOutput, lexerOutput, correctedOutput });
        }, 1000); // 1-second delay
    });
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});