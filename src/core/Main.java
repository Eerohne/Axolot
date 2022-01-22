package core;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World");

        RAM mem = new RAM();

        mem.storeValue((byte)10, 'x');
        mem.storeAValue('x', 'y');

        Util.printBinary(mem.getValue('x'));
        Util.printBinary(mem.getValue((char)('y' + 1)));
        Util.printBinary(mem.getAValue('y'));

    }
}
