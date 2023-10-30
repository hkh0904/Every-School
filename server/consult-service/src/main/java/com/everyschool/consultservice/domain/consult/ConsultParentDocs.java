package com.everyschool.consultservice.domain.consult;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter @Setter
@Document(collection = "consult_parent_docs")
public class ConsultParentDocs {

    @Id
    private Long id;
    private String type;
    private String progressStatus;
    private String title;
    private LocalDateTime consultDate;

    @Builder
    private ConsultParentDocs(Long id, String type, String progressStatus, String title, LocalDateTime consultDate) {
        this.id = id;
        this.type = type;
        this.progressStatus = progressStatus;
        this.title = title;
        this.consultDate = consultDate;
    }
}
