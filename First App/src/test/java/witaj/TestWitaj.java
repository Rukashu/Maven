package witaj;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestWitaj {

    private Przywitanie przywitaj = new Przywitanie();

    @Test
    public void ProgramWita()
    {
        assertThat(przywitaj.czesc(), containsString("Witam"));
    }
}
