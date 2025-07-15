package chat.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private String from;
    private String content;

    public ChatMessage() {}

    public ChatMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

}
