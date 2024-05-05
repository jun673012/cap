package com.kmou.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    private boolean isAccepted;
    private boolean isPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String garbageName;
    private String garbageContent;

    private Long price;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PaymentInfo paymentInfo;
}