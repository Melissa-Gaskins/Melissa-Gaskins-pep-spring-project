package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.repository.MessageRepository;
import com.example.service.MessageService;

import java.net.ResponseCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@ResponseBody

public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
    AccountRepository accountRepository;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.accountRepository = accountRepository;
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


}
