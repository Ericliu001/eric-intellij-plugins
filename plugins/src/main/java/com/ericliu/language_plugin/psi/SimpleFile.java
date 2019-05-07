package com.ericliu.language_plugin.psi;

import com.ericliu.language_plugin.SimpleFileType;
import com.ericliu.language_plugin.SimpleLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class SimpleFile extends PsiFileBase {
  public SimpleFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, SimpleLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return SimpleFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Simple File";
  }

  @Override
  public Icon getIcon(int flags) {
    return super.getIcon(flags);
  }
}