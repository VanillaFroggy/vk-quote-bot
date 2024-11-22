package com.justai.vkquotebot.controller;

import com.justai.vkquotebot.client.VkApiClient;
import com.justai.vkquotebot.config.VkQuoteBotConfigProperties;
import com.justai.vkquotebot.dto.RequestType;
import com.justai.vkquotebot.dto.SendMessageRequest;
import com.justai.vkquotebot.dto.SendMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/callback")
public class VkQuoteBotController {
    private final VkApiClient vkApiClient;

    private final VkQuoteBotConfigProperties configProperties;

    public static final String REPLY_TEXT = "Вы сказали: ";

    @PostMapping
    public ResponseEntity<String> replyToMessage(@RequestBody SendMessageRequest request) {
        switch (request.type()) {
            case null:
                return ResponseEntity.badRequest().build();
            case RequestType.CONFIRMATION:
                return ResponseEntity.ok(configProperties.getConfirmationCode());
            case RequestType.MESSAGE_NEW, MESSAGE_REPLY:
                SendMessageResponse response = mapToSendMessageResponse(request.object().get("message"));
                if (response != null) {
                    vkApiClient.sendMessage(
                            configProperties.getToken(),
                            "5.131",
                            response.peerId().toString(),
                            REPLY_TEXT + response.text(),
                            String.valueOf(System.currentTimeMillis())
                    );
                }
                return ResponseEntity.ok("ok");
        }
    }

    private SendMessageResponse mapToSendMessageResponse(Object rawMessage) {
        return rawMessage instanceof Map<?, ?> messageMap
                ? new SendMessageResponse((String) messageMap.get("text"), (Integer) messageMap.get("peer_id"))
                : null;
    }
}
