package core;

import ui.UILayer;

public class Main {
    public static void main(String[] args) {


        SystemBus systemBus = new SystemBus();
        UILayer uiLayer = new UILayer(systemBus);

        while(uiLayer.running)
        {
            systemBus.clock();
            //uiLayer.updateUI();
        }

        uiLayer.destroy();

    }
}
