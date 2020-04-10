package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesConverter extends Converter<NotesCommand, Notes> {

    public NotesConverter() {
        super(NotesConverter::convertToNotes, NotesConverter::convertToNotesCommand);
    }

    @Synchronized
    @Nullable
    private static NotesCommand convertToNotesCommand(Notes notes) {

        if (notes == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(notes.getId());
        notesCommand.setRecipeNotes(notes.getRecipeNotes());

        return notesCommand;
    }

    @Synchronized
    @Nullable
    private static Notes convertToNotes(NotesCommand notesCommand) {

        if (notesCommand == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());

        return notes;
    }
}
