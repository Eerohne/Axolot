package core;

public class Command {

    public final static Command[] commands =
    {
        new Command("nop",  "No operation", "No operation defined",                                 AddressingMode.IMPLIED,   (byte)0x00),
        new Command("halt", "Halt",         "Stop the clock",                                       AddressingMode.IMPLIED,   (byte)0x01),
        new Command("add",  "Add",          "Adds the value stored in register A",                  AddressingMode.IMMEDIATE, (byte)0x02),
        new Command("add",  "Add",          "Adds the value stored in register A",                  AddressingMode.ABSOLUTE,  (byte)0x03),
        new Command("add",  "Add",          "Adds the value stored in register A",                  AddressingMode.ABSOLUTEX, (byte)0x04),
        new Command("sub",  "Subtract",     "Subtracts the value stored in register A",             AddressingMode.IMMEDIATE, (byte)0x05),
        new Command("sub",  "Subtract",     "Subtracts the value stored in register A",             AddressingMode.ABSOLUTE,  (byte)0x06),
        new Command("sub",  "Subtract",     "Subtracts the value stored in register A",             AddressingMode.ABSOLUTEX, (byte)0x07),
        new Command("lda",  "Load to A",    "Loads the parameter into register A",                  AddressingMode.IMMEDIATE, (byte)0x08),
        new Command("lda",  "Load to A",    "Loads the parameter into register A",                  AddressingMode.ABSOLUTE,  (byte)0x09),
        new Command("lda",  "Load to A",    "Loads the parameter into register A",                  AddressingMode.ABSOLUTEX, (byte)0x0A),
        new Command("ldx",  "Load to X",    "Loads the parameter into register X",                  AddressingMode.IMMEDIATE, (byte)0x0B),
        new Command("ldx",  "Load to X",    "Loads the parameter into register X",                  AddressingMode.ABSOLUTE,  (byte)0x0C),
        new Command("ldx",  "Load to X",    "Loads the parameter into register X",                  AddressingMode.ABSOLUTEX, (byte)0x0D),
        new Command("ldy",  "Load to Y",    "Loads the parameter into register Y",                  AddressingMode.IMMEDIATE, (byte)0x0E),
        new Command("ldy",  "Load to Y",    "Loads the parameter into register Y",                  AddressingMode.ABSOLUTE,  (byte)0x0F),
        new Command("ldy",  "Load to Y",    "Loads the parameter into register Y",                  AddressingMode.ABSOLUTEX, (byte)0x10),
        new Command("sta",  "Store A",      "Stores the value in A into the given address",         AddressingMode.ABSOLUTE,  (byte)0x11),
        new Command("sta",  "Store A",      "Stores the value in A into the given address",         AddressingMode.ABSOLUTEX, (byte)0x12),
        new Command("stx",  "Store X",      "Stores the value in X into the given address",         AddressingMode.ABSOLUTE,  (byte)0x13),
        new Command("stx",  "Store X",      "Stores the value in X into the given address",         AddressingMode.ABSOLUTEX, (byte)0x14),
        new Command("sty",  "Store Y",      "Stores the value in Y into the given address",         AddressingMode.ABSOLUTE,  (byte)0x15),
        new Command("sty",  "Store Y",      "Stores the value in Y into the given address",         AddressingMode.ABSOLUTEX, (byte)0x16),
        new Command("inc",  "Increment A",  "Increments register A by 1",                           AddressingMode.IMPLIED,   (byte)0x17),
        new Command("inx",  "Increment X",  "Increments register X by 1",                           AddressingMode.IMPLIED,   (byte)0x18),
        new Command("iny",  "Increment Y",  "Increments register Y by 1",                           AddressingMode.IMPLIED,   (byte)0x19),
        new Command("dec",  "Decrement A",  "Decrements register A by 1",                           AddressingMode.IMPLIED,   (byte)0x1A),
        new Command("dex",  "Decrement X",  "Decrements register X by 1",                           AddressingMode.IMPLIED,   (byte)0x1B),
        new Command("dey",  "Decrement Y",  "Decrements register Y by 1",                           AddressingMode.IMPLIED,   (byte)0x1C),
        new Command("shr",  "Shift Right",  "Shift the bits inside register A to the right",        AddressingMode.IMPLIED,   (byte)0x1D),
        new Command("shl",  "Shift Left",   "Shift the bits inside register A to the left",         AddressingMode.IMPLIED,   (byte)0x1E),
        new Command("and",  "AND Operator", "AND operation between register A and the given value", AddressingMode.IMMEDIATE, (byte)0x1F),
        new Command("or",   "OR Operator",  "OR operation between register A and the given value",  AddressingMode.IMMEDIATE, (byte)0x20),
        new Command("xor",  "XOR Operator", "XOR operation between register A and the given value", AddressingMode.IMMEDIATE, (byte)0x21)
    };

    private String mnemonic;
    private String name;
    private String desc;
    private AddressingMode mode;
    private byte opcode;

    public Command(String mnemonic, String name, String desc, AddressingMode mode, byte opcode){
        this.mnemonic = mnemonic;
        this.name = name;
        this.desc = desc;
        this.mode = mode;
        this.opcode = opcode;
    }

    enum AddressingMode{
        IMPLIED, IMMEDIATE, ABSOLUTE, ABSOLUTEX
    }
}
