package com.compilerexplorer.compiler;

import com.compilerexplorer.common.RefreshSignal;
import com.compilerexplorer.common.SettingsProvider;
import com.compilerexplorer.common.datamodel.SourceRemoteMatched;
import com.compilerexplorer.common.datamodel.state.LocalCompilerPath;
import com.compilerexplorer.common.datamodel.state.SettingsState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SourceRemoteMatchSaver implements Consumer<SourceRemoteMatched> {
    @NotNull
    private final Project project;
    @NotNull
    private final Consumer<SourceRemoteMatched> sourceRemoteMatchedConsumer;

    public SourceRemoteMatchSaver(@NotNull Project project_, @NotNull Consumer<SourceRemoteMatched> sourceRemoteMatchedConsumer_) {
        project = project_;
        sourceRemoteMatchedConsumer = sourceRemoteMatchedConsumer_;
    }

    @Override
    public void accept(@NotNull SourceRemoteMatched sourceRemoteMatched) {
        SettingsState state = SettingsProvider.getInstance(project).getState();

        if (!state.getEnabled()) {
            return;
        }

        state.getCompilerMatches().put(new LocalCompilerPath(sourceRemoteMatched.getSourceCompilerSettings().getSourceSettings().getCompiler().getAbsolutePath()), sourceRemoteMatched.getRemoteCompilerMatches());
        sourceRemoteMatchedConsumer.accept(sourceRemoteMatched);
    }

    @NotNull
    public Consumer<RefreshSignal> asRefreshSignalConsumer() {
        return refreshSignal -> {
            SettingsState state = SettingsProvider.getInstance(project).getState();
            state.setCompilerMatches(SettingsState.EMPTY.getCompilerMatches());
        };
    }
}
