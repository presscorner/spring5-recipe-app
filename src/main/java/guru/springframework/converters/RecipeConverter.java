package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeConverter extends Converter<RecipeCommand, Recipe> {

    private static CategoryConverter categoryConverter;
    private static IngredientConverter ingredientConverter;
    private static NotesConverter notesConverter;

    public RecipeConverter(CategoryConverter categoryConverter, IngredientConverter ingredientConverter, NotesConverter notesConverter) {
        super(RecipeConverter::convertToRecipe, RecipeConverter::convertToRecipeCommand);
        RecipeConverter.categoryConverter = categoryConverter;
        RecipeConverter.ingredientConverter = ingredientConverter;
        RecipeConverter.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    private static RecipeCommand convertToRecipeCommand(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setNotes(notesConverter.convertFromEntity(recipe.getNotes()));
        recipeCommand.setImage(recipe.getImage());

        if (!recipe.getCategories().isEmpty()) {
            recipe.getCategories().forEach((Category category) -> recipeCommand.getCategories().add(categoryConverter.convertFromEntity(category)));
        }

        if (!recipe.getIngredients().isEmpty()) {
            recipe.getIngredients().forEach((Ingredient ingredient) -> recipeCommand.getIngredients().add(ingredientConverter.convertFromEntity(ingredient)));
        }

        return recipeCommand;

    }

    @Synchronized
    @Nullable
    private static Recipe convertToRecipe(RecipeCommand recipeCommand) {
        if (recipeCommand == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setNotes(notesConverter.convertFromCommand(recipeCommand.getNotes()));
        recipe.setImage(recipeCommand.getImage());

        if (!recipeCommand.getCategories().isEmpty()) {
            recipeCommand.getCategories().forEach((CategoryCommand categoryCommand) -> recipe.getCategories().add(categoryConverter.convertFromCommand(categoryCommand)));
        }

        if (!recipeCommand.getIngredients().isEmpty()) {
            recipeCommand.getIngredients().forEach((IngredientCommand ingredientCommand) -> recipe.getIngredients().add(ingredientConverter.convertFromCommand(ingredientCommand)));
        }

        return recipe;
    }
}
