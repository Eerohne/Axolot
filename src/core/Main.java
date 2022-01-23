package core;

import ui.UILayer;

public class Main {
    public static void main(String[] args) {
        SystemBus systemBus = new SystemBus();
        UILayer uiLayer = new UILayer(systemBus);

        Util.printBinary((byte)-128);
        Util.printBinary((byte)-127);
        Util.printBinary((byte)-1);

        //systemBus.getRam().storeValue((byte)0x01, (char)0x00);

        systemBus.getRam().storeValue((byte)0x0B, (char)0x00);
        systemBus.getRam().storeValue((byte)0x01, (char)0x01);
        systemBus.getRam().storeValue((byte)0x0E, (char)0x02);
        systemBus.getRam().storeValue((byte)0x01, (char)0x03);
        systemBus.getRam().storeValue((byte)0x28, (char)0x04);
        systemBus.getRam().storeValue((byte)0x15, (char)0x05);
        systemBus.getRam().storeValue((byte)0x00, (char)0x06);
        systemBus.getRam().storeValue((byte)0x09, (char)0x07);
        systemBus.getRam().storeValue((byte)0x02, (char)0x08);
        systemBus.getRam().storeValue((byte)0x00, (char)0x09);
        systemBus.getRam().storeValue((byte)0x0C, (char)0x0A);
        systemBus.getRam().storeValue((byte)0x00, (char)0x0B);
        systemBus.getRam().storeValue((byte)0x09, (char)0x0C);
        systemBus.getRam().storeValue((byte)0x27, (char)0x0D);
        systemBus.getRam().storeValue((byte)0x11, (char)0x0E);
        systemBus.getRam().storeValue((byte)0x00, (char)0x0F);
        systemBus.getRam().storeValue((byte)0x1F, (char)0x10);
        systemBus.getRam().storeValue((byte)0x31, (char)0x11);
        systemBus.getRam().storeValue((byte)0x00, (char)0x12);
        systemBus.getRam().storeValue((byte)0x1D, (char)0x13);
        systemBus.getRam().storeValue((byte)0x08, (char)0x14);
        systemBus.getRam().storeValue((byte)0x1F, (char)0x15);
        systemBus.getRam().storeValue((byte)0x17, (char)0x16);
        systemBus.getRam().storeValue((byte)0x11, (char)0x17);
        systemBus.getRam().storeValue((byte)0x00, (char)0x18);
        systemBus.getRam().storeValue((byte)0x10, (char)0x19);
        systemBus.getRam().storeValue((byte)0x2A, (char)0x1A);
        systemBus.getRam().storeValue((byte)0x00, (char)0x1B);
        systemBus.getRam().storeValue((byte)0x04, (char)0x1C);
        systemBus.getRam().storeValue((byte)0x01, (char)0x1D);
        systemBus.getRam().storeValue((byte)0x01, (char)0x1E);
        //systemBus.getRam().storeValue((byte)0x22, (char)0xab);
        //systemBus.getRam().storeValue((byte)0x00, (char)0xac);
        //systemBus.getRam().storeValue((byte)0x1a, (char)0xad);
//        systemBus.getRam().storeValue((byte)0x00, (char)0x03);
        //systemBus.getRam().storeValue((byte)0x33, (char)0xaf);



        systemBus.getCpu().setA((byte)0);
        systemBus.getCpu().setX((byte)1);
        systemBus.getCpu().setY((byte)1);
        systemBus.getCpu().setPc((char)0);

        while(uiLayer.running)
        {
            systemBus.clock();
            //System.out.println(systemBus.getRam().getValue((char)0xFF01));
//            System.out.println(systemBus.getRam().getValue((char)0xFF01));
            //systemBus.cpu.
            uiLayer.updateUI();
        }

        uiLayer.destroy();

    }
}
