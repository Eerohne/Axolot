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
     * // 3 = bigger
     */
    private boolean[] flags = new boolean[3];

    private byte fetchedValue;
    private char tempAddress;

    public CPU(SystemBus bus){
        this.bus = bus;
    }

    @Override
    public void clock() {
        resetFlags();
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
            case "halt":    hlt();                     break;
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
        }

        pc++;
    }

    private void zero(byte r){ flags[0] = (r==0); }
    private void carry(){ flags[1] = (A==0); }
    private void neg() {  }
    private void resetFlags(){
        for (boolean flag : flags) flag = false;
    }

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

    private void hlt() {  }

    private void add() { byte tmpA = A; A += fetchedValue; flags[1] = (A < tmpA);}
    private void sub() { byte tmpA = A; A -= fetchedValue; flags[1] = (A > tmpA);}

    private void lda() { A = fetchedValue; }
    private void ldx() { X = fetchedValue; }
    private void ldy() { Y = fetchedValue; }

    private void sta() { bus.getRam().storeValue(A, tempAddress); }
    private void stx() { bus.getRam().storeValue(X, tempAddress); }
    private void sty() { bus.getRam().storeValue(Y, tempAddress); }

    private void inc() { byte tmpA = A; A++; flags[1] = (A < tmpA);}
    private void inx() { X++; }
    private void iny() { Y++; }

    private void dec() { A--; }
    private void dex() { X--; }
    private void dey() { Y--; }

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

    private void bnz(){}
    private void bzr(){}
    private void bcr(){}
    private void bng(){}

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
}
