import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import GamePage from './components/GamePage';
import CocktailListPage from './components/CocktailListPage';
import './App.css';  

function App() {
  return (
    <Router>
      <div className="background-container">  
        <nav className="navbar navbar-expand-lg navbar-light bg-light mb-4">
          <div className="container-fluid">
            <Link className="navbar-brand" to="/">
              <img
                src="/cocktail logo.jpeg"
                alt="Cocktail Game Logo"
                style={{ height: '40px', marginRight: '10px', marginLeft: '10px' }}
              />
             
            </Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav">
                <li className="nav-item">
                  <Link className="nav-link" to="/">Game</Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/cocktails">All Cocktails</Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>

        <Routes>
          <Route path="/" element={<GamePage />} />
          <Route path="/cocktails" element={<CocktailListPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
