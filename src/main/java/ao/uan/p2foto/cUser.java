package ao.uan.p2foto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class cUser {
    String username;
    String email;
    List <String> albums;

    public cUser() {
        this.username = "";
        this.email = "";
        this.albums = null;
    }

    public cUser(String username, String email) {
        this.username = username;
        this.email = email;
        this.albums = Collections.emptyList();
    }

    public cUser(String username, String email, List<String> albums) {
        this.username = username;
        this.email = email;
        this.albums = Collections.emptyList();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }
}
