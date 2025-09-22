package com.example.ingle.domain.image.repository;

import com.example.ingle.domain.image.domain.Image;
import com.example.ingle.domain.member.dto.res.MemberProfileImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("""
    SELECT new com.example.ingle.domain.member.dto.res.MemberProfileImageResponse(i.imageUrl)
    FROM Image i
    WHERE i.name = :imageName
    AND i.category = 'profile-image'
""")
    Optional<MemberProfileImageResponse> findByName(String imageName);
}
