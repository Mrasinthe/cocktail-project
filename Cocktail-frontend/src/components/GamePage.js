import React, { useState, useEffect } from 'react';
import { startGameAPI, guessCocktailAPI,skipCocktailAPI } from '../apis/index';
import './GamePage.css';  

function GamePage() {
  const [gameState, setGameState] = useState(null);
  const [guess, setGuess] = useState('');
  const [message, setMessage] = useState('');

  useEffect(() => {
    startGame();
  }, []);

  const startGame = async () => {
    try {
      const data = await startGameAPI();
      setGameState(data);
      setMessage('Guess the cocktail!');
      setGuess('');
    } catch (error) {
      console.error('Error starting game:', error);
    }
  };

  const makeGuess = async () => {
    try {
      const data = await guessCocktailAPI(guess);  
      const isNewGame = gameState && gameState.cocktailName !== data.cocktailName; 
      setGameState(data);
  
      if (data.attemptsLeft === 0) {
        setMessage(`Game Over! The cocktail was: ${data.cocktailName}`);
      } else if (isNewGame) {
        setMessage('Guess the cocktail!'); 
      } else {
        setMessage('Try Again!');
      }
  
      setGuess(''); 
    } catch (error) {
      console.error('Error making guess:', error);
    }
  };
  

  const handleSkip = async () => {
    try {
      const newGameState = await skipCocktailAPI();
      setGameState(newGameState);  
      setMessage('Guess the cocktail!');
    } catch (error) {
      console.error('Error skipping the cocktail:', error);
    }
  };

  const handleGuessChange = (e) => {
    setGuess(e.target.value);
  };

  return (
    <div className="container">
      
      {gameState ? (
        <div className="card">
          <div className="card-body">
          <h4 className="text-center">Guess the Cocktail</h4>
          <div className="row">
  <div className="col-md-6">
    <h4 className="card-title">Instructions:</h4>
    <h4 className="card-text"><strong>{gameState.instructions}</strong></h4>

    <h4 className="card-title">Cocktail Name: <strong>{gameState.hiddenName}</strong> </h4>
    {gameState.attemptsLeft <= 5 && (
                  <h4 className="card-title">Ingredients: <strong> {Array.isArray(gameState.ingredients) ? gameState.ingredients.join(', ') : gameState.ingredients}</strong>
                  </h4>
                )}
                  <h4 className="card-title">Alcoholic: {gameState.attemptsLeft <= 4 && (<strong>{gameState.alcoholic}</strong> )}</h4> 
     <h4 className="card-title">Category: {gameState.attemptsLeft <= 3 && (<strong>{gameState.category}</strong> )}</h4>  
   
      <h4 className="card-title">Glass: {gameState.attemptsLeft <= 2 && (<strong>{gameState.glass}</strong> )} </h4>
   
   
            
            <div className="mb-3">
              <input
                type="text"
                value={guess}
                onChange={handleGuessChange}
                className="form-control"
                placeholder="Enter your guess"
                disabled={gameState.attemptsLeft === 0}
              />
            </div>
            <div className="row">
            <div className="col-md-3">
            <button
              className="btn btn-primary mb-3"
              onClick={makeGuess}
              disabled={gameState.attemptsLeft === 0}
            >
              Guess
            </button> </div>
            <div className="col-md-3">
            <button  className="btn btn-secondary mb-3" onClick={handleSkip}>Skip</button> 
            </div>
            </div>
            <p>Attempts Left: <strong>{gameState.attemptsLeft}</strong></p>
            <p>Score: <strong>{gameState.score}</strong></p>
            <p>{message}</p>

            {gameState.attemptsLeft === 0 && (
              <button className="btn btn-danger" onClick={startGame}>
                Play Again
              </button>
            )}
  </div>
  <div className="col-md-2 "></div>
  <div className="col-md-4 ">
  { gameState.drinkThumb && (
      <div className="mb-3">
        <h4 className="card-title">Image: </h4> {gameState.attemptsLeft <= 4 && (
        <img
          src={gameState.drinkThumb}
          alt={gameState.cocktailName || "Cocktail Image"}
          className="img-fluid"
          style={{ maxHeight: '300px', maxWidth: '100%' }}
        />)}
      </div>
    )}
  </div>
</div>
          </div>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}

export default GamePage;
