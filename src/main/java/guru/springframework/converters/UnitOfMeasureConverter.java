package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureConverter extends Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    public UnitOfMeasureConverter() {
        super(UnitOfMeasureConverter::convertToUom, UnitOfMeasureConverter::convertToUomCommand);
    }

    @Synchronized
    @Nullable
    private static UnitOfMeasureCommand convertToUomCommand(UnitOfMeasure uom) {
        if (uom == null) {
            return null;
        }

        final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
        uomc.setId(uom.getId());
        uomc.setDescription(uom.getDescription());

        return uomc;
    }

    @Synchronized
    @Nullable
    private static UnitOfMeasure convertToUom(UnitOfMeasureCommand uomc) {
        if (uomc == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomc.getId());
        uom.setDescription(uomc.getDescription());

        return uom;
    }

}
