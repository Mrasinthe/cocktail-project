package com.ridango.game.service;

import com.ridango.game.dto.CocktailResponse;
import com.ridango.game.dto.GameState;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

    
    public GameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public GameState startNewGame(GameState gameState) {
        CocktailResponse cocktailResponse;

        // Ensure a new cocktail that hasn't been revealed yet
        do {
            cocktailResponse = restTemplate.getForObject(apiUrl, CocktailResponse.class);
        } while (gameState.hasCocktailBeenRevealed(cocktailResponse.getDrinks().get(0).getStrDrink()));

        // Initialize game state with new cocktail
        String cocktailName = cocktailResponse.getDrinks().get(0).getStrDrink();
        String hiddenName = "_".repeat(cocktailName.length());

        gameState.setCocktailName(cocktailName);
        gameState.setHiddenName(hiddenName);
        gameState.setInstructions(cocktailResponse.getDrinks().get(0).getStrInstructions());
        gameState.setCategory(cocktailResponse.getDrinks().get(0).getStrCategory());
        gameState.setGlass(cocktailResponse.getDrinks().get(0).getStrGlass());
        gameState.setDrinkThumb(cocktailResponse.getDrinks().get(0).getStrDrinkThumb());
        gameState.setAlcoholic(cocktailResponse.getDrinks().get(0).getStrAlcoholic());
        gameState.setAttemptsLeft(5);

        
        List<String> ingredientsWithMeasures = extractIngredientsWithMeasures(cocktailResponse.getDrinks().get(0));
        gameState.setIngredients(ingredientsWithMeasures);  

        gameState.addRevealedCocktail(cocktailName);

        return gameState;
    }

   
    private List<String> extractIngredientsWithMeasures(CocktailResponse.Drink drink) {
        List<String> ingredientsWithMeasures = new ArrayList<>();
        
      
        for (int i = 1; i <= 15; i++) {
            try {
               
                String ingredient = (String) CocktailResponse.Drink.class
                        .getDeclaredMethod("getStrIngredient" + i)
                        .invoke(drink);

                String measure = (String) CocktailResponse.Drink.class
                        .getDeclaredMethod("getStrMeasure" + i)
                        .invoke(drink);

               //System.out.println("Ingredient: " + ingredient + " | Measure: " + measure);
               
                if (ingredient != null && !ingredient.isEmpty()) {
                    if (measure != null && !measure.isEmpty()) {
                        ingredientsWithMeasures.add(ingredient + " - " + measure.trim());
                    } else {
                        ingredientsWithMeasures.add(ingredient);  
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ingredientsWithMeasures;
    }

    public GameState processGuess(GameState gameState, String guess) {
        String cocktailName = gameState.getCocktailName().toLowerCase();
        if (cocktailName.equals(guess.toLowerCase())) {
            gameState.setScore(gameState.getScore() + gameState.getAttemptsLeft());
            return startNewGame(gameState);
        } else {
            int remainingAttempts = gameState.getAttemptsLeft() - 1;
            gameState.setAttemptsLeft(remainingAttempts);
            if (remainingAttempts == 0) {
                return gameState;  // Game over
            } else {
                gameState.setHiddenName(revealLetters(gameState));
                return gameState;
            }
        }
    }
    
    private String revealLetters(GameState gameState) {
        String cocktailName = gameState.getCocktailName();
        StringBuilder hiddenName = new StringBuilder(gameState.getHiddenName());
        int attemptsLeft = gameState.getAttemptsLeft();
    
        // Count only hidden letters, excluding spaces
        long hiddenCount = countHiddenLetters(hiddenName, cocktailName);
    

        if (attemptsLeft == 1 && hiddenCount > 1) {
            revealUpTo(hiddenName, cocktailName, hiddenCount - 1); // Reveal all but one letter
        } else {
            // Reveal a proportional number of letters based on remaining attempts
            int lettersToReveal = Math.max(1, (int) Math.ceil(hiddenCount / (double) attemptsLeft));
            revealUpTo(hiddenName, cocktailName, lettersToReveal);
        }
    
        return hiddenName.toString();
    }
    
    private void revealUpTo(StringBuilder hiddenName, String cocktailName, long lettersToReveal) {
        for (int i = 0; i < hiddenName.length() && lettersToReveal > 0; i++) {
            if (hiddenName.charAt(i) == '_' && cocktailName.charAt(i) != ' ') {
                hiddenName.setCharAt(i, cocktailName.charAt(i));
                lettersToReveal--;
            }
        }
    }
    
    private long countHiddenLetters(StringBuilder hiddenName, String cocktailName) {
        long count = 0;
        for (int i = 0; i < hiddenName.length(); i++) {
            if (hiddenName.charAt(i) == '_' && cocktailName.charAt(i) != ' ') {
                count++;
            }
        }
        return count;
    }
    
    
    
    public GameState skipCurrentCocktail(GameState gameState) {
        return startNewGame(gameState);
    }



}
