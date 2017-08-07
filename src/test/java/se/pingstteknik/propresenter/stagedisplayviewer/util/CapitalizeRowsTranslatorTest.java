package se.pingstteknik.propresenter.stagedisplayviewer.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author danielkihlgren
 */
public class CapitalizeRowsTranslatorTest {

    @Test
    public void internationalCharactersShouldBeCapitalizedCorrectly() throws Exception {
        assertThat(CapitalizeRowsTranslator.transform("å\nä\nö"), is("Å\nÄ\nÖ"));
    }

}