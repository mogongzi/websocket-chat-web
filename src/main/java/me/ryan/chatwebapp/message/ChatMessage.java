package me.ryan.chatwebapp.message;

import lombok.Getter;
import lombok.Setter;
import me.ryan.chatwebapp.util.StringUtil;

@Getter
@Setter
public class ChatMessage {

    private String from;
    private String text;
    private String recipient;
    private String time;

    public ChatMessage() { }

    public ChatMessage(String from, String text, String recipient) {
        this.from = from;
        this.text = text;
        this.recipient = recipient;
        this.time = StringUtil.getCurrentTimeStamp();
    }
}
