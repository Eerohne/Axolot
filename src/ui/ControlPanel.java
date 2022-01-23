package ui;

import assembler.Asssembler;
import core.SystemBus;
import imgui.ImGui;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

import java.io.File;
import java.util.Map;

public class ControlPanel extends Panel{

    private boolean showText = true;
    private final int showCounterMax = 20;
    private int showCounter = 20;

    private ImString filePathText = new ImString();

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

        if(ImGui.button(systemBus.isHalted() ? "Resume >" : "Halt ||"))
        {
            systemBus.setHalted(!systemBus.isHalted());
        }
        if(systemBus.isHalted()) {
            ImGui.sameLine();
            if (ImGui.button("Step Once ->")) {
                systemBus.clockOnce();
            }
        }

        if(ImGui.button("Reset Computer"))
        {
            systemBus.reset();
        }

        ImGui.spacing();
        ImGui.separator();

        ImGui.text("Program File path");
        ImGui.sameLine();
        imgui.internal.ImGui.inputText("  ", filePathText, ImGuiInputTextFlags.CallbackResize);
        if(ImGui.button("Load New Program"))
        {
            Asssembler as = new Asssembler(new File(filePathText.get()));
            systemBus.getRam().reset();
            if(as.getProgram() != null) {
                systemBus.getRam().loadProgram(as.getProgram());
            }
        }

    }


}
