package com.ericliu.action;

import com.ericliu.migrator.ParentClassMigrator;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;

public class AddParentClassAction extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent e) {
        final Project project = e.getProject();

        WriteCommandAction.runWriteCommandAction(project, () -> {
            new ParentClassMigrator(project).migrate();
        });
    }
}
