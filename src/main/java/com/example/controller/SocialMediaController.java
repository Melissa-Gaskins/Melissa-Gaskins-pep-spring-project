package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.repository.MessageRepository;
import com.example.service.MessageService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built...
 */
@RestController
@ResponseBody

public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService, AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/register")
    public ResponseEntity <Account> register(@RequestBody Account newAccount) {
        
        if (newAccount.getUsername() == null || newAccount.getPassword().length() < 4) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (accountRepository.existsByUsername(newAccount.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } 

        return new ResponseEntity<>(accountService.createAccount(newAccount), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity <Account> login(@RequestBody Account account) {
        if (accountRepository.existsByUsername(account.getUsername()) && accountRepository.existsByPassword(account.getPassword())) {
            return new ResponseEntity<>(accountService.loginToAccount(account), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/messages")
    public ResponseEntity <Message> create(@RequestBody Message message) {
        if (message.getMessageText() == "" || message.getMessageText().length() > 255 || !messageRepository.existsByPostedBy(message.getPostedBy())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(messageService.createNewMessage(message), HttpStatus.OK);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity <List<Message>> getAllMessages() {
        return new ResponseEntity<> (messageService.retrieveAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity <Optional<Message>> getMessageById(@PathVariable("message_id") Integer messageId){
        if (messageRepository.existsById(messageId)) {
            return new ResponseEntity<> (messageService.getMessageById(messageId), HttpStatus.OK);
        }
        
        else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity <Integer> deleteMessageById(@PathVariable ("message_id") Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            long firstCount = messageRepository.count();
            messageService.deleteMessageById(messageId);
            long secondCount = messageRepository.count();
            Integer rowsUpdated = (int) (firstCount - secondCount);
            return new ResponseEntity<> (rowsUpdated, HttpStatus.OK);
        }
        
        else {
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity <Integer> updateMessageById(@PathVariable ("message_id") Integer messageId, @RequestBody Map <String, String> messageText) {
        String text = messageText.get("messageText");
        if (messageRepository.existsById(messageId) && text.length() <= 255 && !text.isBlank()) {
            return new ResponseEntity<Integer>(messageService.updateMessageById(messageId, text), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity <List<Message>> getMessagesByUserId(@PathVariable ("account_id") Integer userId) {
        return new ResponseEntity<>(messageService.getMessagesByPostedBy(userId), HttpStatus.OK);
    }
}
