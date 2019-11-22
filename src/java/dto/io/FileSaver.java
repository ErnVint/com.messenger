package dto.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver implements IHistorySaver {

    private final File stream;

    public FileSaver(File stream) {
        this.stream = stream;
    }

    @Override
    public void println(String s) {
        //....
    }

    public void printToFile (String string) throws IOException {
        FileWriter fw = new FileWriter(this.stream,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(string+"\n");
        bw.close();

    }
}