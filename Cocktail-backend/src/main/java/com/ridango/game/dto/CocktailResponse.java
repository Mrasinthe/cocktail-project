package com.ridango.game.dto;
import lombok.Data;
import java.util.List;

@Data
public class CocktailResponse {

    private List<Drink> drinks;

    @Data
    public static class Drink {
        private String idDrink;
        private String strDrink;           
        private String strInstructions;   
        private String strCategory;        
        private String strGlass;           
        private String strDrinkThumb;  
        private String strAlcoholic;
         
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;
        private String strIngredient5;
        private String strIngredient6;
        private String strIngredient7;
        private String strIngredient8;
        private String strIngredient9;
        private String strIngredient10;
        private String strIngredient11;
        private String strIngredient12;
        private String strIngredient13;
        private String strIngredient14;
        private String strIngredient15;
  
        private String strMeasure1;
        private String strMeasure2;
        private String strMeasure3;
        private String strMeasure4;
        private String strMeasure5;
        private String strMeasure6;
        private String strMeasure7;
        private String strMeasure8;
        private String strMeasure9;
        private String strMeasure10;
        private String strMeasure11;
        private String strMeasure12;
        private String strMeasure13;
        private String strMeasure14;
        private String strMeasure15;
    }
}
