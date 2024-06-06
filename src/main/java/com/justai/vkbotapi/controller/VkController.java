package com.justai.vkbotapi.controller;

import com.justai.vkbotapi.config.VkConfig;
import com.justai.vkbotapi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class VkController {

    private final VkConfig vkConfig;

    private final MessageService messageService;

    @Autowired
    VkController(VkConfig vkConfig, MessageService messageService){
        this.vkConfig = vkConfig;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<String> callback(@RequestBody Map<String, Object> request) {
        String type = (String) request.get("type");
        switch(type) {
            case "confirmation" -> {
                vkConfig.setGroupId(request.get("group_id").toString());
                vkConfig.setApiVersion(request.get("v").toString());
                return ResponseEntity.ok(vkConfig.getConfirmationToken());
            }
            case "message_new" -> {
                Map<String, Object> object = (Map<String, Object>) request.get("object");
                messageService.processMessage((Map<String, Object>) object.get("message"));
                return ResponseEntity.ok("Message received");
            }
        }

        return ResponseEntity.ok("Unsupported event");
    }
}