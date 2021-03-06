package com.ericliu.language_plugin;

import com.ericliu.language_plugin.psi.SimpleFile;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;

public class SimpleStructureViewModel extends StructureViewModelBase implements
    StructureViewModel.ElementInfoProvider {
  public SimpleStructureViewModel(PsiFile psiFile) {
    super(psiFile, new SimpleStructureViewElement(psiFile));
  }

  @NotNull
  public Sorter[] getSorters() {
    return new Sorter[]{Sorter.ALPHA_SORTER};
  }


  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    return element instanceof SimpleFile;
  }
}