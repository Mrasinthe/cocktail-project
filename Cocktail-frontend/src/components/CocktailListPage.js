import React, { useState } from 'react';
import { filterCocktailsAPI } from '../apis/index';  

function CocktailListPage() {
  const [cocktails, setCocktails] = useState([]);
  const [cocktailName, setCocktailName] = useState('');
  const [loading, setLoading] = useState(false);
  const [selectedCocktail, setSelectedCocktail] = useState(null);
  const [ingredientDetails, setIngredientDetails] = useState(null); 

  const handleSearch = async () => {
    setLoading(true); 
    try {
      const data = await filterCocktailsAPI(cocktailName);
      setCocktails(data);
    } catch (error) {
      console.error('Error searching for cocktails:', error);
    } finally {
      setLoading(false);  
    }
  };

  const fetchIngredientDetails = async (ingredient) => {
    try {
      const response = await fetch(`https://www.thecocktaildb.com/api/json/v1/1/search.php?i=${ingredient}`);
      const data = await response.json();
      setIngredientDetails(data.ingredients[0]); 
    } catch (error) {
      console.error('Error fetching ingredient details:', error);
    }
  };

  const formatIngredients = (cocktail) => {
    return (
      <div>
        {[...Array(15).keys()].map((i) => {
          const ingredient = cocktail[`strIngredient${i + 1}`];
          const measure = cocktail[`strMeasure${i + 1}`];
          return ingredient ? (
            <span key={i}>
              <span
                onClick={() => fetchIngredientDetails(ingredient)}
                style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}
              >
                {ingredient}
              </span>
              {measure ? ` - ${measure.trim()}` : ''}
              {i < 14 ? ', ' : ''}
              <br />
            </span>
          ) : null;
        })}
      </div>
    );
  };

  const handleOpenModal = (cocktail) => {
    setSelectedCocktail(cocktail);
  };

  const handleCloseModal = () => {
    setSelectedCocktail(null);
  };

  const handleCloseIngredientModal = () => {
    setIngredientDetails(null);
  };

  return (
    <div className="container mt-4">
      <h4 className="text-center">Search Cocktail</h4>

      <div className="input-group mb-4" style={{ maxWidth: '600px', margin: '0 auto' }}>
        <input
          type="text"
          value={cocktailName}
          onChange={(e) => setCocktailName(e.target.value)}
          placeholder="Enter cocktail name"
          className="form-control"
        />
        <button 
          onClick={handleSearch} 
          className="btn btn-primary"
          disabled={!cocktailName}
        >
          {loading ? 'Searching...' : 'Search'}
        </button>
      </div>

      {loading && <div className="text-center">Loading...</div>}

      {cocktails.length > 0 && (
        <div className="table-responsive mt-4">
        <table className="table table-bordered table-hover mt-4">
          <thead className="thead-dark">
            <tr>
              <th>#</th>
              <th>Drink Name</th>
              <th>Instructions</th>
              <th>Category</th> 
              <th>Alcoholic Level</th> 
              <th>Glass</th>
              <th>Ingredients</th>
              <th>Image</th>
            </tr>
          </thead>
          <tbody>
            {cocktails.map((cocktail, index) => (
              <tr key={index}>
                <td>
                  <a href="#!" onClick={() => handleOpenModal(cocktail)}>
                    {cocktail.idDrink}
                  </a>
                </td>
                <td>{cocktail.strDrink}</td>
                <td>{cocktail.strInstructions}</td>
                <td>{cocktail.strCategory}</td>
                <td>{cocktail.strAlcoholic}</td>
                <td>{cocktail.strGlass}</td>
                <td>{formatIngredients(cocktail)}</td>
                <td>
                  {cocktail.strDrinkThumb && (
                    <img
                      src={cocktail.strDrinkThumb}
                      alt={cocktail.strDrink}
                      style={{ maxWidth: '100px', borderRadius: '8px' }}
                    />
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </div>
      )}

      {!loading && cocktails.length === 0 && cocktailName && (
        <div className="text-center mt-4">No cocktails found for "{cocktailName}"</div>
      )}

      {selectedCocktail && (
        <div className="modal show" tabIndex="-1" style={{ display: 'block', color: '#8c8c8c' }}>
          <div className="modal-dialog">
            <div className="modal-content" style={{ backgroundColor: '#f8f9fa', border: '1px solid #ccc', borderRadius: '10px' }}>
              <div className="modal-header" style={{ backgroundColor: '#8c8c8c', color: '#fff' }}>
                <h5 className="modal-title">{selectedCocktail.strDrink}</h5>
                <button 
                  type="button" 
                  className="btn-close" 
                  style={{ color: '#f8f9fa' }} 
                  onClick={handleCloseModal}
                ></button>
              </div>
              <div className="modal-body" style={{ maxHeight: '500px', overflowY: 'auto', wordWrap: 'break-word' }}>
                {selectedCocktail.strDrinkThumb && (
                  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column' }}>
                    <img
                      src={selectedCocktail.strDrinkThumb}
                      alt={selectedCocktail.strDrink}
                      style={{ maxHeight: '50%', maxWidth: '50%', borderRadius: '8px', marginBottom: '15px' }}
                    />
                  </div>
                )}
                <p><strong>Instructions:</strong> {selectedCocktail.strInstructions || 'N/A'}</p>
                <p><strong>Ingredients:</strong></p>
                <ul>
                  {[...Array(15).keys()].map(i => {
                    const ingredient = selectedCocktail[`strIngredient${i + 1}`];
                    const measure = selectedCocktail[`strMeasure${i + 1}`];
                    return ingredient ? (
                      <li key={i}>
                        <span
                          onClick={() => fetchIngredientDetails(ingredient)}
                          style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}
                        >
                          {ingredient}
                        </span>
                        {measure ? ` - ${measure.trim()}` : ''}
                      </li>
                    ) : null;
                  })}
                </ul>
                <p><strong>Alcoholic Level:</strong> {selectedCocktail.strAlcoholic || 'N/A'}</p>
                <p><strong>Glass:</strong> {selectedCocktail.strGlass || 'N/A'}</p>
                <p><strong>Category:</strong> {selectedCocktail.strCategory || 'N/A'}</p>
              </div>
              <div className="modal-footer" style={{ backgroundColor: '#8c8c8c', color: '#fff' }}></div>
            </div>
          </div>
        </div>
      )}
      
      {ingredientDetails && (
        <div className="modal show" style={{ display: 'block' }}>
          <div className="modal-dialog modal-lg"> 
            <div className="modal-content" style={{ backgroundColor: '#8c8c8c', color: '#fff' }}>
              <div className="modal-header">
                <h5 className="modal-title">{ingredientDetails.strIngredient}</h5>
                <button type="button" className="btn-close" onClick={handleCloseIngredientModal}></button>
              </div>
              <div className="modal-body">
                <table className="table table-bordered">
                  <tbody>
                    <tr>
                      <th>Name</th>
                      <td>{ingredientDetails.strIngredient || 'N/A'}</td>
                    </tr>
                    <tr>
                      <th>Description</th>
                      <td>{ingredientDetails.strDescription || 'No description available'}</td>
                    </tr>
                    <tr>
                      <th>Type</th>
                      <td>{ingredientDetails.strType || 'N/A'}</td>
                    </tr>
                    <tr>
                      <th>Alcohol</th>
                      <td>{ingredientDetails.strAlcohol ? 'Yes' : 'No'}</td>
                    </tr>
                    <tr>
                      <th>ABV</th>
                      <td>{ingredientDetails.strABV || 'N/A'}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default CocktailListPage;
