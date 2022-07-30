package relationship;

import node.*;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CONTAINS")
public class Contains {

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
    private Chat chat;

    /**
     * the husband in the marriage
     */
    @EndNode
	private Msg msg;

    /**
     * Constructor
     */
    public Contains () {}

    /**
     * Constructor
     * @param wife the wife in the marriage
     * @param husband the husband in the marriage
     * @param yearMarried the year married
     * @param yearDivorced the year divorced
     */
    public Contains (Chat chat, Msg msg) {
        this.chat = chat;
        this.msg = msg;
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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contains contains = (Contains) o;

        /*if (yearDivorced != null ? !yearDivorced.equals(married.yearDivorced) : married.yearDivorced != null)
            return false;
        if (!yearMarried.equals(married.yearMarried)) return false;*/
        if (!msg.equals(contains.msg)) return false;
        return chat.equals(contains.chat);

    }

    @Override
    public int hashCode() {
        //int result = yearDivorced != null ? yearDivorced.hashCode() : 0;
        //result = 31 * result + yearMarried.hashCode();
		int result = msg != null ? msg.hashCode() : 0;
        //result = 31 * result + wife.hashCode();
        result = 31 * result + chat.hashCode();
        return result;
    }
}
