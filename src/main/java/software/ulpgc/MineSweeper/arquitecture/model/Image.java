package software.ulpgc.MineSweeper.arquitecture.model;

import java.io.IOException;

public interface Image {
    byte[] content() throws IOException;

    java.awt.Image image();
}
