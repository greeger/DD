import trash.DDService;
import node.*;
//import relationship.*;

import java.util.List;

public class KEKW {
	public static void main (String[] args) {
		
		DDService.deleteEverything();
		
		Anon greg = DDService.signUp("грига", "123");
		
		Chat fChat=DDService.createChat("чат григи после регистрации", greg);
		
		DDService.writeMsg(greg, fChat, "сообщение григи");
		
		Anon greg2 = DDService.logIn("грига", "123");
		
		Chat rerChat=DDService.createChat("чат григи после входа", greg2);
		
		Anon ira = DDService.signUp("Ира", "на горшке сидел король");
		
		Chat iraChat=DDService.createChat("чат иры", ira);
		
		DDService.writeMsg(ira, fChat, "сообщение Иры 1");
		
		DDService.setMember(ira, fChat);
		
		DDService.writeMsg(ira, fChat, "сообщение Иры 2");
		
		
        System.out.println();
        System.out.println("чаты григи:");
		List<Chat> gregChats=DDService.getChats(greg2);
		for(Chat chat: gregChats){
            System.out.println(chat.getName());
        }
		
        System.out.println();
		
        System.out.println("сообщения в чате после регистрации:");
		List<Msg> fChatMsgs=DDService.getMsgs(fChat);
		for(Msg msg: fChatMsgs){
            System.out.println(DDService.getAuthor(msg).getName()+" : "+msg.getText()+" : "+msg.getTime());
        }
		
        System.out.println();
		
		//greg.setIsAdmin(true);
		//DDService.deleteAnon(ira,greg);
		
		System.exit(0);
    }
}