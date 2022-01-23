package core;


public class SystemBus extends Device {
    CPU cpu;
    RAM ram;
    boolean halted = false;

    public SystemBus(){
        ram = new RAM();
        cpu = new CPU(this);
    }

    @Override
    public void clock() {
        if(!halted)
            cpu.clock();
    }

    public RAM getRam() {
        return ram;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setHalted(boolean halted) {
        this.halted = halted;
    }
}
