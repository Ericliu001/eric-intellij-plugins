package com.ericliu.action;

import com.ericliu.migrator.InterfaceMigrator;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;

public class ChangeInterfaceAction extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent e) {
        final Project project = e.getProject();

        Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                "Action Performed.",
                "Project: " + project.getName(),
                NotificationType.INFORMATION));

        WriteCommandAction.runWriteCommandAction(project, () -> {
            new InterfaceMigrator(project).migrate();
        });
    }
}
