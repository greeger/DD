package com.mkyong.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

import trash.DDService;
import node.*;
import java.util.List;

@Controller
public class WelcomeController {

    private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @GetMapping("/")
    public String index(Model model) {
        return "index";

    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "logIn")
    public String logIn(Model model, @RequestParam String login, @RequestParam String password) {
		Anon anon = DDService.logIn(login, password);
		if(anon==null) return "index";
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "signUp")
    public String signUp(Model model, @RequestParam String login, @RequestParam String password) {
		//DDService.deleteEverything();
		Anon anon = DDService.signUp(login, password);
		if(anon==null) return "index";
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST, params = "enter")
    public String enter(Model model, @RequestParam String chatId, @RequestParam String anonId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/updateChat", method = RequestMethod.POST, params = "update")
    public String updateChat(Model model, @RequestParam String chatId, @RequestParam String anonId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST, params = "delete")
    public String delete(Model model, @RequestParam String chatId, @RequestParam String anonId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		DDService.deleteChat(chat,anon);
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/updateChats", method = RequestMethod.POST, params = "update")
    public String updateChats(Model model, @RequestParam String anonId) {
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/chats", method = RequestMethod.POST, params = "chats")
    public String chats(Model model, @RequestParam String anonId) {
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/index", method = RequestMethod.POST, params = "index")
    public String index2(Model model) {
        return "index";
    }
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST, params = "leave")
    public String leave(Model model, @RequestParam String chatId, @RequestParam String anonId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		DDService.leaveChat(anon,chat);
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST, params = "setMember")
    public String setMember(Model model, @RequestParam String chatId, @RequestParam String anonId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		DDService.setMember(anon,chat);
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
    public String createChat(Model model, @RequestParam String name, @RequestParam String anonId) {
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Chat chat = DDService.createChat(name,anon);
		model.addAttribute("anon", anon);
		model.addAttribute("allChats", DDService.getAllChats());
		model.addAttribute("chats", DDService.getChats(anon));
        return "allChats";
    }
	
	@RequestMapping(value = "/change", method = RequestMethod.POST, params = "deleteAnon")
    public String deleteAnon(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String memberId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Anon member = DDService.getAnon(Integer.parseInt(memberId));
		DDService.deleteAnon(member,anon);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/change", method = RequestMethod.POST, params = "setUnModerates")
    public String unModerates(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String memberId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Anon member = DDService.getAnon(Integer.parseInt(memberId));
		DDService.setUnmoderates(member,chat,anon);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/change", method = RequestMethod.POST, params = "ban")
    public String ban(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String memberId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Anon member = DDService.getAnon(Integer.parseInt(memberId));
		DDService.setBanned(member,chat,anon);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/change", method = RequestMethod.POST, params = "setModerates")
    public String setModerates(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String memberId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Anon member = DDService.getAnon(Integer.parseInt(memberId));
		DDService.setModerates(member,chat,anon);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/change", method = RequestMethod.POST, params = "unBan")
    public String unBan(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String memberId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Anon member = DDService.getAnon(Integer.parseInt(memberId));
		DDService.setUnbanned(member,chat,anon);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "write")
    public String write(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String text) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		DDService.writeMsg(anon,chat,text);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }
	
	@RequestMapping(value = "/deleteMsg", method = RequestMethod.POST, params = "deleteMsg")
    public String deleteMsg(Model model, @RequestParam String chatId, @RequestParam String anonId, @RequestParam String msgId) {
		Chat chat = DDService.getChat(Integer.parseInt(chatId));
		Anon anon = DDService.getAnon(Integer.parseInt(anonId));
		Msg msg = DDService.getMsg(Integer.parseInt(msgId));
		DDService.deleteMsg(msg,chat,anon);
		model.addAttribute("chat", chat);
		model.addAttribute("anon", anon);
		model.addAttribute("creator", DDService.getCreator(chat));
		model.addAttribute("moders", DDService.getModers(chat));
		model.addAttribute("banned", DDService.getBanned(chat));
		model.addAttribute("members", DDService.getMembers(chat));
		model.addAttribute("msgs", DDService.getMsgs(chat));
        return "chat";
    }

}