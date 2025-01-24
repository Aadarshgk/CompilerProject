# Compiler Project

This project is a compiler implementation that includes both frontend and backend components. The frontend provides a user interface for inputting code and displaying the Corrected Code, while the backend handles the compilation and execution of the code.

## Features

- **Code Input**: Enter your code in the provided editor.
- **Compilation**: The backend compiles the code and returns the corrected code.
- **Error Handling**: Displays compilation errors and lexer outputs.
- **Responsive UI**: User-friendly interface with interactive elements.

## Getting Started

Follow these steps to set up and run the project locally.

### Prerequisites

Make sure you have the following installed:

- [Node.js](https://nodejs.org/)
- [npm](https://www.npmjs.com/)
- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)

### Installation

1. **Clone the repository**:
    ```sh
    git clone <repository-url>
    cd CompilerProject
    ```

2. **Set up the backend**:
    ```sh
    cd backend
    npm install
    npm start
    ```

3. **Set up the frontend**:
    Open another terminal and navigate to the frontend directory:
    ```sh
    cd frontend
    npm install
    npm install axios
    npm run dev
    ```

### Usage

- Open your browser and navigate to `http://localhost:3000`.
- Enter your code in the editor and click the "Run" button.
- View the output, errors, and lexer results in the respective sections.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.
