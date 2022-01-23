package core;

public class CPU extends Device{
    // Reference to System Bus
    private SystemBus bus;

    // Program Counter
    private char pc = (char)0;

    // Registry
    private byte A;
    private byte X;
    private byte Y;

    private char stackPointer = 0xFF00;

    /*
     * 0 = zero
     * 1 = carry
     * 2 = negative
     */
    private boolean[] flags = new boolean[3];

    private byte fetchedValue;
    private char tempAddress;

    public CPU(SystemBus bus){
        this.bus = bus;
    }

    @Override
    public void clock() {
        byte command = bus.getRam().getValue(pc);
        Command cmd = Command.getCommandDetails(command);
        getCommand(command);
        switch (cmd.getMode()){
            case IMMEDIATE:     fetchImmediate();   break;
            case ABSOLUTE:      fetchAbsolute();    break;
            case ABSOLUTEX:     fetchAbsoluteX();   break;
        }
        switch (cmd.getMnemonic()){
            case "nop" :                                break;
            case "hlt" :    hlt();                      break;
            case "add" :    add();  zero(A);            break;
            case "sub" :    sub();  zero(A);            break;
            case "lda" :    lda();  zero(A);            break;
            case "ldx" :    ldx();  zero(X);            break;
            case "ldy" :    ldy();  zero(Y);            break;
            case "sta" :    sta();                      break;
            case "stx" :    stx();                      break;
            case "sty" :    sty();                      break;
            case "inc" :    inc();  zero(A);            break;
            case "inx" :    inx();  zero(X);            break;
            case "iny" :    iny();  zero(Y);            break;
            case "dec" :    dec();  zero(A);            break;
            case "dex" :    dex();  zero(X);            break;
            case "dey" :    dey();  zero(Y);            break;
            case "shr" :    shr();  zero(A);            break;
            case "shl" :    shl();  zero(A);            break;
            case "and" :    and();  zero(A);            break;
            case "or"  :    or();   zero(A);            break;
            case "xor" :    xor();  zero(A);            break;
            case "pha" :    pha();                      break;
            case "phx" :    phx();                      break;
            case "pla" :    pla();  zero(A);            break;
            case "plx" :    plx();  zero(A);            break;
            case "tax" :    tax();  zero(A);            break;
            case "tay" :    tay();  zero(A);            break;
            case "txa" :    txa();  zero(A);            break;
            case "tya" :    tya();  zero(A);            break;
            case "jmp" :    jmp();                      break;
            case "jsr" :    jsr();                      break;
            case "rsr" :    rsr();                      break;
            case "bnz" :    bnz();                      break;
            case "bzr" :    bzr();                      break;
            case "bcr" :    bcr();                      break;
            case "bng" :    bng();                      break;
            case "clf" :    clf();                      break;
        }

        pc++;
    }

    private void zero(byte r){ flags[0] = (r==0); }

    public void getCommand(byte opcode){
        System.out.println(Command.getCommandDetails(opcode));
    }

    private void fetchImmediate(){
        fetchedValue = bus.getRam().getValue(++pc);
    }

    private void fetchAbsolute(){
        tempAddress = bus.getRam().getAValue(++pc);
        fetchedValue = bus.getRam().getValue(tempAddress);
        pc++;
    }

    private void fetchAbsoluteX(){
        tempAddress = (char)(bus.getRam().getAValue(++pc) + X);
        fetchedValue = bus.getRam().getValue(tempAddress);
        pc++;
    }

    private void hlt() { bus.setHalted(true); }

    private void add() { byte tmpA = A; A += fetchedValue; flags[1] = (A > tmpA);}
    private void sub() { byte tmpA = A; A -= fetchedValue; flags[2] = (A < tmpA);}

    private void lda() { A = fetchedValue; }
    private void ldx() { X = fetchedValue; }
    private void ldy() { Y = fetchedValue; }

    private void sta() { bus.getRam().storeValue(A, tempAddress); }
    private void stx() { bus.getRam().storeValue(X, tempAddress); }
    private void sty() { bus.getRam().storeValue(Y, tempAddress); }

    private void inc() { byte tmpA = A; A++; flags[1] = (A > tmpA); }
    private void inx() { byte tmpX = X; X++; flags[1] = (X > tmpX); }
    private void iny() { byte tmpY = Y; Y++; flags[1] = (Y > tmpY); }

    private void dec() { byte tmpA = A; A--; flags[2] = (A < tmpA); }
    private void dex() { byte tmpX = X; X--; flags[2] = (X < tmpX); }
    private void dey() { byte tmpY = Y; Y--; flags[2] = (Y < tmpY); }

    private void shr() { A >>= 1; }
    private void shl() { A <<= 1; }

    private void and() { A &= fetchedValue; }
    private void or()  { A |= fetchedValue; }
    private void xor() { A ^= fetchedValue; }

    private void pha() { bus.getRam().storeValue(A, stackPointer++); }
    private void phx() { bus.getRam().storeValue(X, stackPointer++); }

    private void pla() { A = bus.getRam().getValue(--stackPointer); }
    private void plx() { X = bus.getRam().getValue(--stackPointer); }

    private void tax() { X = A; }
    private void tay() { Y = A; }
    private void txa() { A = X; }
    private void tya() { A = Y; }

    private void jmp(){ pc = --tempAddress; }
    private void jsr(){ bus.getRam().storeAValue(pc, stackPointer++);  stackPointer++; pc = --tempAddress; }
    private void rsr(){ stackPointer -= 2; bus.getRam().getAValue(stackPointer); }

    private void bnz(){if(!flags[0]) jmp();}
    private void bzr(){if(flags[0])  jmp();}
    private void bcr(){if(flags[1])  jmp();}
    private void bng(){if(flags[2])  jmp();}

    private void clf(){ for (byte i = 0; i < flags.length; i++) flags[i] = false; }

    //////////////////////////////////////////////////////////////////

    public char getPc() { return pc; }
    public byte getA() { return A; }
    public byte getX() { return X; }
    public byte getY() { return Y; }
    public char getStackPointer() { return stackPointer; }
    public boolean[] getFlags() { return flags; }

    public void setPc(char pc) { this.pc = pc; }
    public void setA(byte a) { A = a; }
    public void setX(byte x) { X = x; }
    public void setY(byte y) { Y = y; }
    public void setStackPointer(char stackPointer) { this.stackPointer = stackPointer; }
    public void setFlags(boolean[] flags) { this.flags = flags; }
    public void setFlag(int index, boolean value) { this.flags[index] = value; }

    public void reset()
    {
        this.A = 0;
        this.X = 0;
        this.Y = 0;
        this.pc = 0;
        this.stackPointer = 0xFF00;
        this.clf();
    }
}
