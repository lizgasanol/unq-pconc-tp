package CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public List<List<String>> read(Integer lineas, String filename) {
        List<List<String>> resultado = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/Data/" + filename))) {
            String line;
            for (int i =0; i < lineas; i ++) {
                line = br.readLine();
                String[] vals_str = line.split(",");
                List<String> arreglo = new ArrayList<String>(List.of(vals_str));
                resultado.add(arreglo);
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}
