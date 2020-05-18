package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientConverter;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientConverter ingredientConverter;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientConverter ingredientConverter, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientConverter = ingredientConverter;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); // todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientConverter.convertFromCommand(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                    .findFirst();
            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totaly safe... But vest guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //todo check for fail
            return ingredientConverter.convertFromEntity(savedIngredientOptional.get());
        }
    }
}
