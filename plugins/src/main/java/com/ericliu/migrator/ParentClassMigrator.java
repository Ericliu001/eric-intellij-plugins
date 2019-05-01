package com.ericliu.migrator;

import com.ericliu.SmallBoat;
import com.ericliu.SteamBoat;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class ParentClassMigrator extends MigratorBase {

    private final PsiClass steamBoatClass;
    private final PsiClass smallBoatClass;

    public ParentClassMigrator(final Project project) {
        super(project);
        steamBoatClass = toPsiClass(SteamBoat.class);
        smallBoatClass = toPsiClass(SmallBoat.class);
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

        steamBoatClass.getExtendsList().add(psiElementFactory.createClassReferenceElement(smallBoatClass));
    }
}
