package core;

import ui.UILayer;

public class Main {
    public static void main(String[] args) {


        SystemBus systemBus = new SystemBus();
        UILayer uiLayer = new UILayer(systemBus);

        systemBus.getRam().storeValue((byte)0x40, (char)0x00);
        systemBus.clock();

        while(uiLayer.running)
        {
            systemBus.clock();
            //systemBus.cpu.
            uiLayer.updateUI();
        }

        uiLayer.destroy();

    }
}
