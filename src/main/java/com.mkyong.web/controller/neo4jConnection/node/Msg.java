package node;

import org.neo4j.ogm.annotation.*;

@NodeEntity
public class Msg {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
	
    private String author;
	
	private String time;
	
	/*@Relationship(type = "WRITTEN")
    private Anon writer = null;*/

    public Msg() {}

    public Msg (String text, String time) {
        this.text = text;
		this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
	
    /*public Anon getWriter() {
        return writer;
    }

    public void setWriter(Anon writer) {
        this.writer = writer;
    }*/

    public String getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        Msg other = (Msg) obj;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text)||id!=other.id)
            return false;
        return true;
    }
}