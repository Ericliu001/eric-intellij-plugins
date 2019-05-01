package com.ericliu;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
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
    private final PsiClass vehicleClass;

    InterfaceMigrator(final Project project) {
        this.project = project;
        javaPsiFacade = JavaPsiFacade.getInstance(project);
        psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);
        boatClass = toPsiClass(Boat.class);
        shipClass = toPsiClass(Ship.class);
        vehicleClass = toPsiClass(Vehicle.class);
    }

    void migrate() {
        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Migration started.",
                "Project: " + project.getName(),
                NotificationType.INFORMATION));

        for (final PsiClass boatImplementor : DirectClassInheritorsSearch.search(boatClass,
                GlobalSearchScope.allScope(project)).findAll()) {

            final PsiReferenceList implementsList = boatImplementor.getImplementsList();

            addInterface(vehicleClass, implementsList);

            removeInterface(boatClass, implementsList);

            addInterface(shipClass, implementsList);
        }
    }

    private void addInterface(final PsiClass interfaceClass, final PsiReferenceList implementsList) {
        implementsList.add(psiElementFactory.createClassReferenceElement(interfaceClass));

        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Found Implementor",
                "Adds interface " + interfaceClass.getName(),
                NotificationType.INFORMATION));
    }

    private void removeInterface(final PsiClass interfaceClass, final PsiReferenceList implementsList) {
        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Removing",
                "Interface name: " + interfaceClass.getName(),
                NotificationType.INFORMATION));

        for (final PsiJavaCodeReferenceElement referenceElement : implementsList.getReferenceElements()) {
            Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                    "PsiJavaCodeReferenceElement",
                    "Reference name: " + referenceElement.getQualifiedName(),
                    NotificationType.INFORMATION));

            if (referenceElement.getQualifiedName().equals(interfaceClass.getQualifiedName())) {

                Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                        "Found interface to remove",
                        referenceElement.getQualifiedName(),
                        NotificationType.INFORMATION));
                referenceElement.delete();
            }
        }
    }

    private PsiClass toPsiClass(Class<?> clazz) {
        return javaPsiFacade.findClass(clazz.getName(), GlobalSearchScope.allScope(project));
    }
}
