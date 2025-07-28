package chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // ou getters/setters manuels
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String fromUuid;
    private String body;
    private String status;

}
//
//@Setter
//@Getter
//public class ChatMessage {
//    private String from;
//    private String toUuid;
//    private String content;
//    private MessageStatus status;
//    private String createdAt;
//    private String updatedAt;
//    private String deletedAt;
//
//    public ChatMessage() {
//    }
//
//    public ChatMessage(String from, String to, String content, MessageStatus status, String createdAt,
//            String updatedAt, String deletedAt) {
//        this.from = from;
//        this.to = to;
//        this.content = content;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.deletedAt = deletedAt;
//    }
//
//}