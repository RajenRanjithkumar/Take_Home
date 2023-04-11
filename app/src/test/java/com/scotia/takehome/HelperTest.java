package com.scotia.takehome;

import static org.junit.Assert.*;

import com.scotia.takehome.utils.Helper;

import org.junit.Test;

public class HelperTest {

    @Test
    public void valid_userName() {

        String result;

        Helper helper = new Helper();

        result = helper.userName("Octocat");

        assertEquals("valid", result);


    }

    @Test
    public void inValid_userName() {

        String result;

        Helper helper = new Helper();

        result = helper.userName("dfhgildfbdfkgbdfjklgjbdfjklgbsdfjklgbjdfjklgbsdjklgbsdfjklgb");

        assertEquals("invalid", result);


    }

    @Test
    public void userName_empty() {

        String result;

        Helper helper = new Helper();

        result = helper.userName("");

        assertEquals("invalid", result);


    }


    @Test
    public void userName_Integers() {

        String result;

        Helper helper = new Helper();

        result = helper.isNumeric("123344345");

        assertEquals("invalid", result);


    }

    @Test
    public void userNameNotIntegers() {

        String result;

        Helper helper = new Helper();

        result = helper.isNumeric("octocat");

        assertEquals("valid", result);


    }

    @Test
    public void username_without_SpecialCharacters() {

        String result;

        Helper helper = new Helper();

        result = helper.isSpecialCharacters("JIM");

        assertEquals("valid", result);


    }

    @Test
    public void username_with_SpecialCharacters() {

        String result;

        Helper helper = new Helper();

        result = helper.isSpecialCharacters("%%$#@!@$%^");

        assertEquals("invalid", result);


    }


}