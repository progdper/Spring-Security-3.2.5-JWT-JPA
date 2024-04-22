package com.example.demo.domain_DB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "users")
//@Table(name = "user",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "email")
//        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

//    @Enumerated(EnumType.STRING)
//    private ERole role;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank
    private String email;

    @Column(name = "password")
    @JsonIgnore
    @NotBlank
    private String password;

    @CreatedDate
    @Column(name = "createdAt")
    private LocalDateTime createdAt;    // OffsetDateTime, Instant, ZonedDateTime 등으로 변경할 수 있다.

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

//    @Builder
//    public User(ERole role, String email, String password) {
//        this.role = role;
//        this.email = email;
//        this.password = password;
//    }

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void update(String password) {
        this.password = password;
    }

}
