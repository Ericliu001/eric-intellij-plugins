package com.ericliu.language_plugin.psi.impl;

import com.ericliu.language_plugin.psi.SimpleNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

import org.jetbrains.annotations.NotNull;

public abstract class SimpleNamedElementImpl extends ASTWrapperPsiElement implements SimpleNamedElement {
  public SimpleNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }
}