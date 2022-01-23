package core;


public class SystemBus extends Device {
    CPU cpu;
    RAM ram;

    public SystemBus(){
        ram = new RAM();
        cpu = new CPU(this);
    }

    @Override
    public void clock() {
        cpu.clock();
    }

    public RAM getRam() {
        return ram;
    }

    public CPU getCpu() {
        return cpu;
    }
}
