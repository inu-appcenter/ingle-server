package com.example.ingle.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum College {

    // 학부
    HUMANITIES("College of Humanities"),                                            // 인문대학
    NATURAL_SCIENCES("College of Natural Sciences"),                                // 자연과학대학
    SOCIAL_SCIENCES("College of Social Sciences"),                                  // 사회과학대학
    COMMERCE_PUBLIC_AFFAIRS("College of Commerce & Public Affairs"),                // 글로정경대학
    ENGINEERING("College of Engineering"),                                          // 공과대학
    INFORMATION_TECHNOLOGY("College of Information Technology"),                    // 정보기술대학
    BUSINESS("College of Business"),                                                // 경영대학
    ARTS_PHYSICAL_EDUCATION("College of Arts and Physical Education"),              // 예술체육대학
    EDUCATION("College of Education"),                                              // 사범대학
    URBAN_SCIENCE("College of Urban Science"),                                      // 도시과학대학
    LIFE_SCIENCES_BIOENGINEERING("College of Life Sciences and Bioengineering"),    // 생명과학기술대학
    LIBERAL_ARTS_COLLEGE("College of Liberal Arts"),                                // 융합자유전공대학
    NORTHEAST_ASIAN_STUDIES("School of Northeast Asian Studies"),                   // 동북아국제통물류학부
    LAW("School of Law"),                                                           // 법학부

    // 대학원
    GRADUATE_SCHOOL("Graduate School"),                                              // 일반대학원
    GRADUATE_SCHOOL_LOGISTICS("Graduate School of Logistics"),                      // 동북아물류대학원
    GRADUATE_SCHOOL_EDUCATION("Graduate School of Education"),                      // 교육대학원
    GRADUATE_SCHOOL_PUBLIC_ADMINISTRATION("Graduate School of Public Administration"), // 정책대학원
    GRADUATE_SCHOOL_INFORMATION_TECHNOLOGY("Graduate School of Information Technology"), // 정보기술대학원
    GRADUATE_SCHOOL_BUSINESS("Graduate School of Business"),                        // 경영대학원
    GRADUATE_SCHOOL_ENGINEERING("Graduate School of Engineering"),                  // 공학대학원
    GRADUATE_SCHOOL_CULTURE("Graduate School of Culture");                           // 문화대학원

    private final String fullName;
}
