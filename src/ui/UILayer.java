package ui;

import core.CPU;
import core.SystemBus;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class UILayer implements IEventListener{

    public boolean running = true;
    private Window window;
    private SystemBus systemBus;

    ImGuiImplGlfw ImGuiGlfw;
    ImGuiImplGl3 ImGuiGl;
    ImGuiIO ImGuiIO;

    public UILayer(SystemBus systemBus) {
        this.systemBus = systemBus;

        window = new Window("Axolotl Computer", 1280, 800, true, false);
        window.init(this);

        initImGui();
    }

    public void initImGui()
    {
        ImGui.createContext();

        ImGuiIO = ImGui.getIO();
        ImGuiIO.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        ImGuiIO.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        ImGuiIO.setConfigViewportsNoTaskBarIcon(true);


        long windowHandle = window.getSystemWindow();

        ImGuiGlfw = new ImGuiImplGlfw();
        ImGuiGlfw.init(windowHandle, true);

        ImGuiGl = new ImGuiImplGl3();
        ImGuiGl.init("#version 410");
    }

    public void ImGuibegin()
    {
        ImGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    static ImString A_RegisterText = new ImString("00");
    static ImString X_RegisterText = new ImString("00");
    static ImString Y_RegisterText = new ImString("00");
    static ImString PC_RegisterText = new ImString("0000");
    static ImString S_RegisterText = new ImString("0000");

    static ImBoolean[] flagsBool = {new ImBoolean(false), new ImBoolean(false), new ImBoolean(false)};


    public void updateUI()
    {
        window.clear();

        ImGuibegin();
        {
            boolean dockspaceOpen = true;
            boolean opt_fullscreen_persistant = true;
            boolean opt_fullscreen = opt_fullscreen_persistant;

            int window_flags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;


            if (opt_fullscreen)
            {
                ImGuiViewport viewport = ImGui.getMainViewport();
                ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY());
                ImGui.setNextWindowSize(viewport.getSizeX(), viewport.getSizeY());
                ImGui.setNextWindowViewport(viewport.getID());
                ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
                ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

                window_flags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove
                        | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

                window_flags |= ImGuiWindowFlags.NoBackground;
            }

            ImGui.begin("DockSpace Demo", new ImBoolean(true), window_flags);
            ImGui.popStyleVar(2);


            int dockspace_id = ImGui.getID("MyDockSpace");
            ImGui.dockSpace(dockspace_id);

            //APP UI HERE

            //CPU PANEL
            ImGui.begin("CPU");
            {
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

                //ImGui.text("PC: " + (int)systemBus.getCpu().getPc());

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
            ImGui.end();

            //MEM PANEL
            ImGui.begin("MEMORY");
            {

            }
            ImGui.end();



            //dockspace end
            ImGui.end();
        }
        ImGuiend();

        window.update();
    }

    public void ImGuiend(){

        ImGui.render();
        ImGuiGl.renderDrawData(ImGui.getDrawData());

        if(ImGuiIO.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    public void destroy()
    {
        window.close();

        ImGuiGl.dispose();
        ImGuiGlfw.dispose();
        ImGui.destroyContext();
    }

    @Override
    public void onCloseEvent() {
        this.running = false;
    }
}
