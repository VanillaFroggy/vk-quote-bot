package com.justai.vkquotebot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record SendMessageRequest(
        RequestType type,
        @JsonProperty("group_id") Integer groupId,
        Map<String, Object> object
) {
}
