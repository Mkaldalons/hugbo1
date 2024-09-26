import React from 'react';
import NavBar from "./navBar.js";
import './App.css';
import frontpage from "./frontPage.png"

function App() {
    return (
        <div className="App">
            <NavBar />
            <div className="App-header">
                <img src={frontpage} alt="frontpage"/>
            </div>
        </div>
    );
}

export default App;