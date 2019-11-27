package dto.io;

import java.io.*;

public class FileSaver implements IHistorySaver {

    private final File stream;

    public FileSaver(File stream) {
        this.stream = stream;
    }

    @Override
    public void println(String s) {
        //....
    }

    public void printToFile(Object o) {
//        FileWriter fw = new FileWriter(this.stream,true);
//        BufferedWriter bw = new BufferedWriter(fw);
//        bw.write(string+"\n");
//        bw.close();
        try (ObjectOutputStream toFile = new ObjectOutputStream(new FileOutputStream(stream))) {
            toFile.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Object readFromFile() {
        Object o = null;
        try (ObjectInputStream fromFile = new ObjectInputStream(new FileInputStream(stream))) {
            o = fromFile.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return o;
        }
        return o;

    }
}