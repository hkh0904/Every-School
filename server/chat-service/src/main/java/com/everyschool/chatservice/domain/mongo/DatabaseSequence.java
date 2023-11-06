package com.everyschool.chatservice.domain.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "database_sequences")
@Data
public class DatabaseSequence {

    @Id
    private String id;
    private long seq;

    @Builder
    private DatabaseSequence(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }
}
