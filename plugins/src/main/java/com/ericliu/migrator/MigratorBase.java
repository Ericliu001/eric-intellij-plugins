package com.ericliu.migrator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.search.GlobalSearchScope;

public abstract class MigratorBase {

    protected final Project project;
    protected final JavaPsiFacade javaPsiFacade;
    protected final PsiElementFactory psiElementFactory;

    MigratorBase(final Project project) {
        this.project = project;
        javaPsiFacade = JavaPsiFacade.getInstance(project);
        psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);
    }

    protected PsiClass toPsiClass(Class<?> clazz) {
        return javaPsiFacade.findClass(clazz.getName(), GlobalSearchScope.allScope(project));
    }

    public abstract void migrate();
}
