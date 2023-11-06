package com.everyschool.chatservice.domain.filterword;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "filter_word")
@Getter
@Setter
public class FilterWord {
    @Transient
    public static final String SEQUENCE_NAME = "filter_sequence";

    @Id
    @Field("filter_word_id")
    private Long id;

    private String word;

    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    private FilterWord(Long id, String word) {
        this.id = id;
        this.word = word;
        this.isDeleted = false;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }
}
