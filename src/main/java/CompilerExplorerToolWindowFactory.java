import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.project.Project;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CompilerExplorerToolWindowFactory implements ToolWindowFactory {
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        addContentToToolWindow(createContent(project), toolWindow);
    }

    @NotNull
    private static JComponent createContent(@NotNull Project project) {
        CompilerExplorer explorer = new CompilerExplorer();

        CompilerExplorerToolWindowForm form = new CompilerExplorerToolWindowForm(project);
        form.getRefreshButton().addActionListener(event -> explorer.refresh());

        return form.getContent();
    }

    private static void addContentToToolWindow(@NotNull JComponent content, @NotNull ToolWindow toolWindow) {
        toolWindow.getContentManager().addContent(ContentFactory.SERVICE.getInstance().createContent(content, "", false));
    }
}
