package org.softarex.chat.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto implements Serializable {

    @NotNull
    private String author;
    @NotNull
    private MessageType type;
}