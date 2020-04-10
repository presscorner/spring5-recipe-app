package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter extends Converter<CategoryCommand, Category> {
    public CategoryConverter() {
        super(CategoryConverter::convertToCategory, CategoryConverter::convertToCategoryCommand);
    }

    @Synchronized
    @Nullable
    private static CategoryCommand convertToCategoryCommand(Category category) {
        if (category == null) {
            return null;
        }
        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(category.getId());
        categoryCommand.setDescription(category.getDescription());

        return categoryCommand;
    }

    @Synchronized
    @Nullable
    private static Category convertToCategory(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setDescription(categoryCommand.getDescription());

        return category;
    }
}
