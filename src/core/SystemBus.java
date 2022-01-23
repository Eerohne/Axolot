package core;


public class SystemBus extends Device {
    CPU cpu;
    RAM ram;
    Terminal terminal;
    boolean halted = true;
    public boolean clockOnce = false;

    public SystemBus(){
        ram = new RAM();
        cpu = new CPU(this);
        terminal = new Terminal(this);
    }

    @Override
    public void clock() {
        if(!halted) {
            cpu.clock();
            terminal.clock();
        }
        else if(clockOnce)
        {
            clockOnce = false;
            cpu.clock();
            terminal.clock();
        }
    }

    public RAM getRam() {
        return ram;
    }

    public CPU getCpu() {
        return cpu;
    }

    public Terminal getTerminal() {
        return terminal;
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

    @Override
    public void reset()
    {
        this.halted = true;
        cpu.reset();
        terminal.reset();
    }
}
