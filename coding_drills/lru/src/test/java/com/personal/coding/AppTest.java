package com.personal.coding;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.personal.coding.cache.LRU;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void putKey()
    {
        LRU l = new LRU(2);
        l.put("a",1);
        l.put("b",2);
        assert(l.get("a")==1);
        assert(l.get("b")==2);
    }

    @Test
    public void trimCache()
    {
        LRU l = new LRU(2);
        l.put("a",1);
        l.put("b",2);
        assert(l.get("a")==1);
        assert(l.get("b")==2);
        l.put("c",3);
        System.out.println("**********");
        System.out.println(l.get("a"));
        System.out.println("**********");
        assert(l.get("a")==null);
    }
}
