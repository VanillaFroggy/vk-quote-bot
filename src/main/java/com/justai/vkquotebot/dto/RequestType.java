package com.justai.vkquotebot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum RequestType {
    @JsonProperty("message_new")
    MESSAGE_NEW("message_new"),

    @JsonProperty("message_reply")
    MESSAGE_REPLY("message_reply"),

    @JsonProperty("confirmation")
    CONFIRMATION("confirmation");

    private static final Map<String, RequestType> TYPES = new HashMap<>();

    static {
        for (RequestType type : EnumSet.allOf(RequestType.class)) {
            TYPES.put(type.getValue(), type);
        }
    }

    private final String value;
}
