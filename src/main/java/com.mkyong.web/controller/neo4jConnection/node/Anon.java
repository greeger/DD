package node;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Anon {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
	
    private String password;
	
	private boolean isAdmin;

    /*@Relationship(type = "MEMBER")
    private List<Chat> chats = null;*/

    public Anon() {}

    public Anon (String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*
    public List<Chat> getChats() {
        if (chats == null) {
            chats = new ArrayList<>();
        }
        return chats;
    }
	
	public void addChat(Chat chat){
        if (chats == null) {
            chats = new ArrayList<>();
        }
		chats.add(chat);
	}
	
	public void removeChat(Chat chat){
        if (chats == null) {
            chats = new ArrayList<>();
        }
		chats.remove(chat);
	}
	*/
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin=isAdmin;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Anon other = (Anon) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name)||id!=other.id)
            return false;
        return true;
    }
}