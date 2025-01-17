package software.ulpgc.MineSweeper.arquitecture.io;

import javax.swing.*;

public class FileImageDeserializer implements ImageDeserializer {
    @Override
    public ImageIcon deserialize(byte[] bytes) {
        try {
            return new ImageIcon(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing image bytes", e);
        }
    }
}
