package core;

import ui.UILayer;

public class Main {
    public static void main(String[] args) {


        SystemBus systemBus = new SystemBus();
        UILayer uiLayer = new UILayer(systemBus);

        systemBus.getRam().storeValue((byte)0x2d, (char)0xaa);
        systemBus.getRam().storeValue((byte)0x22, (char)0xab);
        systemBus.getRam().storeValue((byte)0x00, (char)0xac);
        systemBus.getRam().storeValue((byte)0x1a, (char)0xad);
        //systemBus.getRam().storeValue((byte)0x00, (char)0x03);
        systemBus.getRam().storeValue((byte)0x1a, (char)0x2204);


        byte i = 0;
        systemBus.getCpu().setA((byte)40);
        systemBus.getCpu().setX((byte)4);
        systemBus.getCpu().setY((byte)67);
        systemBus.getCpu().setPc((char)0xa9);

        while(uiLayer.running)
        {
            systemBus.clock();
            System.out.println(systemBus.getRam().getValue((char)0xFF01));
//            System.out.println(systemBus.getRam().getValue((char)0xFF01));
            //systemBus.cpu.
            uiLayer.updateUI();
        }

        uiLayer.destroy();

    }
}
