package chat.model;

import lombok.Data;

@Data
public class PartialUserModel {
    private String uuid;
    private String name;

    public PartialUserModel(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}