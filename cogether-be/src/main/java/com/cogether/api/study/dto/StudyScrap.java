package com.cogether.api.study.dto;

import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "StudyScrap")
public class StudyScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id")
    private User user;
}
