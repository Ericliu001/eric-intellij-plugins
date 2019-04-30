package com.ericliu;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiReferenceList;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.DirectClassInheritorsSearch;

/**
 * This migrator replaces Boat interface implementation with Ship interface.
 */
class InterfaceMigrator {

    private final Project project;
    private final JavaPsiFacade javaPsiFacade;
    private final PsiElementFactory psiElementFactory;

    private final PsiClass boatClass;
    private final PsiClass shipClass;

    InterfaceMigrator(final Project project) {
        this.project = project;
        javaPsiFacade = JavaPsiFacade.getInstance(project);
        psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);
        boatClass = toPsiClass(Boat.class);
        shipClass = toPsiClass(Ship.class);
    }

    void migrate() {
        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Migration started.",
                "Project: " + project.getName(),
                NotificationType.INFORMATION));

        for (final PsiClass boatImplementor : DirectClassInheritorsSearch.search(boatClass,
                GlobalSearchScope.allScope(project)).findAll()) {

            final PsiReferenceList implementsList = boatImplementor.getImplementsList();
            removeBoatInterface(implementsList);

            addShipInterface(boatImplementor, implementsList);
        }
    }

    private void addShipInterface(final PsiClass boatImplementor, final PsiReferenceList implementsList) {
        implementsList.add(psiElementFactory.createClassReferenceElement(shipClass));

        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Found Implementor: " + boatImplementor.getName(),
                "Adds interface " + shipClass.getName(),
                NotificationType.INFORMATION));
    }

    private void removeBoatInterface(final PsiReferenceList implementsList) {
        final PsiClassType boatType = psiElementFactory.createType(boatClass);

        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Removing boat",
                "Reference name: " + boatType.getClassName(),
                NotificationType.INFORMATION));

        for (final PsiJavaCodeReferenceElement referenceElement : implementsList.getReferenceElements()) {
            Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                    "PsiJavaCodeReferenceElement",
                    "Reference name: " + referenceElement.getQualifiedName(),
                    NotificationType.INFORMATION));

            referenceElement.delete();
        }
    }

    private PsiClass toPsiClass(Class<?> clazz) {
        return javaPsiFacade.findClass(clazz.getName(), GlobalSearchScope.allScope(project));
    }
}
