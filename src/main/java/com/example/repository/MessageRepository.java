package com.example.repository;

import com.example.entity.Message;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    boolean existsByPostedBy(Integer posted_by);
    @Modifying
    @Query("UPDATE Message messages SET messages.messageText = :messageText WHERE messages.id = :id")
    int updateText (@Param ("messageText") String messageText, @Param ("id") Integer id);
}
