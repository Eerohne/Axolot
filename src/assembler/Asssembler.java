package assembler;

import core.RAM;

import java.io.File;
import java.io.IOException;

public class Asssembler {
    byte[] program;

    public Asssembler(File file){
        try{
            SourceParser srcParser = new SourceParser(file);
            program = srcParser.getByteTranslation();

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public byte[] getProgram(){
        return program;
    }
}
