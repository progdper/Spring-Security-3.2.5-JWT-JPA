package com.example.demo.domain_DB;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
//@DynamicUpdate 변경사항만 적용할 경우 사용 가능 - 오버헤드 문제 발생할 수 있음
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    @CreatedDate
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

//    @OneToOne 양방향 연관 관계에서 연관 관계의 주인이 아닌 쪽 엔티티를 조회할 때, Lazy로 동작할 수 없는 오류 있음
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false, unique = true)    // referencedColumnName의 기본값은 pk임에 지정하지 않아도 된다.
    private User userId;

    @Builder
    public Token(String refreshToken, User userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
    }



}
