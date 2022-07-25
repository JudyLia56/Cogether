package com.cogether.api.Noti.dto;

import com.cogether.api.user.dto.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Noti")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Noti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED",nullable = false)
    private int id ;

    @ManyToOne
    @JoinColumn(name="to_id")
    private User user;

    @Column(name="from_id",nullable = false)
    private int fromId;

    @Column(name="type", length=10,nullable = false)
    private String type;

    @Column(name="content_id",nullable = false)
    private int contentId;

    @Column(name="message", length=200,nullable = false)
    private String message;

    @Column(name="url", length = 300,nullable = false)
    private String url;

    @Column(name ="create_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createAt;

    @Column(name="read_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime readAt;
}
