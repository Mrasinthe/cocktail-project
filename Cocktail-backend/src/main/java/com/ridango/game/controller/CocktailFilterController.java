package com.ridango.game.controller;

import com.ridango.game.service.CocktailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cocktails")
@CrossOrigin(origins = "http://localhost:3000")
public class CocktailFilterController {

    private final CocktailService cocktailService;

    public CocktailFilterController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    // Search cocktails by name
    @Operation(summary = "Search cocktails by name", description = "Search cocktails from the database by their name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Cocktails not found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, String>>> searchCocktailByName(@RequestParam String name) {
        return ResponseEntity.ok(cocktailService.searchCocktailByName(name));
    }

    // Fetch categories
    @Operation(summary = "Fetch all cocktail categories", description = "Get a list of all available cocktail categories.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, String>>> getCategories() {
        return ResponseEntity.ok(cocktailService.getCategories());
    }

    // Fetch glasses
    @Operation(summary = "Fetch all glasses", description = "Get a list of all types of glasses used for cocktails.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/glasses")
    public ResponseEntity<List<Map<String, String>>> getGlasses() {
        return ResponseEntity.ok(cocktailService.getGlasses());
    }

    // Fetch ingredients
    @Operation(summary = "Fetch all ingredients", description = "Get a list of all ingredients used in cocktails.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/ingredients")
    public ResponseEntity<List<Map<String, String>>> getIngredients() {
        return ResponseEntity.ok(cocktailService.getIngredients());
    }

    // Fetch alcoholic
    @Operation(summary = "Fetch alcoholic filters", description = "Get the list of available alcoholic filters.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/alcoholic")
    public ResponseEntity<List<Map<String, String>>> getAlcoholicFilters() {
        return ResponseEntity.ok(cocktailService.getAlcoholicFilters());
    }  
   
}
