package com.ericliu.migrator;

import com.ericliu.SmallBoat;
import com.ericliu.SteamBoat;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiReferenceList;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.AllClassesSearch;

import java.util.HashSet;
import java.util.Set;

public class ParentClassMigrator extends MigratorBase {

    private final PsiClass steamBoatClass;
    private final PsiClass smallBoatClass;

    private final Set<PsiClass> targetSet;

    public ParentClassMigrator(final Project project) {
        super(project);
        targetSet = new HashSet<>();
        steamBoatClass = toPsiClass(SteamBoat.class);
        smallBoatClass = toPsiClass(SmallBoat.class);

        targetSet.add(steamBoatClass);
    }

    /**
     * Adds an extends express to extend a Parent Class.
     */
    @Override
    public void migrate() {
        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Add Parent class.",
                smallBoatClass.getName(),
                NotificationType.INFORMATION));

        for (final PsiClass currentClass : AllClassesSearch.
                search(GlobalSearchScope.allScope(project), project)) {

            if (targetSet.contains(currentClass)) {

                addParentClass(smallBoatClass, steamBoatClass.getExtendsList());
            }
        }
    }

    private void removeParentClass(final PsiClass targetClass, final PsiReferenceList extendsList) {

        for (final PsiJavaCodeReferenceElement referenceElement : extendsList.getReferenceElements()) {

            if (referenceElement.getQualifiedName().equals(targetClass)) {
                referenceElement.delete();
            }
        }

        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Removing",
                "Parent Class name: " + targetClass.getName(),
                NotificationType.INFORMATION));
    }

    private void addParentClass(final PsiClass targetClass, final PsiReferenceList extendsList) {
        extendsList.add(psiElementFactory.createClassReferenceElement(targetClass));

        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Adding",
                "Parent Class name: " + targetClass.getName(),
                NotificationType.INFORMATION));
    }
}
