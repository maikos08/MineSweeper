package software.ulpgc.MineSweeper.arquitecture.io;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.*;
import java.io.ByteArrayInputStream;

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

