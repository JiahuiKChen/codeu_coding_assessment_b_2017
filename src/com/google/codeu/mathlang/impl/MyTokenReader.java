// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.codeu.mathlang.core.tokens.*;
import com.google.codeu.mathlang.parsing.TokenReader;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  int index = 0;
  String input;
//  ArrayList<String> tokens;

  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    input = source;
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
    StringBuilder token = new StringBuilder();

    while (index < input.length()) {
//      String currentInput = input.substring(index);
//      char currentChar = currentInput.charAt(0);
      char currentChar = input.charAt(index);

      //handles white space
      if (currentChar == ' ' || currentChar == '\n') {
        index++;
        return next();
      }
      //Symbol Tokens
      if (isSymbolToken(currentChar)) {
        index++;
        return new SymbolToken(currentChar);
      }
      //Number Tokens
      if (Character.isDigit(currentChar)){
        int numIndex = index;

        while (Character.isDigit(input.charAt(numIndex))){
          token.append(input.charAt(numIndex));
          numIndex++;
        }
        index = numIndex;
        return new NumberToken(Double.parseDouble(token.toString()));
      }
      //handles token within quotations
      if (currentChar == '"') {
        int endQuote = ++index;

        //append every character until another " is hit
        while (input.charAt(endQuote) != '"') {
          token.append(input.charAt(endQuote));

          if (endQuote == input.length() - 1) {
            throw new IOException("Unmatched quotation mark in input!");
          }
          endQuote++;
        }
        //once the while loop is exited, endQuote will be index of closing quote
        index = ++endQuote;
        //everything appended is the string within the quotes
        String quotedToken = token.toString();
        return new StringToken(quotedToken);
      }
      //handles input without quotations
      int i = index;

      while(Character.isDigit(input.charAt(i)) || Character.isAlphabetic(input.charAt(i))){
          token.append(input.charAt(i));
          i++;
        }
      index = i;
      String unquotedToken = token.toString();
      if (isNameToken(unquotedToken)) {
        return new NameToken(unquotedToken);
      }
      return new StringToken(unquotedToken);
    }
      return null;
  }

  private boolean isSymbolToken(char token) {
      return (token == '+' || token == '-' || token == '=' || token == ';');
  }

  private boolean isNameToken(String token) {
    //NameToken can't have space within it
     if (token.split("\\s+").length == 1){
       return Character.isAlphabetic(token.charAt(0));
     }
     return false;
  }

}