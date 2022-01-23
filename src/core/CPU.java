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
            case "halt":    halt();                     break;
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
        }

        pc++;
    }

    private void zero(byte r){ flags[0] = (r==0); }
    private void carry(){ flags[1] = (A==0); }
    private void neg() {  }

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
        tempAddress = bus.getRam().getAValue((char)(++pc + X));
        fetchedValue = bus.getRam().getValue(tempAddress);
        pc++;
    }

    private void halt() {  }

    public void add() { byte tmpA = A; A += fetchedValue; flags[1] = (A < tmpA);}
    public void sub() { byte tmpA = A; A -= fetchedValue; flags[1] = (A > tmpA);}

    public void lda() { A = fetchedValue; }
    public void ldx() { X = fetchedValue; }
    public void ldy() { Y = fetchedValue; }

    public void sta() { bus.getRam().storeValue(A, tempAddress); }
    public void stx() { bus.getRam().storeValue(X, tempAddress); }
    public void sty() { bus.getRam().storeValue(Y, tempAddress); }

    public void inc() { byte tmpA = A; A++; flags[1] = (A < tmpA);}
    public void inx() { X++; }
    public void iny() { Y++; }

    public void dec() { A--; }
    public void dex() { X--; }
    public void dey() { Y--; }

    public void shr() { A >>= 1; }
    public void shl() { A <<= 1; }

    public void and() { A &= fetchedValue; }
    public void or()  { A |= fetchedValue; }
    public void xor() { A ^= fetchedValue; }

    public void pha() { bus.getRam().storeValue(A, stackPointer++); }
    public void phx() { bus.getRam().storeValue(X, stackPointer++); }

    public void pla() { A = bus.getRam().getValue(stackPointer--); }
    public void plx() { X = bus.getRam().getValue(stackPointer--); }

    public void tax() { X = A; }
    public void tay() { Y = A; }
    public void txa() { A = X; }
    public void tya() { A = Y; }

    public char getPc() {
        return pc;
    }

    public byte getA() {
        return A;
    }

    public byte getX() {
        return X;
    }

    public byte getY() {
        return Y;
    }

    public void setPc(char pc) {
        this.pc = pc;
    }

    public void setA(byte a) {
        A = a;
    }

    public void setX(byte x) {
        X = x;
    }

    public void setY(byte y) {
        Y = y;
    }

    public void setStackPointer(char stackPointer) {
        this.stackPointer = stackPointer;
    }
}
