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

    static String myText = "4";

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

            ImGui.showDemoWindow();
            ImGui.begin("CPU");
            {
                CPU cpu = systemBus.getCpu();
                ImGui.text("A: " + systemBus.getCpu().getA());
                //ImGui.inputText("A: ", new ImString(myText), ImGuiInputTextFlags.CallbackResize);
                ImGui.text("X: " + systemBus.getCpu().getX());
                ImGui.text("Y: " + systemBus.getCpu().getY());
                ImGui.text("PC: " + (int)systemBus.getCpu().getPc());
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
