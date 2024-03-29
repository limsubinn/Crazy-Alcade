package com.eni.backend.member.entity;

import com.eni.backend.auth.oauth2.user.OAuth2Provider;
import com.eni.backend.auth.oauth2.user.OAuth2UserInfo;
import com.eni.backend.common.entity.BaseTime;
import com.eni.backend.item.entity.MemberItem;
import com.eni.backend.member.dto.request.PutCoinRequest;
import com.eni.backend.member.dto.request.PutLanguageRequest;
import com.eni.backend.member.dto.request.PutNicknameRequest;
import com.eni.backend.member.dto.request.PutRewardRequest;
import com.eni.backend.problem.entity.Code;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@ToString
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer coin;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer exp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language lang;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer complaint;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp connectedAt;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Code> codes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MemberItem> memberItems = new ArrayList<>();

    @Builder
    private Member(String email, OAuth2Provider provider, String socialId, String nickname) {
        this.email = email;
        this.socialId = socialId;
        this.provider = provider;
        this.nickname = nickname;
        this.connectedAt = Timestamp.valueOf(LocalDateTime.now());
        this.profile = "image1";
    }

    public static Member of(String email, OAuth2Provider provider, String socialId, String nickname) {
        return builder()
                .email(email)
                .provider(provider)
                .socialId(socialId)
                .nickname(nickname)
                .build();
    }

    public static Member from(OAuth2UserInfo info) {
        return builder()
                .nickname(info.getProvider().getRegistrationId() + info.getId())
                .socialId(info.getId())
                .provider(info.getProvider())
                .email(info.getEmail())
                .build();
    }

    public void updateConnectedAt(Timestamp timestamp) {
        this.connectedAt = timestamp;
    }

    public void updateLevel(Level level) {
        this.level = level;
    }

    public void updateProfile(String image) {
        this.profile = image;
    }

    public void updateNickname(PutNicknameRequest putNicknameRequest) {
        this.nickname = putNicknameRequest.getNickname();
    }

    public void updateLanguage(PutLanguageRequest putLanguageRequest) {
        this.lang = putLanguageRequest.getLang();
    }

    public void updateCoin(PutCoinRequest putCoinRequest, boolean operator) {
        if (operator) {
            this.coin += putCoinRequest.getPutValue();
        } else {
            this.coin -= putCoinRequest.getPutValue();
        }
    }

    public void setDefaultCoin(Integer putValue) {
        this.coin = putValue;
    }

    public void updateCoin(Integer putValue) {
        this.coin -= putValue;
    }

    public void putReward(PutRewardRequest putRewardRequest) {
        this.coin += putRewardRequest.getPutCoinValue();
        this.exp += putRewardRequest.getPutExpValue();
    }
}