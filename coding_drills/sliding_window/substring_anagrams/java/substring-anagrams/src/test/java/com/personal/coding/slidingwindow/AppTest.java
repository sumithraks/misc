package com.personal.coding.slidingwindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

        String txt = "bacdgabcda";
        String pat = "abcd";
        //AnagramFinder.search(pat, txt);
        assertEquals(3,AnagramFinder.search(pat, txt));
        txt="aaababaa";
        pat="aaba";
        assertEquals(3,AnagramFinder.search(pat, txt));
        assertEquals(0,AnagramFinder.search(null, txt));
        assertEquals(0,AnagramFinder.search(pat, null));
        assertEquals(0,AnagramFinder.search(null, null));
        assertEquals(0,AnagramFinder.search(txt, pat));
        pat = "zzz";
        assertEquals(0,AnagramFinder.search(pat, txt));
    }
}
