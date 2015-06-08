package demo;

public class Doc {

    private String title;

    private String data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Doc(String title, String data) {
        this.title = title;
        this.data = data;
    }
}
