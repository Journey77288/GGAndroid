package io.ganguo.library.util;

import org.junit.Test;

import io.ganguo.utils.util.Strings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tony on 9/30/15.
 */
public class StringsTest {

    @Test
    public void testEmpty() throws Exception {
        assertTrue(Strings.isEmpty(null, ""));
        assertTrue(Strings.isNotEmpty("a"));
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(Strings.isEquals("a", "a"));
        assertFalse(Strings.isEquals("a", "b"));
        assertTrue(Strings.isEqualsIgnoreCase("a", "A"));
        assertFalse(Strings.isEqualsIgnoreCase("a", "b"));
    }

    @Test
    public void testFormat() throws Exception {
        String pattern = "{0} is {1}";
        String result = Strings.format(pattern, "apple", "fruit");

        assertEquals(result, "apple is fruit");
    }

    @Test
    public void testRandomUuId() {
        String randomUUID1 = Strings.randomUUID();
        String randomUUID2 = Strings.randomUUID();

        assertNotEquals(randomUUID1, randomUUID2);
    }
}
