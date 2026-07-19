package com.example.authmanager.service;

import com.example.authmanager.model.Recipe;
import com.example.authmanager.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(String title, String description, String ownerEmail) {
        Recipe recipe = new Recipe(title, description, ownerEmail);
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getMyRecipes(String ownerEmail) {
        return recipeRepository.findByOwnerEmail(ownerEmail);
    }

    public boolean deleteRecipe(Long id, String requesterEmail) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            return false;
        }

        if (!recipe.getOwnerEmail().equals(requesterEmail)) {
            return false;
        }

        recipeRepository.deleteById(id);
        return true;
    }
}