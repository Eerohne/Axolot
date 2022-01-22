package ui;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    protected String title;

    protected long window;

    protected int width;
    protected int height;

    protected boolean vSync;
    protected boolean debugMode = true;

    public Window(String title, int width, int height, boolean vSync, boolean debugMode){
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.debugMode = debugMode;
    }


    public void init() {
        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // For debugging purposes
        if(debugMode){
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        this.window = glfwCreateWindow(width,height,title,NULL,NULL);
        if(window == NULL){
            throw new RuntimeException("Failed to create Window");
        }

        //***CALLBACKS***//
        /*{
            setEventListener(listener);

            //Sets close callback
            glfwSetWindowCloseCallback(window, (window) -> {
                glfwSetWindowShouldClose(window, false); //set shouldClose to false, engine should decide that

                if (eventListener != null) {
                    eventListener.onEvent(new Event.WindowCloseEvent());
                }
            });

            //Sets focus callback
            glfwSetWindowFocusCallback(window, (window, focused) -> {
                if (focused)
                    eventListener.onEvent(new Event.WindowFocusEvent());
                else
                    eventListener.onEvent(new Event.WindowLostFocusEvent());
            });

            //Sets moved callback
            glfwSetWindowPosCallback(window, (window, x, y) -> {
                eventListener.onEvent(new Event.WindowMovedEvent(x, y));
            });

            //Sets resize callback
            glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
                this.width = width;
                this.height = height;

                glViewport(0, 0, width, height);

                eventListener.onEvent(new Event.WindowResizedEvent(width, height));
            });




            //Sets key callback
            glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
                Event keyEvent = null;

                switch (action) {
                    case GLFW_PRESS:
                        keyEvent = new Event.KeyPressedEvent(KeyCode.getKey(key), false);
                        break;
                    case GLFW_REPEAT:
                        keyEvent = new Event.KeyPressedEvent(KeyCode.getKey(key), true);
                        break;
                    case GLFW_RELEASE:
                        keyEvent = new Event.KeyReleasedEvent(KeyCode.getKey(key));
                        break;
                }
                eventListener.onEvent(keyEvent);

            });

            //Sets key typed callback
            glfwSetCharCallback(window, (window, c) -> {
                eventListener.onEvent(new Event.KeyTypedEvent(c));
            });

            //Sets mouse button callback
            glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {

                Event mouseButtonEvent = null;

                switch (action) {
                    case GLFW_PRESS:
                        mouseButtonEvent = new Event.MouseButtonPressedEvent(KeyCode.getKey(button));
                        break;
                    case GLFW_RELEASE:
                        mouseButtonEvent = new Event.MouseButtonReleasedEvent(KeyCode.getKey(button));
                        break;
                }
                eventListener.onEvent(mouseButtonEvent);
            });

            //Sets mouse moved callback
            glfwSetCursorPosCallback(window, (window, x, y) -> {
                eventListener.onEvent(new Event.MouseMovedEvent((float) x, (float) y));
            });

            //Sets mouse scroll callback
            glfwSetScrollCallback(window, (window, sx, sy) -> {
                eventListener.onEvent(new Event.MouseScrolledEvent((float) sx, (float) sy));
            });
        }*/

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        //Set vsync
        glfwSwapInterval(vSync ? 1 : 0);

        //Gives the window the ability to render using openGL on the current window
        GL.createCapabilities();

        // Debug Callback
        if(debugMode){
            GLUtil.setupDebugMessageCallback();
        }


        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1f);
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }


    public void close()
    {
        glfwDestroyWindow(window);
    }

    public void setDebugMode(boolean isDebugOn) {
        if(debugMode)
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        else
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_FALSE);
        GLUtil.setupDebugMessageCallback();
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public void setVSync(boolean vSync) {
        this.vSync = vSync;
    }

    public long getSystemWindow() {
        return window;
    }
}
