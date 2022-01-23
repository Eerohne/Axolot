package core;

import ui.UILayer;

public class Main {
    public static void main(String[] args) {


        SystemBus systemBus = new SystemBus();
        UILayer uiLayer = new UILayer(systemBus);

        systemBus.getRam().storeValue((byte)0x40, (char)0x00);
        systemBus.clock();

        byte i = 0;
        while(uiLayer.running)
        {
            //systemBus.getCpu().setA( (i++) );
            systemBus.clock();
            uiLayer.updateUI();
        }

        uiLayer.destroy();

    }
}
