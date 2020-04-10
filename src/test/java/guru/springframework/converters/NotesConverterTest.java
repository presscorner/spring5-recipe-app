package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesConverterTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    NotesConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesConverter();
    }

    @Test
    public void testNullObjects() throws Exception {
        assertNull(converter.convertFromCommand(null));
        assertNull(converter.convertFromEntity(null));
    }

    @Test
    public void testEmptyObjects() throws Exception {
        assertNotNull(converter.convertFromCommand(new NotesCommand()));
        assertNotNull(converter.convertFromEntity(new Notes()));
    }

    @Test
    public void testConverter() throws Exception {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(DESCRIPTION);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(DESCRIPTION);

        //when
        Notes returnedNotes = converter.convertFromCommand(notesCommand);
        NotesCommand returnedNotesCommand = converter.convertFromEntity(notes);

        //then
        assertEquals(returnedNotes.getId(), returnedNotesCommand.getId());
        assertEquals(returnedNotes.getRecipeNotes(), returnedNotesCommand.getRecipeNotes());
    }


}