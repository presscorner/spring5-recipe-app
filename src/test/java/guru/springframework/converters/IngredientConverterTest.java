package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientConverterTest {

    private static final Recipe RECIPE = new Recipe();
    private static final BigDecimal AMMOUNT = new BigDecimal("1");
    private static final String DESCRIPTION = "Cheeseburger";
    private static final Long UOM_ID = 2L;
    private static final Long ID_VALUE = 1L;

    IngredientConverter converter;
    Ingredient ingredient = new Ingredient();
    IngredientCommand ingredientCommand = new IngredientCommand();

    @Before
    public void setUp() throws Exception {
        converter = new IngredientConverter(new UnitOfMeasureConverter());

        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMMOUNT);
        ingredient.setDescription(DESCRIPTION);

        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
    }

    @Test
    public void testNullObjects() throws Exception {
        assertNull(converter.convertFromCommand(null));
        assertNull(converter.convertFromEntity(null));
    }

    @Test
    public void testEmptyObjects() throws Exception {
        assertNotNull(converter.convertFromCommand(new IngredientCommand()));
        assertNotNull(converter.convertFromEntity(new Ingredient()));
    }

    @Test
    public void testConverterNullUom() throws Exception {
        //given
        ingredient.setUom(null);
        ingredientCommand.setUom(null);

        //when
        IngredientCommand ingredientCommand = converter.convertFromEntity(ingredient);
        Ingredient ingredient = converter.convertFromCommand(ingredientCommand);

        //then
        assertNull(ingredientCommand.getUom());
        assertNull(ingredient.getUom());

        assertEquals(ingredient.getId(), ingredientCommand.getId());
        assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
    }

    @Test
    public void testConverterWithUom() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();

        uom.setId(UOM_ID);
        uomc.setId(UOM_ID);

        ingredient.setUom(uom);
        ingredientCommand.setUom(uomc);

        //when
        IngredientCommand ingredientCommand = converter.convertFromEntity(ingredient);
        Ingredient ingredient = converter.convertFromCommand(ingredientCommand);

        //then
        assertNotNull(ingredientCommand.getUom());
        assertNotNull(ingredient.getUom());

        assertEquals(ingredient.getId(), ingredientCommand.getId());
        assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
    }
}