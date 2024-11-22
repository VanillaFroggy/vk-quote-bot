package com.justai.vkquotebot;

import com.justai.vkquotebot.client.VkApiClient;
import com.justai.vkquotebot.config.VkQuoteBotConfigProperties;
import com.justai.vkquotebot.controller.VkQuoteBotController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VkQuoteBotController.class)
public class CallbackApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VkApiClient client;

    @Autowired
    private VkQuoteBotConfigProperties configProperties;

    @Test
    public void testConfirmation() throws Exception {
        this.mockMvc.perform(post("/callback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VkApiTestRequests.VALID_CONFIRMATION_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().string(configProperties.getConfirmationCode()));
    }

    @Test
    public void testReplyToMessage() throws Exception {
        String peerId = "12345";
        String text = "Hello, world!";
        String fixedRandomId = "1234567890";

        when(client.sendMessage(
                eq(configProperties.getToken()),
                eq("5.131"),
                eq(peerId),
                eq(VkQuoteBotController.REPLY_TEXT + text),
                eq(fixedRandomId)
        )).thenReturn("Feign client is working");

        this.mockMvc.perform(post("/callback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VkApiTestRequests.VALID_MESSAGE_NEW_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));

        ArgumentCaptor<String> peerIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(client).sendMessage(
                eq(configProperties.getToken()),
                eq("5.131"),
                peerIdCaptor.capture(),
                messageCaptor.capture(),
                anyString()
        );

        assertEquals(peerId, peerIdCaptor.getValue());
        assertEquals("Вы сказали: " + text, messageCaptor.getValue());
    }

    @Test
    public void testBadRequest() throws Exception {
        this.mockMvc.perform(post("/callback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VkApiTestRequests.BAD_REQUEST))
                .andExpect(status().isBadRequest());
    }
}
