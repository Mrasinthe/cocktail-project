package com.ridango.game.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
public class CocktailService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";
    
     // Search Cocktails by Name
     public List<Map<String, String>> searchCocktailByName(String cocktailName) {
        String url = BASE_URL + "search.php?s=" + cocktailName;
        ResponseEntity<Map<String, List<Map<String, String>>>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<Map<String, String>>>>() {});
        return response.getBody().get("drinks");
    }

    // Categories
    public List<Map<String, String>> getCategories() {
        String url = BASE_URL + "list.php?c=list";
        ResponseEntity<Map<String, List<Map<String, String>>>> response =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<Map<String, String>>>>() {});
        return response.getBody().get("drinks");
    }

    // Glasses
    public List<Map<String, String>> getGlasses() {
        String url = BASE_URL + "list.php?g=list";
        ResponseEntity<Map<String, List<Map<String, String>>>> response =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<Map<String, String>>>>() {});
        return response.getBody().get("drinks");
    }

    // Ingredients
    public List<Map<String, String>> getIngredients() {
        String url = BASE_URL + "list.php?i=list";
        ResponseEntity<Map<String, List<Map<String, String>>>> response =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<Map<String, String>>>>() {});
        return response.getBody().get("drinks");
    }

    // Alcoholic/Non-Alcoholic
    public List<Map<String, String>> getAlcoholicFilters() {
        String url = BASE_URL + "list.php?a=list";
        ResponseEntity<Map<String, List<Map<String, String>>>> response =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<Map<String, String>>>>() {});
        return response.getBody().get("drinks");
    }

}
