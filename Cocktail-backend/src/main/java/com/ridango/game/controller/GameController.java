package com.ridango.game.controller;

import com.ridango.game.service.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.ridango.game.dto.GameState;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:3000")  
public class GameController {

    private final GameService gameService;
    private GameState gameState = new GameState(); 

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Start a new game
    @Operation(summary = "Start a new game", description = "Initialize a new game session.")
    @ApiResponse(responseCode = "200", description = "Game started successfully")
    @GetMapping("/start")
    public ResponseEntity<GameState> startGame() {
        gameState = gameService.startNewGame(gameState);
        return ResponseEntity.ok(gameState);
    }
        
    // Guess the cocktail
    @Operation(summary = "Guess a cocktail", description = "Submit a guess for the cocktail currently displayed in the game.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Guess processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid guess")
    })
    @PostMapping("/guess")
    public ResponseEntity<GameState> guessCocktail(@RequestParam String guess) {
        gameState = gameService.processGuess(gameState, guess);
        return ResponseEntity.ok(gameState);
    }
      
    // Skip a game
    @Operation(summary = "Skip the current cocktail", description = "Skip the current cocktail in the game.")
    @ApiResponse(responseCode = "200", description = "Cocktail skipped successfully")
    @GetMapping("/skip")
    public ResponseEntity<GameState> skipCocktail() {
        gameState = gameService.skipCurrentCocktail(gameState);  
        return ResponseEntity.ok(gameState);
    }


}
