package com.ericliu.language_plugin.psi;

import com.ericliu.language_plugin.SimpleLanguage;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SimpleTokenType extends IElementType {
  public SimpleTokenType(@NotNull @NonNls String debugName) {
    super(debugName, SimpleLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return "SimpleTokenType." + super.toString();
  }
}