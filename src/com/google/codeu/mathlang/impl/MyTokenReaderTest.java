package com.google.codeu.mathlang.impl;

import com.google.codeu.mathlang.core.tokens.StringToken;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Jiahui Chen on 8/4/2017.
 */
public class MyTokenReaderTest {
    @Test
    public void testOneWord() throws IOException{
        StringToken hi = new StringToken("hi");
        MyTokenReader hiReader = new MyTokenReader("\"hi\"");
        assertTrue(hi.equals(hiReader.next()));
    }

    @Test
    public void firstTest() throws IOException{
        StringToken note = new StringToken("note");
        MyTokenReader noteReader = new MyTokenReader("note \"my comment\";");
        assertTrue(note.equals(noteReader.next()));

        StringToken myComment = new StringToken("my comment");
        assertTrue(myComment.equals(noteReader.next()));
    }
}