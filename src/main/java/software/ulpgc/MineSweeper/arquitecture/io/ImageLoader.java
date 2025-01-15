package software.ulpgc.MineSweeper.arquitecture.io;

import software.ulpgc.MineSweeper.arquitecture.model.Image;
import java.util.Map;

public interface ImageLoader {
    Map<String, Image> load();
}
