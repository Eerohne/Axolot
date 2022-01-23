package ui;

import core.CPU;
import core.SystemBus;
import imgui.*;
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

    private CPUPanel cpuPanel;
    private MemoryPanel memoryPanel;
    private ControlPanel controlPanel;
    private TerminalPanel terminalPanel;

    public UILayer(SystemBus systemBus) {
        this.systemBus = systemBus;

        window = new Window("Axolotl Computer", 1280, 800, true, false);
        window.init(this);

        this.cpuPanel = new CPUPanel();
        this.memoryPanel = new MemoryPanel();
        this.controlPanel = new ControlPanel();
        this.terminalPanel = new TerminalPanel();

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

        ImFontAtlas fontAtlas = ImGuiIO.getFonts();
        ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

        ImFont font = ImGuiIO.getFonts().addFontFromFileTTF("FreeMono.ttf", 16, fontConfig);

        ImGuiGl.init("#version 410");
        //ImGui.pushFont(font);
    }

    public void ImGuibegin()
    {
        ImGuiGlfw.newFrame();
        ImGui.newFrame();
    }



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
            cpuPanel.ImGuiRender(systemBus);
            ImGui.end();

            //MEM PANEL
            ImGui.begin("MEMORY");
            {
                memoryPanel.ImGuiRender(systemBus);
            }
            ImGui.end();

            //CONTROL PANEL
            ImGui.begin("CONTROL");
            {
                controlPanel.ImGuiRender(systemBus);
            }
            ImGui.end();

            //TERMINAL PANEL
            ImGui.begin("TERMINAL");
            {
                terminalPanel.ImGuiRender(systemBus);
            }
            ImGui.end();



            //dockspace end
            ImGui.end();

        }
        ImGuiend();
        //ImGui.popFont();

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
