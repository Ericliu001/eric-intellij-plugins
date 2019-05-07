package com.ericliu.language_plugin;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class SimpleLexerAdapter extends FlexAdapter {
  public SimpleLexerAdapter() {
    super(new SimpleLexer((Reader) null));
  }
}
