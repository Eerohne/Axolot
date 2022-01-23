package ui;

import core.SystemBus;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.internal.ImGui;
import imgui.type.ImString;

public class MemoryPanel extends Panel{

    private char memoryPointer = 0;
    private char selectedCell = 5;

    private ImString memPtr_RegisterText = new ImString("0000");

    @Override
    public void ImGuiRender(SystemBus systemBus) {

        String oldVal;

        //memPointer
        {
            ImGui.text("Start: ");
            ImGui.sameLine();
            ImGui.setNextItemWidth(50);
            memPtr_RegisterText.set((Integer.toHexString((memoryPointer & 0xffff)).toUpperCase()));
            oldVal = memPtr_RegisterText.get();
            ImGui.inputText(" ", memPtr_RegisterText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

            if (oldVal != memPtr_RegisterText.get())
                memoryPointer = (memPtr_RegisterText.get().length() <= 0 ? 0 : (char) Long.parseLong(memPtr_RegisterText.get(), 16));
            ImGui.sameLine();

            if (ImGui.button("^")) {
                memoryPointer += 0x10;
            }
            ImGui.sameLine();
            if (ImGui.button("v") && memoryPointer >0) {
                memoryPointer -= 0x10;
            }

            if (memoryPointer < 0x10)
                memoryPointer = 0x0;

            memoryPointer -= (memoryPointer % 0x10);

            ImGui.text("memPtr: " + (int) memoryPointer);
        }

        ImVec4 pressColor =  new ImVec4( 0.5f, 0, 0, 1.0f );

        for(int y = 0; y < 0x10; y++)
        {
            for(int x = 0; x < 0x10; x++)
            {
                ImGui.setNextItemWidth(20);
                //if(ImGui.button((y == 0 ? "0" : "") + Integer.toHexString((x + y * 0x10 )) ))
                char address = (char)(x + y * 0x10 + memoryPointer);

                boolean pop = false;

                if(x + y * 0x10 == selectedCell ) {
                    ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0, 1.0f);
                    pop = true;
                }

                /*if(ImGui.button("a"))
                {
                    System.out.println("h");
                }*/
                if(ImGui.button(Integer.toHexString(systemBus.getRam().getValue(address))))
                {
                    selectedCell = (char)(x + y * 0x10);
                    System.out.println((int)selectedCell);
                }

                if(pop)
                    ImGui.popStyleColor();

                if(x < 0xf)
                    ImGui.sameLine();
            }
        }


    }
}
