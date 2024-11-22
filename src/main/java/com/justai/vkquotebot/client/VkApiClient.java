package com.justai.vkquotebot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "vkApiClient", url = "${vk.api-url}")
public interface VkApiClient {
    @GetMapping("/messages.send")
    String sendMessage(
            @RequestParam("access_token") String accessToken,
            @RequestParam("v") String apiVersion,
            @RequestParam("peer_id") String peerId,
            @RequestParam("message") String message,
            @RequestParam("random_id") String randomId
    );
}
