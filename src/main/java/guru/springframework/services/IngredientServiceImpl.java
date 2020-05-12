package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientConverter ingredientConverter;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientConverter ingredientConverter, RecipeRepository recipeRepository) {
        this.ingredientConverter = ingredientConverter;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error(String.format("recipe with id = %d not found.", recipeId));
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientConverter.convertFromEntity(ingredient)).findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            log.error(String.format("Ingredient with id = %d not found.", ingredientId));
        }

        return ingredientCommandOptional.get();
    }
}
