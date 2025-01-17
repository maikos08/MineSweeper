package software.ulpgc.MineSweeper.arquitecture.io;

import software.ulpgc.MineSweeper.arquitecture.model.Image;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileImageLoader implements ImageLoader {
    private final Map<String, ImageIcon> images;
    private final File folder;
    private final FileImageDeserializer deserializer;


    public FileImageLoader(String path) {
        this.folder = new File(path);
        this.deserializer = new FileImageDeserializer();
        this.images = new HashMap<>();
        validateDirectory(folder);
    }

    @Override
    public Map<String, ImageIcon> load() {
        loadImages(folder);
        return images;
    }

    private void loadImages(File directory) {
        File[] files = directory.listFiles((dir, name) -> name.matches(".*\\.(png|jpg|webp)$"));
        if (files == null || files.length == 0) {
            throw new RuntimeException("No images found in directory: " + directory.getAbsolutePath());
        }
        for (File file : files) {
            processImage(file);
        }
    }

    private void processImage(File file) {
        try {
            byte[] content = Files.readAllBytes(file.toPath());
            Image image = new Image() {
                @Override
                public byte[] content(){
                    return content;
                }

                @Override
                public String toString() {
                    return file.getAbsolutePath();
                }

            };
            images.put(file.getName(), deserializer.deserialize(image.content()));
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file: " + file.getAbsolutePath(), e);
        }
    }

    private void validateDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new RuntimeException("Invalid folder: " + directory.getAbsolutePath());
        }
    }
}
