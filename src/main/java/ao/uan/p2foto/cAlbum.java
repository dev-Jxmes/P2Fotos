package ao.uan.p2foto;

import java.util.ArrayList;

public class cAlbum {
    String name, creator;
    ArrayList <String> users;

    public cAlbum(String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
