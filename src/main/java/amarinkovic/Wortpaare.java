package amarinkovic;


public class Wortpaare {
    private String wort;
    private String bildURL;

    public Wortpaare(String bildURL, String wort) {
        this.wort = wort;
        this.bildURL = bildURL;
    }

    public String getWort() {
        return wort;
    }

    public String getBildURL() {
        return bildURL;
    }

    public void setWort(String wort) {
        if(!bildURL.endsWith(".png") && !bildURL.endsWith(".jpg") && !bildURL.endsWith(".jpeg")) {
            throw new IllegalArgumentException("URL is not an image!");
        }
    }

    public void setBildURL(String bildURL) {
        this.bildURL = bildURL;
    }
    public String toString() {
        return this.wort + ":" + this.bildURL;
    }
}
