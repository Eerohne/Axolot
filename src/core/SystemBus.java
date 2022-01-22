package core;


public class SystemBus implements IDevice {
    CPU cpu = new CPU();
    RAM ram = new RAM();

    @Override
    public void clock() {

    }
}
