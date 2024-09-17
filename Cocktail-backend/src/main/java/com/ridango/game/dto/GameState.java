package com.ridango.game.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data  
@NoArgsConstructor  
public class GameState {

    private String cocktailName;
    private String instructions;
    private String hiddenName;
    private String category;
    private String glass;
    private List<String> ingredients;  
    private String drinkThumb;
    private String alcoholic;
    private int attemptsLeft = 5;  // Start with 5 attempts
    private int score = 0;  // Start score is 0
    private Set<String> revealedCocktails = new HashSet<>();  

    // To add cocktail to revealed cocktails set
    public void addRevealedCocktail(String cocktail) {
        this.revealedCocktails.add(cocktail);
    }

    // To check if a cocktail has already been revealed
    public boolean hasCocktailBeenRevealed(String cocktail) {
        return this.revealedCocktails.contains(cocktail);
    }
}
