package guru.springframework.converters;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter<T, U> {
    private final Function<T, U> fromCommand;
    private final Function<U, T> fromEntity;

    public Converter(Function<T, U> fromCommand, Function<U, T> fromEntity) {
        this.fromCommand = fromCommand;
        this.fromEntity = fromEntity;
    }

    public final U convertFromCommand(final T command) {
        return fromCommand.apply(command);
    }

    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }

    public final List<U> createFromCommands(final Collection<T> commands) {
        return commands.stream().map(this::convertFromCommand).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }

}
