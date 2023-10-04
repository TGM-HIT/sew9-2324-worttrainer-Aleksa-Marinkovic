package amarinkovic;

public interface SaveType {
    void safe(String path, Worttrainer worttrainer);
    Worttrainer load(String path);
}
