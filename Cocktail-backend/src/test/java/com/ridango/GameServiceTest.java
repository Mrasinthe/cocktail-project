package com.ridango;

import com.ridango.game.dto.CocktailResponse;
import com.ridango.game.dto.GameState;
import com.ridango.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

class GameServiceTest {

    @Mock
    private RestTemplate restTemplate; 

    @InjectMocks
    private GameService gameService;  

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
        gameState = new GameState();  
    }

    @Test
    void testStartNewGame() {
        
        CocktailResponse mockResponse = new CocktailResponse();
        CocktailResponse.Drink mockDrink = new CocktailResponse.Drink();

        mockDrink.setStrDrink("Mojito");
        mockDrink.setStrInstructions("Mix rum with mint");
        mockDrink.setStrCategory("Ordinary Drink");
        mockDrink.setStrGlass("Cocktail glass");
        mockDrink.setStrIngredient1("Rum");
        mockDrink.setStrMeasure1("2 oz");
        mockResponse.setDrinks(List.of(mockDrink));

        when(restTemplate.getForObject(anyString(), eq(CocktailResponse.class))).thenReturn(mockResponse);

        GameState newGameState = gameService.startNewGame(gameState);
   
        assertEquals("Mojito", newGameState.getCocktailName());
        assertEquals("______", newGameState.getHiddenName());
        assertEquals("Mix rum with mint", newGameState.getInstructions());
        assertEquals("Ordinary Drink", newGameState.getCategory());
        assertEquals("Cocktail glass", newGameState.getGlass());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(CocktailResponse.class));
    }

    @Test
    void testProcessGuessCorrect() {
   
    gameState.setCocktailName("Mojito");
    gameState.setAttemptsLeft(3);
    gameState.setHiddenName("______");

    CocktailResponse mockResponse = new CocktailResponse();
    CocktailResponse.Drink mockDrink = new CocktailResponse.Drink();

    mockDrink.setStrDrink("Mojito");
    mockDrink.setStrInstructions("Mix rum with mint");
    mockDrink.setStrCategory("Ordinary Drink");
    mockDrink.setStrGlass("Cocktail glass");
    mockDrink.setStrIngredient1("Rum");
    mockDrink.setStrMeasure1("2 oz");
    mockResponse.setDrinks(List.of(mockDrink));

    when(restTemplate.getForObject(anyString(), eq(CocktailResponse.class))).thenReturn(mockResponse);

    GameState updatedState = gameService.processGuess(gameState, "Mojito");

    assertEquals(3, updatedState.getScore());  
    assertEquals(5, updatedState.getAttemptsLeft());  

    verify(restTemplate, times(1)).getForObject(anyString(), eq(CocktailResponse.class));
}


    @Test
    void testProcessGuessIncorrect() {
        gameState.setCocktailName("Mojito");
        gameState.setAttemptsLeft(3);
        gameState.setHiddenName("______");


        GameState updatedState = gameService.processGuess(gameState, "Mojeto");

        assertEquals(2, updatedState.getAttemptsLeft());  
        assertNotEquals("Mojito", updatedState.getHiddenName());  
    }
}
