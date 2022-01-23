package ui;

import core.SystemBus;
import imgui.ImGuiStyle;
import imgui.ImVec4;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.internal.ImGui;
import imgui.type.ImString;

public class MemoryPanel extends Panel{

    private char memoryPointer = 0;
    private char selectedCell = 0;

    private ImString memPtr_RegisterText = new ImString("0000");
    private ImString mem_EditText = new ImString("00");

    @Override
    public void ImGuiRender(SystemBus systemBus) {

        String oldVal;

        //memPointer
        {
            ImGui.text("Start (hex): ");
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

            ImGui.text("memPtr (Decimal): " + (int) memoryPointer);
        }


        //editField
        {
            ImGui.text("Edit memory (" + ((int)(systemBus.getRam().getValue((char)(memoryPointer + selectedCell))) & 0xff) + " Decimal) : ");
            ImGui.sameLine();
            ImGui.setNextItemWidth(50);
            byte fetchedValue = systemBus.getRam().getValue((char)(memoryPointer + selectedCell));
            mem_EditText.set((Integer.toHexString((fetchedValue & 0xff)).toUpperCase()));
            oldVal = mem_EditText.get();
            ImGui.inputText("  ", mem_EditText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

            if (oldVal != mem_EditText.get())
                systemBus.getRam().storeValue((byte)(mem_EditText.get().length() <= 0 ? 0 : (char) Long.parseLong(mem_EditText.get(), 16)), (char)(memoryPointer + selectedCell));
        }

        ImVec4 pressColor =  new ImVec4( 0.5f, 0, 0, 1.0f );

        for(int y = 0; y < 0x10; y++)
        {
            for(int x = 0; x < 0x10; x++)
            {
                ImGui.setNextItemWidth(20);



                int index = (x + y * 0x10);
                boolean pop = false;
                if(selectedCell == index)
                {
                    pop = true;
                    ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0, 0, 1.0f);
                }


                byte fetchedValue = systemBus.getRam().getValue((char)(memoryPointer + index));
                ImGui.pushID(index);
                if(ImGui.button(( (((int)fetchedValue) & 0xff) < 0x10 ? "0" : "") +Integer.toHexString( (int)fetchedValue & 0xff)) )
                {
                    selectedCell = (char)(x + y * 0x10);
                }
                ImGui.popID();

                if(pop)
                {
                    ImGui.popStyleColor();
                }


                if(x < 0xf)
                    ImGui.sameLine();
            }
        }


    }
}
