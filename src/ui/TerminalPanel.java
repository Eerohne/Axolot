package ui;

import core.SystemBus;
import imgui.ImGui;

public class TerminalPanel extends Panel{

    @Override
    public void ImGuiRender(SystemBus systemBus) {

        ImGui.textWrapped(systemBus.getTerminal().getScreenBuffer());
    }
}
