package node;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
	
	/*@Relationship(type = "CREATED")
    private Anon creator = null;*/

    /*@Relationship(type = "CONTAINS")
    private List<Msg> msgs = null;*/

    public Chat() {}

    public Chat (String name) {
        this.name = name;
		//this.creator = creator;
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
	
    /*public Anon getCreator() {
        return creator;
    }

    public void setCreator(Anon creator) {
        this.creator = creator;
    }*/

    /*public List<Msg> getMsgs() {
        if (msgs == null) {
            msgs = new ArrayList<>();
        }
        return msgs;
    }

    public void setMsgs (List<Msg> msgs) {
		this.msgs=msgs;
    }
	
	public void addMsg(Msg msg){
        if (msgs == null) {
            msgs = new ArrayList<>();
        }
		msgs.add(msg);
	}*/

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
        Chat other = (Chat) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name)||id!=other.id)
            return false;
        return true;
    }
}