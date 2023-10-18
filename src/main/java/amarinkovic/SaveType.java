package amarinkovic;

import java.io.IOException;

public interface SaveType {
    void save(String path, Worttrainer worttrainer) throws IOException;
    Worttrainer load(String path, Worttrainer worttrainer);
}
