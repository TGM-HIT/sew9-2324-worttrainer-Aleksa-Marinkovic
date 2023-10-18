package amarinkovic;


public class Wortpaare {
    private String wort;
    private String bildURL;

    public Wortpaare(String bildURL, String wort) {
        setBildURL(bildURL);
        setWort(wort);

    }

    public String getWort() {
        return wort;
    }

    public String getBildURL() {
        return bildURL;
    }

    public void setBildURL(String bildURL) {
        if(!bildURL.endsWith(".png") && !bildURL.endsWith(".jpg") && !bildURL.endsWith(".jpeg")) {
            throw new IllegalArgumentException("URL is not an image!");
        }
        this.bildURL = bildURL;
    }

    public void setWort(String wort) {
        this.wort = wort;
    }
    public String toString() {
        return this.wort + ":" + this.bildURL;
    }
}
