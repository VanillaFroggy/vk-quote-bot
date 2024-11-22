package com.justai.vkquotebot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SendMessageResponse(
        String text,
        @JsonProperty("peer_id") Integer peerId
) {
}
