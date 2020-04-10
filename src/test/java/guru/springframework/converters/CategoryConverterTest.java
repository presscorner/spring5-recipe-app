package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryConverter();
    }

    @Test
    public void testNullObjects() throws Exception {
        assertNull(converter.convertFromCommand(null));
        assertNull(converter.convertFromEntity(null));
    }

    @Test
    public void testEmptyObjects() throws Exception {
        assertNotNull(converter.convertFromCommand(new CategoryCommand()));
        assertNotNull(converter.convertFromEntity(new Category()));
    }

    @Test
    public void testConverter() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category returnedCategory = converter.convertFromCommand(categoryCommand);
        CategoryCommand returnedCategoryCommand = converter.convertFromEntity(category);

        //then
        assertEquals(returnedCategory.getId(), returnedCategoryCommand.getId());
        assertEquals(returnedCategory.getDescription(), returnedCategoryCommand.getDescription());
    }


}