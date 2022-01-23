package ui;

import core.CPU;
import core.SystemBus;
import imgui.flag.ImGuiInputTextFlags;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;

public class CPUPanel extends Panel{

    private ImString A_RegisterText = new ImString("00");
    private ImString X_RegisterText = new ImString("00");
    private ImString Y_RegisterText = new ImString("00");
    private ImString PC_RegisterText = new ImString("0000");
    private ImString S_RegisterText = new ImString("0000");

    private ImBoolean[] flagsBool = {new ImBoolean(false), new ImBoolean(false), new ImBoolean(false)};


    public void ImGuiRender(SystemBus systemBus)  {
        CPU cpu = systemBus.getCpu();

        String oldVal;

        //PC
        ImGui.text("PC: ");
        ImGui.sameLine();
        PC_RegisterText.set((Integer.toHexString(((int)cpu.getPc())  & 0xffff) ));
        oldVal = PC_RegisterText.get();
        ImGui.inputText( " ", PC_RegisterText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

        if(oldVal != PC_RegisterText.get())
            systemBus.getCpu().setPc(PC_RegisterText.get().length() <= 0 ? 0 : (char) Long.parseLong(PC_RegisterText.get(), 16));

        //A
        ImGui.text("A: ");
        ImGui.sameLine();
        A_RegisterText.set((Integer.toHexString(((int)cpu.getA())  & 0xff) ));
        oldVal = A_RegisterText.get();
        ImGui.inputText( "  ", A_RegisterText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

        if(oldVal != A_RegisterText.get())
            systemBus.getCpu().setA(A_RegisterText.get().length() <= 0 ? 0 : (byte) Long.parseLong(A_RegisterText.get(), 16));

        //x
        ImGui.text("X: ");
        ImGui.sameLine();
        X_RegisterText.set((Integer.toHexString(((int)cpu.getX())  & 0xff) ));
        oldVal = X_RegisterText.get();
        ImGui.inputText( "   ", X_RegisterText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

        if(oldVal != X_RegisterText.get())
            systemBus.getCpu().setX(X_RegisterText.get().length() <= 0 ? 0 : (byte) Long.parseLong(X_RegisterText.get(), 16));

        //Y
        ImGui.text("Y: ");
        ImGui.sameLine();
        Y_RegisterText.set((Integer.toHexString(((int)cpu.getY())  & 0xff) ));
        oldVal = Y_RegisterText.get();
        ImGui.inputText( "    ", Y_RegisterText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

        if(oldVal != Y_RegisterText.get())
            systemBus.getCpu().setY(Y_RegisterText.get().length() <= 0 ? 0 : (byte) Long.parseLong(Y_RegisterText.get(), 16));

        //Stack
        ImGui.text("Stack: ");
        ImGui.sameLine();
        S_RegisterText.set((Integer.toHexString(((int)cpu.getStackPointer())  & 0xffff) ));
        oldVal = S_RegisterText.get();
        ImGui.inputText( "     ", S_RegisterText, ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CharsHexadecimal);

        if(oldVal != S_RegisterText.get())
            systemBus.getCpu().setStackPointer(S_RegisterText.get().length() <= 0 ? 0 : (char) Long.parseLong(S_RegisterText.get(), 16));

                /*ImGui.text("Y: " + systemBus.getCpu().getY());
                ImGui.text("PC: " + (int)systemBus.getCpu().getPc());
                ImGui.text("Stack: " + (int)systemBus.getCpu().getStackPointer());*/

        //flags

        String[] flagNames = {"Z", "C", "N"};
        for(int i = 0; i < 3; i++)
        {
            flagsBool[i].set(cpu.getFlags()[i]);
            boolean old = cpu.getFlags()[i];
            ImGui.checkbox(flagNames[i], flagsBool[i]);

            if(old != flagsBool[i].get())
                systemBus.getCpu().setFlag(i, flagsBool[i].get());

            ImGui.sameLine();
        }

    }
}
