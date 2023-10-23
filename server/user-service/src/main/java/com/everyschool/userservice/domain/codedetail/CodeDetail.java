package com.everyschool.userservice.domain.codedetail;

import com.everyschool.userservice.domain.BaseEntity;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CodeDetail extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Integer id;

    @Column(unique = true, nullable = false, updatable = false, length = 10)
    private String codeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private CodeGroup group;

    protected CodeDetail() {
        super();
    }

    @Builder
    private CodeDetail(String codeName, CodeGroup group) {
        this();
        this.codeName = codeName;
        this.group = group;
    }
}
