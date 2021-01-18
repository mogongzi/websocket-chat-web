package me.ryan.chatwebapp.controller;

import me.ryan.chatwebapp.handler.MyTextWebSocketHandler;
import me.ryan.chatwebapp.message.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketBroadcastContoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketBroadcastContoller.class);

    @GetMapping("/sockjs-broadcast")
    public String  getWebSocketWithSockJsBroadcast() {
        return "sockjs-broadcast";
    }

    @RequestMapping("/stomp-broadcast")
    public String getWebSocketBroadcast() {
        return "stomp-broadcast";
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public ChatMessage send(ChatMessage chatMessage) {
        LOGGER.info("Message from {} and content is {}", chatMessage.getFrom(), chatMessage.getText());
        return new ChatMessage(chatMessage.getFrom(), chatMessage.getText(), "ALL");
    }

}
