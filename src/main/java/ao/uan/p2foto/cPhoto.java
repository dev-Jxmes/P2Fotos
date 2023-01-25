package ao.uan.p2foto;

public class cPhoto {
    String title, link, creator;

    public cPhoto(String title, String link, String creator) {
        this.title = title;
        this.link = link;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
