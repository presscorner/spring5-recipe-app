package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientConverter extends Converter<IngredientCommand, Ingredient> {

    private static UnitOfMeasureConverter uomConverter;

    public IngredientConverter(UnitOfMeasureConverter uomConverter) {
        super(IngredientConverter::convertToIngredient, IngredientConverter::convertToIngredientCommand);
        IngredientConverter.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    private static IngredientCommand convertToIngredientCommand(Ingredient ingredient) {

        if (ingredient == null) {
            return null;
        }

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUom(uomConverter.convertFromEntity(ingredient.getUom()));

        return ingredientCommand;
    }

    @Synchronized
    @Nullable
    private static Ingredient convertToIngredient(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setUom(uomConverter.convertFromCommand(ingredientCommand.getUom()));

        return ingredient;
    }
}
