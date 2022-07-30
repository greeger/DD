package trash;

import node.*;
import relationship.*;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;

//import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class DDService {
	public static boolean isAdmin(Anon anon){
        return anon.isAdmin();
	}
	
	public static void deleteEverything(){
        Session session = Factory.getSessionFactory().openSession();
		session.purgeDatabase();
	}
	
	public static boolean isAnonNameUnique(String name){
        Session session = Factory.getSessionFactory().openSession();
		Filter filter = new Filter ("name", ComparisonOperator.EQUALS, name);
        return session.loadAll(Anon.class, filter).isEmpty();
	}
	
	public static Anon signUp(String name, String password){
		if(!isAnonNameUnique(name))
			return null;
		Anon anon=new Anon(name,password);
        Session session = Factory.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
		session.save(anon);
        txn.commit();
		return anon;
	}
	
	public static Anon logIn(String name, String password){
        Session session = Factory.getSessionFactory().openSession();
		Filter filter = new Filter ("name", ComparisonOperator.EQUALS, name);
		Iterator<Anon> iterator = session.loadAll(Anon.class, filter).iterator();
		if (!iterator.hasNext()) {
		  return null;
		}
		Anon anon=iterator.next();
		if(!anon.getPassword().equals(password)){
			return null;
		}
		return anon;
	}
	
	public static List<Chat> getAllChats() {
		Iterable<Chat> actualIterable = Factory.getSessionFactory().openSession().loadAll(Chat.class);
		List<Chat> actualList = new ArrayList<Chat>();
		actualIterable.forEach (one -> actualList.add(one));
		return actualList;
    }
	
	public static Chat createChat(String name, Anon anon) {
        Session session = Factory.getSessionFactory().openSession();
        Chat chat=new Chat(name);
        Transaction txn = session.beginTransaction();
		session.save(new Member(anon,chat));
		session.save(new Created(chat,anon));
		session.save(chat);
        txn.commit();
		return chat;
    }
	
	public static boolean isBanned(Anon anon, Chat chat){
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:BANNED]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		if (!iterator.hasNext()) {
		  return false;
		}
		else return true;
	}
	
	public static boolean isContains(Chat chat, Msg msg){
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
		params.put ("chatId", chat.getId());
        params.put ("msgId", msg.getId());
        String cypher = "MATCH (c:Chat)-[b:CONTAINS]->(a:Msg) WHERE id(a)=$msgId and id(c)=$chatId RETURN a;";		
		Iterator<Msg> iterator = session.query(Msg.class, cypher, params).iterator();
		if (!iterator.hasNext()) {
		  return false;
		}
		else return true;
	}
	
	public static boolean isCreated(Chat chat, Anon anon){
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
		params.put ("chatId", chat.getId());
        params.put ("anonId", anon.getId());
        String cypher = "MATCH (c:Chat)-[b:CREATED]->(a:Anon) WHERE id(a)=$anonId and id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		if (!iterator.hasNext()) {
		  return false;
		}
		else return true;
	}
	
	public static boolean isMember(Anon anon, Chat chat){
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:MEMBER]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		if (!iterator.hasNext()) {
		  return false;
		}
		else return true;
	}
	
	public static boolean isModerates(Anon anon, Chat chat){
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:MODERATES]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		if (!iterator.hasNext()) {
		  return false;
		}
		else return true;
	}
	
	public static boolean isWritten(Msg msg, Anon anon){
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
		params.put ("msgId", msg.getId());
        params.put ("anonId", anon.getId());
        String cypher = "MATCH (c:Msg)-[b:WRITTEN]->(a:Anon) WHERE id(a)=$anonId and id(c)=$msgId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		if (!iterator.hasNext()) {
		  return false;
		}
		else return true;
	}
	
	public static void setBanned(Anon anon, Chat chat, Anon moder) {
		if(!isMember(anon, chat)||isCreated(chat, anon)||isAdmin(anon))
			return;
		if(!(isCreated(chat, moder)||isAdmin(moder))&&!(isModerates(moder, chat)&&!isModerates(anon, chat)))
			return;
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
		Transaction txn = session.beginTransaction();
        String cypher = "MATCH (a:Anon)-[b:MODERATES]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";
		session.query(Chat.class, cypher, params).iterator();
        cypher = "MATCH (a:Anon)-[b:MEMBER]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";
		session.query(Chat.class, cypher, params).iterator();
		session.save(new Banned(anon,chat));
		txn.commit();
	}
	
	public static void setCreated(Chat chat, Anon anon) {
		if(isBanned(anon, chat))
			return;
        Session session = Factory.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
		if(isModerates(anon,chat)){
			Map<String, Object> params = new HashMap<>(2);
			params.put ("anonId", anon.getId());
			params.put ("chatId", chat.getId());
			String cypher = "MATCH (a:Anon)-[b:MODERATES]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";
			session.query(Chat.class, cypher, params).iterator();
		}
		session.save(new Created(chat,anon));
        txn.commit();
    }
	
	public static void setMember(Anon anon, Chat chat) {
		if(isBanned(anon, chat))
			return;
        Session session = Factory.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
		session.save(new Member(anon,chat));
        txn.commit();
    }
	
	public static void setModerates(Anon anon, Chat chat, Anon creator) {
		if(isCreated(chat,anon)||isBanned(anon, chat)||!isMember(anon, chat)||!(isCreated(chat, creator)||isAdmin(creator)))
			return;
		Session session = Factory.getSessionFactory().openSession();
		Transaction txn = session.beginTransaction();
		session.save(new Moderates(anon,chat));
		txn.commit();
    }
	
	public static void setUnbanned(Anon anon, Chat chat, Anon creator) {
		if(!(isCreated(chat, creator)||isAdmin(creator)))
			return;
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:BANNED]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
	}
	
	public static void setUnmoderates(Anon anon, Chat chat, Anon creator) {
		if(!(isCreated(chat, creator)||isAdmin(creator)))
			return;
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:MODERATES]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
	}
	
	public static boolean writeMsg(Anon anon, Chat chat, String text) {
		if(isBanned(anon, chat)||!isMember(anon, chat))
			return false;
        Session session = Factory.getSessionFactory().openSession();
        Msg msg=new Msg(text, Calendar.getInstance().getTime().toString());
		msg.setAuthor(anon.getName());
        Transaction txn = session.beginTransaction();
		session.save(new Contains(chat, msg));
		session.save(new Written(msg, anon));
		session.save(msg);
        txn.commit();
		return true;
    }
	
	public static List<Chat> getChats(Anon anon) {
        Session session = Factory.getSessionFactory().openSession();
		List<Chat> chats= new ArrayList<Chat>();
		Map<String, Object> params = new HashMap<>(1);
        params.put ("anonId", anon.getId());
        String cypher = "MATCH (a:Anon)-[b:MEMBER]->(c:Chat) WHERE id(a)=$anonId RETURN c;";
		Iterator<Chat> iterator = session.query(Chat.class, cypher, params).iterator();
		while (iterator.hasNext()) {
		  chats.add(iterator.next());
		}
		return chats;
    }
	
	public static List<Msg> getMsgs(Chat chat) {
		List<Msg> msgs= new ArrayList<Msg>();
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
        params.put ("chatId", chat.getId());
        String cypher = "MATCH (c:Chat)-[b:CONTAINS]->(a:Msg) WHERE id(c)=$chatId RETURN a;";		
		Iterator<Msg> iterator = session.query(Msg.class, cypher, params).iterator();
		while (iterator.hasNext()) {
			msgs.add(iterator.next());
		}
		return msgs;
    }
	
	public static Anon getCreator(Chat chat) {
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
        params.put ("chatId", chat.getId());
        String cypher = "MATCH (c:Chat)-[b:CREATED]->(a:Anon) WHERE id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		return iterator.next();
    }
	
	public static Anon getAuthor(Msg msg) {
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
        params.put ("msgId", msg.getId());
        String cypher = "MATCH (c:Msg)-[b:WRITTEN]->(a:Anon) WHERE id(c)=$msgId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		return iterator.next();
    }
	
	public static List<Anon> getMembers(Chat chat) {
		List<Anon> members= new ArrayList<Anon>();
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
        params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:MEMBER]->(c:Chat) WHERE id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		while (iterator.hasNext()) {
			members.add(iterator.next());
		}
		return members;
    }
	
	public static List<Anon> getModers(Chat chat) {
		List<Anon> moders= new ArrayList<Anon>();
		List<Anon> members= getMembers(chat);
		for(Anon anon: members){
			if(isModerates(anon,chat))
				moders.add(anon);
        }
		return moders;
    }
	
	public static List<Anon> getBanned(Chat chat) {
		List<Anon> banned= new ArrayList<Anon>();
        Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
        params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:BANNED]->(c:Chat) WHERE id(c)=$chatId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		while (iterator.hasNext()) {
			banned.add(iterator.next());
		}
		return banned;
    }
	
	public static Chat updateChat(Chat chat){
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (c:Chat) WHERE id(c)=$chatId RETURN c;";		
		Iterator<Chat> iterator = session.query(Chat.class, cypher, params).iterator();
		chat=iterator.next();
		return chat;
	}
	
	public static Anon updateAnon(Anon anon){
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
		params.put ("anonId", anon.getId());
        String cypher = "MATCH (a:Anon) WHERE id(a)=$anonId RETURN a;";		
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		anon=iterator.next();
		return anon;
	}
	
	public static void leaveChat(Anon anon, Chat chat) {
		if(!isMember(anon, chat))
			return;
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(2);
        params.put ("anonId", anon.getId());
		params.put ("chatId", chat.getId());
        String cypher = "MATCH (a:Anon)-[b:MODERATES]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";
		session.query(Chat.class, cypher, params).iterator();
        cypher = "MATCH (a:Anon)-[b:MEMBER]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";
		session.query(Chat.class, cypher, params).iterator();
        cypher = "MATCH (a:Anon)-[b:BANNED]->(c:Chat) WHERE id(a)=$anonId and id(c)=$chatId DELETE b;";
		session.query(Chat.class, cypher, params).iterator();
		if(isCreated(chat,anon)){
			cypher = "MATCH (c:Chat)-[b:CREATED]->(a:Anon) WHERE id(c)=$chatId DELETE b;";
			session.query(Chat.class, cypher, params).iterator();
			List<Anon> moders=getModers(chat);
			if(moders.size()!=0){
				setCreated(chat,moders.get(0));
			}
			else{
				List<Anon> members=getMembers(chat);
				if(members.size()!=0){
					setCreated(chat,members.get(0));
				}
				else{
					cypher = "MATCH (c:Chat)-[b:CONTAINS]->(a:Msg)-[d:WRITTEN]->(e:Anon) WHERE id(c)=$chatId DETACH DELETE a;";
					session.query(Chat.class, cypher, params).iterator();
					cypher = "MATCH (c:Chat) WHERE id(c)=$chatId DETACH DELETE c;";
					session.query(Chat.class, cypher, params).iterator();
				}
			}
		}
	}
	
	public static void deleteChat(Chat chat, Anon admin){
		if(!isAdmin(admin))
			return;
		List<Anon> members = getMembers(chat);
		for(Anon anon: members){
            leaveChat(anon, chat);
        }
	}
	
	public static void deleteMsg(Msg msg, Chat chat, Anon moder) {
		Anon author=getAuthor(msg);
		if(isAdmin(author))
			return;
		if(!isAdmin(moder)&&!isCreated(chat,moder))
			if(!(isModerates(moder,chat)&&!isModerates(author,chat)))
				if(author.getId()!=moder.getId())
					return;
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(3);
		params.put ("chatId", chat.getId());
		params.put ("msgId", msg.getId());
        String cypher = "MATCH (c:Chat)-[b:CONTAINS]->(a:Msg) WHERE id(a)=$msgId and id(c)=$chatId DETACH DELETE a;";
		session.query(Chat.class, cypher, params).iterator();
	}
	
	public static void deleteAnon(Anon anon, Anon admin){
		if(!isAdmin(admin))
			return;
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
		params.put ("anonId", anon.getId());
        String cypher = "MATCH (c:Msg)-[b:WRITTEN]->(a:Anon) WHERE id(a)=$anonId DETACH DELETE c;";
		session.query(Chat.class, cypher, params).iterator();
		List<Chat> chats = getChats(anon);
		for(Chat chat: chats){
            leaveChat(anon, chat);
        }
        cypher = "MATCH (a:Anon) WHERE id(a)=$anonId DETACH DELETE a;";
		session.query(Chat.class, cypher, params).iterator();
	}
	
	public static Chat getChat(int id){
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
		params.put ("id", id);
        String cypher = "MATCH (c:Chat) WHERE id(c)=$id RETURN c;";	
		Iterator<Chat> iterator = session.query(Chat.class, cypher, params).iterator();
		return iterator.next();
	}
	
	public static Anon getAnon(int id){
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
		params.put ("id", id);
        String cypher = "MATCH (c:Anon) WHERE id(c)=$id RETURN c;";	
		Iterator<Anon> iterator = session.query(Anon.class, cypher, params).iterator();
		return iterator.next();
	}
	
	public static Msg getMsg(int id){
		Session session = Factory.getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<>(1);
		params.put ("id", id);
        String cypher = "MATCH (c:Msg) WHERE id(c)=$id RETURN c;";	
		Iterator<Msg> iterator = session.query(Msg.class, cypher, params).iterator();
		return iterator.next();
	}
	
}