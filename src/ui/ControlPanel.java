package ui;

import core.SystemBus;
import imgui.ImGui;

public class ControlPanel extends Panel{

    private boolean showText = true;
    private final int showCounterMax = 20;
    private int showCounter = 20;


    @Override
    public void ImGuiRender(SystemBus systemBus) {

        if(systemBus.isHalted())
        {
            if(showText) {
                ImGui.text("-The computer is Halted-");
            }
            else
                ImGui.text("");
        }
        else {
            if(showText) {
                ImGui.text("Executing...");
            }
            else
                ImGui.text("Executing..");
        }

        showCounter--;
        if(showCounter <= 0)
        {
            showCounter = showCounterMax;
            showText = !showText;
        }

        if(ImGui.button(systemBus.isHalted() ? "Resume" : "Halt"))
        {
            systemBus.setHalted(!systemBus.isHalted());
        }
        ImGui.sameLine();
        if(systemBus.isHalted()) {
            if (ImGui.button("Step Once")) {
                systemBus.clockOnce();
            }
        }

        if(ImGui.button("Reset"))
        {
            //systemBus.reset();
        }
    }
}
