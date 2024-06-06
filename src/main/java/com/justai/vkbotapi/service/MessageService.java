package com.justai.vkbotapi.service;

import com.justai.vkbotapi.config.VkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@Service
public class MessageService {

    private final VkConfig vkConfig;

    private final RestTemplate restTemplate;

    @Autowired
    public MessageService(VkConfig vkConfig) {
        this.vkConfig = vkConfig;
        this.restTemplate = new RestTemplate();
    }

    public void processMessage(Map<String, Object> message) {
        String userId = message.get("from_id").toString();
        String text = message.get("text").toString();
        sendMessage(userId, "Вы сказали: " + text);
    }

    public void sendMessage(String userId, String message) {
        int randomId = new Random().nextInt(Integer.MAX_VALUE);

        String url = String.format("%s/method/messages.send?user_id=%s&random_id=%d&message=%s&access_token=%s&v=%s&group_id=%s", //
                vkConfig.getApiUrl(), userId, randomId, message, vkConfig.getAccessToken(), vkConfig.getApiVersion(), vkConfig.getGroupId()); //

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        System.out.println(response.getBody());
    }
}
