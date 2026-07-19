package com.example.authmanager.repository;

import com.example.authmanager.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByOwnerEmail(String ownerEmail);
}