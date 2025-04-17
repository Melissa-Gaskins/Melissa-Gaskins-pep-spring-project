package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createNewMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> retrieveAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public void deleteMessageById(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    public int updateMessageById(Integer messageId, String messageText) {
        return messageRepository.updateText(messageText, messageId);
    }
}
