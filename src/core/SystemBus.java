package core;


public class SystemBus extends Device {
    CPU cpu;
    RAM ram;
    boolean halted = true;
    public boolean clockOnce = false;

    public SystemBus(){
        ram = new RAM();
        cpu = new CPU(this);
    }

    @Override
    public void clock() {
        if(!halted)
            cpu.clock();
        else if(clockOnce)
        {
            clockOnce = false;
            cpu.clock();
        }
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

    public boolean isHalted(){return this.halted;}

    public void clockOnce()
    {
        if(this.halted)
            clockOnce = true;
    }
}
