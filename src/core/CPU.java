package core;

public class CPU extends Device{
    private SystemBus bus;

    private char pc = (char)0;
    private byte A;
    private byte X;
    private byte Y;

    private byte fetchedValue;
    private char tempAddress;

    public CPU(SystemBus bus){
        this.bus = bus;
    }

    @Override
    public void clock() {

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

    private void add() { A += fetchedValue; }

    private void sub() { A -= fetchedValue; }

    private void lda() { A = fetchedValue; }

    private void ldx() { X = fetchedValue; }

    private void ldy() { Y = fetchedValue; }

    private void sta() { bus.getRam().storeValue(A, tempAddress); }

    private void stx() { bus.getRam().storeValue(X, tempAddress); }

    private void sty() { bus.getRam().storeValue(Y, tempAddress); }

    private void inc() { A++; }

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
}
