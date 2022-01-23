package core;

import java.util.HashMap;

public class Command {

    private final static Command[] commands =
    {
        new Command("nop",  "No operation", "No operation defined",                                 AddressingMode.IMPLIED,     (byte)0x00),
        new Command("hlt",  "Halt",         "Stop the clock",                                       AddressingMode.IMPLIED,     (byte)0x01),
        new Command("add",  "Add",          "Adds the value stored in register A",                  AddressingMode.IMMEDIATE,   (byte)0x02),
        new Command("add",  "Add",          "Adds the value stored in register A",                  AddressingMode.ABSOLUTE,    (byte)0x03),
        new Command("add",  "Add",          "Adds the value stored in register A",                  AddressingMode.ABSOLUTEX,   (byte)0x04),
        new Command("sub",  "Subtract",     "Subtracts the value stored in register A",             AddressingMode.IMMEDIATE,   (byte)0x05),
        new Command("sub",  "Subtract",     "Subtracts the value stored in register A",             AddressingMode.ABSOLUTE,    (byte)0x06),
        new Command("sub",  "Subtract",     "Subtracts the value stored in register A",             AddressingMode.ABSOLUTEX,   (byte)0x07),
        new Command("lda",  "Load to A",    "Loads the parameter into register A",                  AddressingMode.IMMEDIATE,   (byte)0x08),
        new Command("lda",  "Load to A",    "Loads the parameter into register A",                  AddressingMode.ABSOLUTE,    (byte)0x09),
        new Command("lda",  "Load to A",    "Loads the parameter into register A",                  AddressingMode.ABSOLUTEX,   (byte)0x0A),
        new Command("ldx",  "Load to X",    "Loads the parameter into register X",                  AddressingMode.IMMEDIATE,   (byte)0x0B),
        new Command("ldx",  "Load to X",    "Loads the parameter into register X",                  AddressingMode.ABSOLUTE,    (byte)0x0C),
        new Command("ldx",  "Load to X",    "Loads the parameter into register X",                  AddressingMode.ABSOLUTEX,   (byte)0x0D),
        new Command("ldy",  "Load to Y",    "Loads the parameter into register Y",                  AddressingMode.IMMEDIATE,   (byte)0x0E),
        new Command("ldy",  "Load to Y",    "Loads the parameter into register Y",                  AddressingMode.ABSOLUTE,    (byte)0x0F),
        new Command("ldy",  "Load to Y",    "Loads the parameter into register Y",                  AddressingMode.ABSOLUTEX,   (byte)0x10),
        new Command("sta",  "Store A",      "Stores the value in A into the given address",         AddressingMode.ABSOLUTE,    (byte)0x11),
        new Command("sta",  "Store A",      "Stores the value in A into the given address",         AddressingMode.ABSOLUTEX,   (byte)0x12),
        new Command("stx",  "Store X",      "Stores the value in X into the given address",         AddressingMode.ABSOLUTE,    (byte)0x13),
        new Command("stx",  "Store X",      "Stores the value in X into the given address",         AddressingMode.ABSOLUTEX,   (byte)0x14),
        new Command("sty",  "Store Y",      "Stores the value in Y into the given address",         AddressingMode.ABSOLUTE,    (byte)0x15),
        new Command("sty",  "Store Y",      "Stores the value in Y into the given address",         AddressingMode.ABSOLUTEX,   (byte)0x16),
        new Command("inc",  "Increment A",  "Increments register A by 1",                           AddressingMode.IMPLIED,     (byte)0x17),
        new Command("inx",  "Increment X",  "Increments register X by 1",                           AddressingMode.IMPLIED,     (byte)0x18),
        new Command("iny",  "Increment Y",  "Increments register Y by 1",                           AddressingMode.IMPLIED,     (byte)0x19),
        new Command("dec",  "Decrement A",  "Decrements register A by 1",                           AddressingMode.IMPLIED,     (byte)0x1A),
        new Command("dex",  "Decrement X",  "Decrements register X by 1",                           AddressingMode.IMPLIED,     (byte)0x1B),
        new Command("dey",  "Decrement Y",  "Decrements register Y by 1",                           AddressingMode.IMPLIED,     (byte)0x1C),
        new Command("shr",  "Shift Right",  "Shift the bits inside register A to the right",        AddressingMode.IMPLIED,     (byte)0x1D),
        new Command("shl",  "Shift Left",   "Shift the bits inside register A to the left",         AddressingMode.IMPLIED,     (byte)0x1E),
        new Command("and",  "AND Operator", "AND operation between register A and the given value", AddressingMode.IMMEDIATE,   (byte)0x1F),
        new Command("or",   "OR Operator",  "OR operation between register A and the given value",  AddressingMode.IMMEDIATE,   (byte)0x20),
        new Command("xor",  "XOR Operator", "XOR operation between register A and the given value", AddressingMode.IMMEDIATE,   (byte)0x21),
        new Command("pha",  "Push A",       "Push A to the front of the stack",                     AddressingMode.IMPLIED,     (byte)0x22),
        new Command("phx",  "Push X",       "Push X to the front of the stack",                     AddressingMode.IMPLIED,     (byte)0x23),
        new Command("pla",  "Pull A",       "Pull the top value of the stack into A",               AddressingMode.IMPLIED,     (byte)0x24),
        new Command("plx",  "Pull X",       "Pull the top value of the stack into X",               AddressingMode.IMPLIED,     (byte)0x25),
        new Command("tax",  "Transfer A X", "Transfers the value of A to X",                        AddressingMode.IMPLIED,     (byte)0x26),
        new Command("tay",  "Transfer A Y", "Transfers the value of A to Y",                        AddressingMode.IMPLIED,     (byte)0x27),
        new Command("txa",  "Transfer X A", "Transfers the value of X to A",                        AddressingMode.IMPLIED,     (byte)0x28),
        new Command("tya",  "Transfer Y A", "Transfers the value of Y to A",                        AddressingMode.IMPLIED,     (byte)0x29),
        new Command("jmp",  "Jump",         "Jumps to the given address parameter",                 AddressingMode.ABSOLUTE,    (byte)0x2A),
        new Command("jmp",  "Jump",         "Jumps to the given address parameter",                 AddressingMode.ABSOLUTEX,   (byte)0x2B),
        new Command("jsr",  "Jump to subroutine","Jumps to the given subroutine",                   AddressingMode.ABSOLUTE,    (byte)0x2C),
        new Command("jsr",  "Jump to subroutine","Jumps to the given subroutine",                   AddressingMode.ABSOLUTEX,   (byte)0x2D),
        new Command("rsr",  "Return from subroutine","Returns to the address before jumping",       AddressingMode.ABSOLUTEX,   (byte)0x2E),
        new Command("bnz",  "Branch if not zero","Branch if the zero flag is false",                AddressingMode.ABSOLUTE,    (byte)0x2F),
        new Command("bzr",  "Branch if zero","Branch if the zero flag is true",                     AddressingMode.ABSOLUTE,    (byte)0x30),
        new Command("bcr",  "Branch if carry","Branch if the carry flag is true",                   AddressingMode.ABSOLUTE,    (byte)0x31),
        new Command("bng",  "Branch if negative","Branch if the negative flag is true",             AddressingMode.ABSOLUTE,    (byte)0x32),
        new Command("clf",  "Clear flags",  "Sets all flags to false",                              AddressingMode.IMPLIED,     (byte)0x33),
    };

    private static final HashMap<Byte, Command> commandDict = createCommandDictionnary();

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

    public enum AddressingMode{
        IMPLIED, IMMEDIATE, ABSOLUTE, ABSOLUTEX
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public AddressingMode getMode() {
        return mode;
    }

    public byte getOpcode() {
        return opcode;
    }

    public static Command getCommandDetails(byte opcode){
        return commandDict.get(opcode);
    }

    public static byte getCommandOpcode(String mnemonic, AddressingMode mode){
        for(int i = 0; i<commands.length; i++){
            if(commands[i].mnemonic.equals(mnemonic) && commands[i].mode.equals(mode))
                return commands[i].opcode;
        }

        return 0;
    }

    private static HashMap<Byte, Command> createCommandDictionnary(){
        HashMap<Byte, Command> commandHashMap = new HashMap<Byte, Command>(commands.length);

        for (Command command : commands){
            commandHashMap.put(command.getOpcode(), command);
        }

        return commandHashMap;
    }


    @Override
    public String toString() {
        return "Command{" +
                "mnemonic='" + mnemonic + '\'' +
                ", name='" + name + '\'' +
                ", mode=" + mode +
                ", opcode=" + opcode +
                '}';
    }
}
