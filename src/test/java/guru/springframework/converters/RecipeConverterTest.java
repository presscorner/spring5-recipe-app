package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeConverterTest {
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;


    RecipeConverter converter;
    Recipe recipe = new Recipe();
    RecipeCommand recipeCommand = new RecipeCommand();

    @Before
    public void setUp() {
        converter = new RecipeConverter(new CategoryConverter(), new IngredientConverter(new UnitOfMeasureConverter()), new NotesConverter());

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setNotes(notes);
        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CAT_ID_1);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGRED_ID_1);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(INGRED_ID_2);

        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setNotes(notesCommand);
        recipeCommand.getCategories().add(categoryCommand);
        recipeCommand.getCategories().add(categoryCommand2);
        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().add(ingredientCommand2);
    }

    @Test
    public void testNullObjects() throws Exception {
        assertNull(converter.convertFromCommand(null));
        assertNull(converter.convertFromEntity(null));
    }

    @Test
    public void testEmptyObjects() throws Exception {
        assertNotNull(converter.convertFromCommand(new RecipeCommand()));
        assertNotNull(converter.convertFromEntity(new Recipe()));
    }

    @Test
    public void testConverter() throws Exception {
        //when
        Recipe returnedRecipe = converter.convertFromCommand(recipeCommand);
        RecipeCommand returnedRecipeCommand = converter.convertFromEntity(recipe);

        //then
        assertNotNull(recipe);
        assertNotNull(recipeCommand);

        assertEquals(recipe.getId(), recipeCommand.getId());
        assertEquals(recipe.getCookTime(), recipeCommand.getCookTime());
        assertEquals(recipe.getPrepTime(), recipeCommand.getPrepTime());
        assertEquals(recipe.getDescription(), recipeCommand.getDescription());
        assertEquals(recipe.getDifficulty(), recipeCommand.getDifficulty());
        assertEquals(recipe.getDirections(), recipeCommand.getDirections());
        assertEquals(recipe.getServings(), recipeCommand.getServings());
        assertEquals(recipe.getSource(), recipeCommand.getSource());
        assertEquals(recipe.getUrl(), recipeCommand.getUrl());
        assertEquals(recipe.getNotes().getId(), recipeCommand.getNotes().getId());
        assertEquals(recipe.getCategories().size(), recipeCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), recipeCommand.getIngredients().size());

    }


}