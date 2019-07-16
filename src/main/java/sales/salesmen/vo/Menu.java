package sales.salesmen.vo;

public class Menu {
    private String name;
    private String url;
    private String elementid;

    public Menu(String name, String url, String id) {
        this.elementid = id;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
