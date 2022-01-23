package assembler;

import core.Command;

import java.util.HashMap;

public class Instruction {
    boolean label = false;
    String mnemonic;
    String mode;
    String argument;

    Command.AddressingMode m  = Command.AddressingMode.IMPLIED;

    byte byteCount;
    byte opCode;

    public Instruction(String mnemonic, String mode, String argument) {
        this.mnemonic = mnemonic;
        this.mode = mode;
        this.argument = argument;

        switch (mode){
            case ",": m = Command.AddressingMode.IMMEDIATE; break;
            case "#": m = Command.AddressingMode.ABSOLUTE;  break;
            case "x": m = Command.AddressingMode.ABSOLUTEX; break;
        }

        if(m == Command.AddressingMode.IMMEDIATE) byteCount += 2;
        else byteCount += 3;

        opCode = Command.getCommandOpcode(mnemonic, m);
    }

    public Instruction(boolean label, String mnemonic){
        this.label = label;
        if(label)
            this.mnemonic = mnemonic.split(":")[1];
        else {
            this.mnemonic = mnemonic;
            byteCount++;
            opCode = Command.getCommandOpcode(mnemonic, m);
        }
    }

    public byte getByteCount() {
        return byteCount;
    }

    byte parsedArgument(){
        if(argument.startsWith("$")){
            return Byte.parseByte(argument.replace("$", ""), 16);
        }
        return Byte.parseByte(argument);
    }

    char parsedCharArgument(HashMap<String, Character> doc){
        if(argument.startsWith("$"))
            return (char)Integer.parseInt(argument.replace("$", ""), 16);
        else if(doc.containsKey(argument))
            return doc.get(argument);
        else return (char)Short.parseShort(argument);
    }
}
