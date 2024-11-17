package com.reliaquest.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiUtilTest {

    @Test
    public void testMask_LessThanThreeCharacters() {
        String name = "ab";
        assertEquals("ab", ApiUtil.mask(name));
    }

    @Test
    public void testMask_ThreeCharacters() {
        String name = "abc";
        assertEquals("abc", ApiUtil.mask(name));
    }

    @Test
    public void testMask_MoreThanThreeCharacters() {
        String name = "abcdef";
        assertEquals("****def", ApiUtil.mask(name));
    }

    @Test
    public void testMask_Null() {
        assertNull(ApiUtil.mask(null));
    }

    @Test
    public void testMask_Empty() {
        assertEquals("", ApiUtil.mask(""));
    }

    @Test
    public void testMask_ThreeCharactersWithSpace() {
        String name = "abc ";
        assertEquals("abc ", ApiUtil.mask(name));
    }

    @Test
    public void testMask_MoreThanThreeCharactersWithSpace() {
        String name = "abcdef ";
        assertEquals("****def", ApiUtil.mask(name));
    }
}