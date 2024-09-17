import axios from 'axios';

// Base URL for the API
const BASE_URL = 'http://localhost:8080/api';
// const BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';


// API Endpoints
const API = {
  startGame: `${BASE_URL}/game/start`,
  guessCocktail: `${BASE_URL}/game/guess`,
  skipCocktail: `${BASE_URL}/game/skip`,
  filterCocktails: `${BASE_URL}/cocktails/search`,
 
};

// Function to start a new game
export const startGameAPI = async () => {
  try {
    const response = await axios.get(API.startGame);
    console.log(response)
    return response.data;
   
  } catch (error) {
    console.error('Error starting game:', error);
    throw error;
  }
};

// Function to make a guess
export const guessCocktailAPI = async (guess) => {
  try {
    const response = await axios.post(`${API.guessCocktail}?guess=${guess}`);
    return response.data;
  } catch (error) {
    console.error('Error making guess:', error);
    throw error;
  }
};

// Function to skip a game
export const skipCocktailAPI = async () => {
  try {
    const response = await axios.get(API.skipCocktail);
    console.log(response);
    return response.data;
  } catch (error) {
    console.error('Error skipping cocktail:', error);
    throw error;
  }
};


// Function to filter cocktails
export const filterCocktailsAPI = async (name) => {
  try {
    const response = await axios.get(`${API.filterCocktails}`, {
    params: { name }
  });
    return response.data;
  } catch (error) {
    console.error('Error filtering cocktails:', error);
    throw error;
  }
};

