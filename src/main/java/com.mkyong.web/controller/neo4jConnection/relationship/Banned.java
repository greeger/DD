package relationship;

import node.*;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "BANNED")
public class Banned {

	@Id
    @GeneratedValue
    private Long id;

    /**
     * If divorced, what year was the divorce finalized
     */
    //private Integer yearDivorced;

    /**
     * the year married
     */
    //private Integer yearMarried;

    /**
     * the wife in the marriage
     */
    @StartNode
    private Anon anon;

    /**
     * the husband in the marriage
     */
    @EndNode
    private Chat chat;

    /**
     * Constructor
     */
    public Banned () {}

    /**
     * Constructor
     * @param wife the wife in the marriage
     * @param husband the husband in the marriage
     * @param yearMarried the year married
     * @param yearDivorced the year divorced
     */
    public Banned (Anon anon,
                    Chat chat
					//,int yearMarried,
                    //Integer yearDivorced
					) {
        this.anon = anon;
        this.chat = chat;
        /*this.yearMarried = yearMarried;
        if (yearDivorced != null) {
            this.yearDivorced = yearDivorced;
        }*/
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Integer getYearDivorced() {
        return yearDivorced;
    }

    public void setYearDivorced(Integer yearDivorced) {
        this.yearDivorced = yearDivorced;
    }

    public Integer getYearMarried() {
        return yearMarried;
    }

    public void setYearMarried(Integer yearMarried) {
        this.yearMarried = yearMarried;
    }*/

    public Anon getAnon() {
        return anon;
    }

    public void setAnon(Anon anon) {
        this.anon = anon;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Banned banned = (Banned) o;

        /*if (yearDivorced != null ? !yearDivorced.equals(married.yearDivorced) : married.yearDivorced != null)
            return false;
        if (!yearMarried.equals(married.yearMarried)) return false;*/
        if (!anon.equals(banned.anon)) return false;
        return chat.equals(banned.chat);

    }

    @Override
    public int hashCode() {
        //int result = yearDivorced != null ? yearDivorced.hashCode() : 0;
        //result = 31 * result + yearMarried.hashCode();
		int result = anon != null ? anon.hashCode() : 0;
        //result = 31 * result + wife.hashCode();
        result = 31 * result + chat.hashCode();
        return result;
    }
}
