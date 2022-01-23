package core;

import assembler.Asssembler;
import ui.UILayer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        SystemBus systemBus = new SystemBus();
        UILayer uiLayer = new UILayer(systemBus);

        Asssembler as = new Asssembler(new File("bin/a.txt"));

        systemBus.getRam().loadProgram(as.getProgram());

        while(uiLayer.running)
        {
            systemBus.clock();
            //System.out.println(systemBus.getRam().getValue((char)0xFF01));
            //System.out.println(systemBus.getRam().getValue((char)0xFF01));
            //systemBus.cpu.
            uiLayer.updateUI();
        }

        uiLayer.destroy();

    }
}
