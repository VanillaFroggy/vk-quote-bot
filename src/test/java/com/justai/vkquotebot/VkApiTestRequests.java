package com.justai.vkquotebot;

public class VkApiTestRequests {
    public static final String VALID_CONFIRMATION_REQUEST = "{\"type\":\"confirmation\"}";

    public static final String VALID_MESSAGE_NEW_REQUEST = """
                {
                    "type": "message_new",
                    "object": {
                        "message": {
                            "peer_id": 12345,
                            "text": "Hello, world!"
                        }
                    }
                }
                """;

    public static final String BAD_REQUEST = "{\"type\":null}";
}
