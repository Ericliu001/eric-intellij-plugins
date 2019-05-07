package com.ericliu.language_plugin;

import com.ericliu.language_plugin.psi.SimpleTypes;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;

import org.jetbrains.annotations.NotNull;

public class SimpleCompletionContributor extends CompletionContributor {
  public SimpleCompletionContributor() {
    extend(CompletionType.BASIC,
           PlatformPatterns.psiElement(SimpleTypes.VALUE).withLanguage(SimpleLanguage.INSTANCE),
           new CompletionProvider<CompletionParameters>() {
             public void addCompletions(@NotNull CompletionParameters parameters,
                                        ProcessingContext context,
                                        @NotNull CompletionResultSet resultSet) {
               resultSet.addElement(LookupElementBuilder.create("Hello"));
             }
           }
    );
  }
}
