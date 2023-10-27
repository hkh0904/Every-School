package com.everyschool.schoolservice.domain.school;

import com.everyschool.schoolservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class School extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 6)
    private String zipcode;

    @Column(nullable = false, length = 100)
    private String address;

    private String url;

    @Column(unique = true, length = 13)
    private String tel;

    @Column(nullable = false)
    private LocalDateTime openDate;

    @Column(nullable = false)
    private Integer schoolTypeCodeId;

    protected School() {
        super();
    }

    @Builder
    private School(String name, String zipcode, String address, String url, String tel, LocalDateTime openDate, Integer schoolTypeCodeId) {
        this();
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.url = url;
        this.tel = tel;
        this.openDate = openDate;
        this.schoolTypeCodeId = schoolTypeCodeId;
    }
}
