package com.ericliu;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.DirectClassInheritorsSearch;

import java.util.Collection;

public class ChangeInterfaceAction extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent e) {
        final Project project = e.getProject();
        final JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(project);

        WriteCommandAction.runWriteCommandAction(project, () -> {
            final String name = Boat.class.getName();
            final PsiClass[] boats = psiFacade.findClasses(name,
                    GlobalSearchScope.allScope(project));

            Notifications.Bus.notify(new Notification("xxx", "Boat class name: " + name, "boats " + boats.length,
                    NotificationType.INFORMATION));

            for (final PsiClass boat : boats) {
                final Collection<PsiClass> allBoats = DirectClassInheritorsSearch.search(boat,
                        GlobalSearchScope.everythingScope(project), true).findAll();

                for (final PsiClass boatImplementor : allBoats) {

                    Notifications.Bus.notify(new Notification("xxx", "What's implementing Boat: ",
                            boatImplementor.getName(),
                            NotificationType.INFORMATION));
                }
            }
        });
    }
}
