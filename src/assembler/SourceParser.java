package assembler;

import core.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SourceParser {
    private ArrayList<Instruction> instructions = new ArrayList<>();
    private byte[] bytes;

    public SourceParser(File src) throws FileNotFoundException {
        Scanner scan = new Scanner(src);

        while(scan.hasNext()){
            String s = scan.nextLine();
            s = s.split(";")[0];
            s = s.trim();
            if(s.equals("") || s.equals("\n")) continue;

            String[] isplit = s.split(" ");

            Instruction ins;
            if(isplit.length == 3)
                ins = new Instruction(isplit[0], isplit[1], isplit[2]);
            else {
                if(isplit[0].startsWith(":"))
                    ins = new Instruction(true, isplit[0]);
                else ins = new Instruction(false, isplit[0]);
            }

            instructions.add(ins);
        }

        bytes = new byte[getByteSize()];
    }

    private char getByteSize(){
        char byteSize = 0;
        for(Instruction i : instructions){
            byteSize += i.getByteCount();
        }

        return byteSize;
    }

    private HashMap<String, Character> labelDoc(){
        HashMap<String, Character> doc = new HashMap<>();
        char currentAddress = 0;

        for(Instruction i : instructions){
            if(i.label)
                doc.put(i.mnemonic, currentAddress);
            else currentAddress += i.getByteCount();
        }

        return doc;
    }

    public byte[] getByteTranslation(){
        HashMap<String, Character> doc = labelDoc();
        char pc = 0;

        for(Instruction i : instructions){
            if(!i.label){
                bytes[pc++] = i.opCode;
                if(i.m == Command.AddressingMode.IMPLIED) continue;
                else if(i.m == Command.AddressingMode.IMMEDIATE)
                    bytes[pc++] = i.parsedArgument();
                else{
                    char arg = i.parsedCharArgument(doc);
                    bytes[pc++] = (byte)(arg >> 8);
                    bytes[pc++] = (byte)(arg);
                }
            }
        }

        return bytes;
    }


}
