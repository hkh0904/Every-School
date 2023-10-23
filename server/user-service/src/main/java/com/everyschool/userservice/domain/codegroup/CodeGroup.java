package com.everyschool.userservice.domain.codegroup;

import com.everyschool.userservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CodeGroup extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;

    @Column(unique = true, nullable = false, updatable = false, length = 10)
    private String groupName;

    protected CodeGroup() {
        super();
    }

    @Builder
    private CodeGroup(String groupName) {
        this();
        this.groupName = groupName;
    }
}
