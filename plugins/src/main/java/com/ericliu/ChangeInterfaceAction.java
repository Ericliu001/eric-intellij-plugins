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
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiReferenceList;
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

            final PsiClass[] ships = psiFacade.findClasses(Ship.class.getName(),
                    GlobalSearchScope.allScope(project));

            Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(), "Boat class name: " + name,
                    "boats " + boats.length,
                    NotificationType.INFORMATION));

            for (final PsiClass boat : boats) {
                final Collection<PsiClass> allBoats = DirectClassInheritorsSearch.search(boat,
                        GlobalSearchScope.everythingScope(project), true).findAll();

                for (final PsiClass boatImplementor : allBoats) {

                    Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(), "What's implementing "
                            + "Boat: ",
                            boatImplementor.getName(),
                            NotificationType.INFORMATION));

                    final PsiClass[] interfaces = boatImplementor.getInterfaces();

                    Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(),
                            "How many interfaces: " + interfaces.length,
                            boatImplementor.getName(),
                            NotificationType.INFORMATION));

                    for (final PsiClass anInterface : interfaces) {
                        Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(),
                                "What is the interface: " + anInterface.getName(),
                                boatImplementor.getName(),
                                NotificationType.INFORMATION));


                        if (Boat.class.getName().equals(anInterface.getQualifiedName())) {
                            Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(),
                                    "Qualified name matched " + anInterface.getName(),
                                    anInterface.getName(),
                                    NotificationType.INFORMATION));

                            final PsiReferenceList implementsList = boatImplementor.getImplementsList();

                            for (final PsiClassType referencedType : implementsList.getReferencedTypes()) {
                                Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(),
                                        "Referenced Type ",
                                        referencedType.getClassName(),
                                        NotificationType.INFORMATION));

                                if (referencedType.getClass().equals(boat)) {
                                    Notifications.Bus.notify(new Notification(ChangeInterfaceAction.class.getName(),
                                            "This is a boat!",
                                            referencedType.getClassName(),
                                            NotificationType.INFORMATION));

                                }
                            }


                        }
                    }


                    interfaces[0] = ships[0];
                }
            }
        });
    }
}
