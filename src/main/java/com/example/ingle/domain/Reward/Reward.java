package com.example.ingle.domain.Reward;


import com.example.ingle.domain.Reward.dto.req.CreateRewardRequestDto;
import com.example.ingle.domain.Reward.dto.req.UpdateRewardRequestDto;
import com.example.ingle.domain.member.dto.req.SignupRequestDto;
import com.example.ingle.domain.member.dto.req.UpdateMemberRequestDto;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.domain.tutorial.Category;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position", nullable = false, unique = true)
    @Min(1)
    private Integer position; // 리워드 칸 위치
    //[1][2][3]
    //[4][5][6]
    //[7][8][9]

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "reward_image_url", nullable = false)
    private String rewardImageUrl;

    @Column(name = "reward_detail_image_url", nullable = false)
    private String rewardDetailImageUrl;

    public Reward(CreateRewardRequestDto createRewardRequestDto) {
        this.position = createRewardRequestDto.getPosition();
        this.title = createRewardRequestDto.getTitle();
        this.description = createRewardRequestDto.getDescription();
        this.rewardImageUrl = createRewardRequestDto.getRewardImageUrl();
        this.rewardDetailImageUrl = createRewardRequestDto.getRewardDetailImageUrl();
    }

    public void updateReward(@Valid UpdateRewardRequestDto updateRewardRequestDto) {
        this.position = updateRewardRequestDto.getPosition() != null? updateRewardRequestDto.getPosition(): this.position;
        this.title = updateRewardRequestDto.getTitle() != null? updateRewardRequestDto.getTitle(): this.title;
        this.description = updateRewardRequestDto.getDescription() != null? updateRewardRequestDto.getDescription(): this.description;
        this.rewardImageUrl = updateRewardRequestDto.getRewardImageUrl() != null? updateRewardRequestDto.getRewardImageUrl(): this.rewardImageUrl;
        this.rewardDetailImageUrl = updateRewardRequestDto.getRewardDetailImageUrl() != null? updateRewardRequestDto.getRewardDetailImageUrl(): this.rewardDetailImageUrl;
    }
}
