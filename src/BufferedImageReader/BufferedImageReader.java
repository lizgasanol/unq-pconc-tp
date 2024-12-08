package BufferedImageReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferedImageReader {
    public List<String> readImage(String filename) {
        try {
            File imgPath = new File("src/Data/" + filename);
            BufferedImage bufferedImage = ImageIO.read(imgPath);
            WritableRaster raster = bufferedImage.getRaster();
            byte[] datos_byte = ((DataBufferByte) raster.getDataBuffer()).getData();
            List<String> datos_string = new ArrayList<>();
            for(byte dato : datos_byte) {
                datos_string.add(String.valueOf(Byte.toUnsignedInt(dato)));
            }
            return datos_string;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeImage(List<String> data) {
        byte[] data_byte = new byte[784];
        data.removeFirst();
        for (int i = 0; i < data.size(); i++) {
            data_byte[i] = (byte) Integer.parseInt(data.get(i));
        }

        BufferedImage imagen = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        imagen.getRaster().setDataElements(0, 0, 28, 28, data_byte);
        File outputfile = new File("src/Data/muestra.png");
        try {
            ImageIO.write(imagen,"png", outputfile);
        } catch(IOException e) {
        }
    }
}
