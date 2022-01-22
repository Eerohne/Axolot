package ui;

import core.SystemBus;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.ImGui;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class UILayer {

    private Window window;
    private SystemBus systemBus;

    ImGuiImplGlfw ImGuiGlfw;
    ImGuiImplGl3 ImGuiGl;
    ImGuiIO ImGuiIO;

    public UILayer(SystemBus systemBus) {
        this.systemBus = systemBus;

        window = new Window("Axolotl Computer", 1280, 800, true, false);
        window.init();

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

    public void begin()
    {
        ImGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    public void updateUI()
    {
        begin();
        {
            ImGui.showDemoWindow();
        }
        end();
    }

    public void end(){

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
        ImGuiGl.dispose();
        ImGuiGlfw.dispose();
        ImGui.destroyContext();
    }
}
