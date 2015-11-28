package se.pingstteknik.propresenter.stagedisplayviewer.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author danielkihlgren
 * @version 1.2.0
 * @since 1.2.0
 */
public class RemoveLinesAfterEmptyLineTest {

    private final RemoveLinesAfterEmptyLineTranslator transformer = new RemoveLinesAfterEmptyLineTranslator();

    @Test
    public void testOneLine() throws Exception {
        assertThat(transformer.transform(""), is(""));
        assertThat(transformer.transform("\n"), is(""));
        assertThat(transformer.transform("first line"), is("first line"));
    }

    @Test
    public void testTwoLines() throws Exception {
        assertThat(transformer.transform("\n"), is(""));
        assertThat(transformer.transform("First Line\nSecond Line"), is("First Line\nSecond Line"));
        assertThat(transformer.transform("First Line\n"), is("First Line"));
        assertThat(transformer.transform("\nSecond Line"), is(""));
    }

    @Test
    public void testThreeLines() throws Exception {
        assertThat(transformer.transform("First Line\nSecond Line\nThird Line"), is("First Line\nSecond Line\nThird Line"));
        assertThat(transformer.transform("First Line\n\nThird Line"), is("First Line"));
    }
}