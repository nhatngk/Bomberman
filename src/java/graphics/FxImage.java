package graphics;

import com.bomberman.Main;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class FxImage {
    public static final int DEFAULT_SIZE = 16;
    public static final int SCALED_SIZE = DEFAULT_SIZE * 2;
    private String _path;
    public final int SIZE;
    public int[] _pixels;
    public BufferedImage image;

    public FxImage(String path, int size) {
        _path = path;
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();
    }

    public Image getImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if ( _pixels[x + y * SIZE] == 0xffff00ff) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return input;
    }

    private void load() {
        try {
            URL a = Main.class.getResource(_path);
            image = ImageIO.read(a);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, _pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
