package com.example.authmanager.controller;

import com.example.authmanager.model.Recipe;
import com.example.authmanager.service.RecipeService;
import com.example.authmanager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequest request,
                                          @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        String ownerEmail = jwtUtil.extractEmail(token);
        Recipe recipe = recipeService.createRecipe(request.getTitle(), request.getDescription(), ownerEmail);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyRecipes(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        String ownerEmail = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(recipeService.getMyRecipes(ownerEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id,
                                          @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        String ownerEmail = jwtUtil.extractEmail(token);
        boolean deleted = recipeService.deleteRecipe(id, ownerEmail);
        if (deleted) {
            return ResponseEntity.ok("Recipe deleted successfully");
        } else {
            return ResponseEntity.status(403).body("You are not allowed to delete this recipe");
        }
    }

    public static class RecipeRequest {
        private String title;
        private String description;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}