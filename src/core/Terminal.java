package core;

public class Terminal extends Device{
    private SystemBus bus;

    final char EXECUTION_FLAG = 0xFE00;
    final char ADDRESS_REGISTER = 0xFE01;

    private String screenBuffer = "";
    /*
    *Terminal memory registers:
    * 0xFE00 : EXECUTION FLAG (0 -> nothing, 1 -> draw)
    * 0xFE01-0XFE02 : ADDRESS REGISTER (address to read characters from)
    */

    /*
    *The terminal will wait until it read a 0x1 in the EXECUTION FLAG
    *When that happens, it will start reading character byte starting from address defined in the ADDRESS REGISTER
    * The terminal only stops when it finds a null (0x0) character
    * */

    public Terminal(SystemBus systemBus)
    {
        this.bus = systemBus;
        screenBuffer+= ">";
    }


    @Override
    void clock() {
        if(bus.getRam().getValue(EXECUTION_FLAG) == 0x1)
        {
            //reset flag
            bus.getRam().storeValue((byte)0x0, EXECUTION_FLAG);

            char currentAddress = bus.getRam().getAValue(ADDRESS_REGISTER);
            while(true) //until we find a 0x0 char
            {
                byte fetched = bus.getRam().getValue(currentAddress);
                if(fetched == 0x0)
                    break;
                else {
                    System.out.println(fetched);
                    screenBuffer += (char)fetched;
                    currentAddress++;
                }
            }

            screenBuffer+= "\n>";

        }
    }

    public String getScreenBuffer() { return this.screenBuffer; }

    @Override
    void reset() {
        bus.getRam().storeValue((byte)0x0, EXECUTION_FLAG);
        screenBuffer = ">";
    }
}
