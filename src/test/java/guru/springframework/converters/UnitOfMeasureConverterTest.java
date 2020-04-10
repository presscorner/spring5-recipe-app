package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    UnitOfMeasureConverter uomConverter;

    @Before
    public void setUp() throws Exception {
        uomConverter = new UnitOfMeasureConverter();
    }

    @Test
    public void testNullObjects() throws Exception {
        assertNull(uomConverter.convertFromCommand(null));
        assertNull(uomConverter.convertFromEntity(null));
    }

    @Test
    public void testEmptyObjects() throws Exception {
        assertNotNull(uomConverter.convertFromCommand(new UnitOfMeasureCommand()));
        assertNotNull(uomConverter.convertFromEntity(new UnitOfMeasure()));
    }

    @Test
    public void testConverter() throws Exception {
        //given
        UnitOfMeasure category = new UnitOfMeasure();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        UnitOfMeasureCommand categoryCommand = new UnitOfMeasureCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure returnedCategory = uomConverter.convertFromCommand(categoryCommand);
        UnitOfMeasureCommand returnedCategoryCommand = uomConverter.convertFromEntity(category);

        //then
        assertEquals(returnedCategory.getId(), returnedCategoryCommand.getId());
        assertEquals(returnedCategory.getDescription(), returnedCategoryCommand.getDescription());
    }
}