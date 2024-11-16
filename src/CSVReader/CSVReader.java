package CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVReader {

    public void read(Integer lineas) {
        //TODO
        try (BufferedReader br = new BufferedReader(new FileReader("archivo.csv"))) {
            String line;
            for (int i =0; i < lineas; i ++) {
                line = br.readLine();
                String[] vals_str = line.split(",");
                // En el array vals_str estan los valores, en tipo String
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

}
