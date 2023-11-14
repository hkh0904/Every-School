package com.everyschool.chatservice.api.service.chat;

import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ChatMongoService {

    private MongoOperations mongoOperations;

    @Autowired
    public ChatMongoService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long chatUpdate(Long chatId) {
        Chat chat = mongoOperations.findAndModify(query(where("chat_id").is(chatId)),
                new Update().set("status", ChatStatus.WARNING.getCode())
                        .set("lastModifiedDate", LocalDateTime.now()), options().returnNew(true).upsert(true),
                Chat.class);
        return chatId;
    }
}
