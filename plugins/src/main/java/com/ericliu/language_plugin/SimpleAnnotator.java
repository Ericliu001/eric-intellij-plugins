package com.ericliu.language_plugin;

import com.ericliu.language_plugin.psi.SimpleProperty;
import com.ericliu.migrator.InterfaceMigrator;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PsiLiteralExpression) {
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
            String value =
                    literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;

            if (value != null && value.startsWith("simple" + ":")) {
                Project project = element.getProject();
                String key = value.substring(7);
                List<SimpleProperty> properties = SimpleUtil.findProperties(project, key);
                if (properties.size() == 1) {
                    TextRange range = new TextRange(element.getTextRange().getStartOffset() + 7,
                            element.getTextRange().getStartOffset() + 7);
                    Annotation annotation = holder.createInfoAnnotation(range, null);
                    annotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);
                } else if (properties.size() == 0) {
                    TextRange range = new TextRange(element.getTextRange().getStartOffset() + 8,
                            element.getTextRange().getEndOffset());
                    holder.createErrorAnnotation(range, "Unresolved property").
                            registerFix(new CreatePropertyQuickFix(key));
                }
            }
        } else if (element instanceof PsiMethodCallExpression) {
            PsiMethodCallExpression psiMethodCallExpression = (PsiMethodCallExpression) element;

            final PsiMethod psiMethod = psiMethodCallExpression.resolveMethod();

            final String psiMethodName = psiMethod.getName();

            if ("drinkCoke".equals(psiMethodName) && psiMethod.getParameterList().isEmpty()) {
                Notifications.Bus.notify(new Notification(InterfaceMigrator.class.getName(),
                        "Method drinkCode() is called.",
                        "method name: " + psiMethodName + "; parameter count: " + psiMethod.getParameterList().getParametersCount(),
                        NotificationType.INFORMATION));
            }
        }
    }
}
