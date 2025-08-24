package com.example.ingle.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {

    // 인문대학
    KOREAN_LITERATURE(College.HUMANITIES, "Dept. of Korean Language & Literature"),              // 국어국문학과
    ENGLISH_LITERATURE(College.HUMANITIES, "Dept. of English Language & Literature"),            // 영어영문학과
    GERMAN_STUDIES(College.HUMANITIES, "Dept. of German Language & Literature"),                 // 독어독문학과
    FRENCH_STUDIES(College.HUMANITIES, "Dept. of French Language & Literature"),                 // 불어불문학과
    JAPANESE_LITERATURE(College.HUMANITIES, "Dept. of Japanese Language & Literature"),          // 일본지역문화학과
    CHINESE_STUDIES(College.HUMANITIES, "Dept. of Chinese Language & Cultural Studies"),         // 중어중국학과

    // 자연과학대학
    MATHEMATICS(College.NATURAL_SCIENCES, "Dept. of Mathematics"),                               // 수학과
    PHYSICS(College.NATURAL_SCIENCES, "Dept. of Physics"),                                       // 물리학과
    CHEMISTRY(College.NATURAL_SCIENCES, "Dept. of Chemistry"),                                   // 화학과
    FASHION_INDUSTRY(College.NATURAL_SCIENCES, "Dept. of Fashion Industry"),                     // 패션산업학과
    MARINE_SCIENCE(College.NATURAL_SCIENCES, "Dept. of Marine Science"),                         // 해양학과

    // 사회과학대학
    SOCIAL_WELFARE(College.SOCIAL_SCIENCES, "Dept. of Social Welfare"),                          // 사회복지학과
    MEDIA_COMMUNICATION(College.SOCIAL_SCIENCES, "Dept. of Mass Communication"),                 // 미디어커뮤니케이션학과
    LIBRARY_INFO(College.SOCIAL_SCIENCES, "Dept. of Library and Information Science"),           // 문헌정보학과
    CREATIVE_HRD(College.SOCIAL_SCIENCES, "Dept. of Creative Human Resource Development"),       // 창의인재개발학과

    // 글로벌정경대학
    PUBLIC_ADMINISTRATION(College.COMMERCE_PUBLIC_AFFAIRS, "Dept. of Public Administration"),                 // 행정학과
    POLITICS_DIPLOMACY(College.COMMERCE_PUBLIC_AFFAIRS, "Dept. of Political Science & International Studies"),// 정치외교학과
    ECONOMICS(College.COMMERCE_PUBLIC_AFFAIRS, "Dept. of Economics"),                                         // 경제학과
    TRADE(College.COMMERCE_PUBLIC_AFFAIRS, "Dept. of International Trade"),                                   // 무역학부
    CONSUMER_SCIENCE(College.COMMERCE_PUBLIC_AFFAIRS, "Dept. of Consumer Science"),                           // 소비자학과

    // 공과대학
    ENERGY_CHEMICAL(College.ENGINEERING, "Dept. of Energy and Chemical Engineering"),             // 에너지화학공학과
    ELECTRICAL_ENGINEERING(College.ENGINEERING, "Dept. of Electrical Engineering"),               // 전기공학과
    ELECTRONICS_ENGINEERING(College.ENGINEERING, "Dept. of Electronics Engineering"),             // 전자공학과
    INDUSTRIAL_MANAGEMENT(College.ENGINEERING, "Dept. of Industrial and Management Engineering"), // 산업경영공학과
    MATERIAL_SCIENCE(College.ENGINEERING, "Dept. of Materials Science and Engineering"),          // 신소재공학과
    MECHANICAL_ENGINEERING(College.ENGINEERING, "Dept. of Mechanical Engineering and Robotics"),  // 기계공학과
    BIO_ROBOTICS_ENGINEERING(College.ENGINEERING, "Dept. of Biomedical & Robotics Engineering"),  // 바이오-로봇 시스템 공학과
    SAFETY_ENGINEERING(College.ENGINEERING, "Dept. of Safety Engineering"),                       // 안전공학과
    MECHATRONICS_ENGINEERING(College.ENGINEERING, "Dept. of Mechatronics Engineering"),           // 메카트로닉스공학과

    // 정보기술대학
    COMPUTER_ENGINEERING(College.INFORMATION_TECHNOLOGY, "Dept. of Computer Science & Engineering"),                 // 컴퓨터공학부
    INFORMATION_COMMUNICATION_ENGINEERING(College.INFORMATION_TECHNOLOGY, "Dept. of Information Telecommunication Engineering"), // 정보통신공학과
    EMBEDDED_SYSTEM(College.INFORMATION_TECHNOLOGY, "Dept. of Embedded-Systems Engineering"),                        // 임베디드시스템공학과

    // 경영대학
    BUSINESS_ADMINISTRATION(College.BUSINESS, "Dept. of Business Administration"),            // 경영학부
    DATA_SCIENCE(College.BUSINESS, "Dept. of Data Science"),                                  // 데이터과학과
    TAX_ACCOUNTING(College.BUSINESS, "Dept. of Tax & Accounting"),                            // 세무회계학과
    TECHNO_MANAGEMENT(College.BUSINESS, "Dept. of Technology Management"),                    // 테크노경영학과

    // 예술체육대학
    FINE_ARTS(College.ARTS_PHYSICAL_EDUCATION, "Korean Painting Major, Western Painting Major"),             // 조형예술학부
    DESIGN(College.ARTS_PHYSICAL_EDUCATION, "Division of Design"),                                           // 디자인학부
    PERFORMING_ART(College.ARTS_PHYSICAL_EDUCATION, "Dept. of Performing Arts"),                             // 공연예술학과
    SPORTS_SCIENCE(College.ARTS_PHYSICAL_EDUCATION, "Dept. of Sport Science"),                               // 스포츠과학부
    HEALTH_EXERCISE(College.ARTS_PHYSICAL_EDUCATION, "Dept. of Health & Kinesiology"),                       // 운동건강학부

    // 사범대학
    KOREAN_EDUCATION(College.EDUCATION, "Dept. of Korean Language Education"),                 // 국어교육과
    ENGLISH_EDUCATION(College.EDUCATION, "Dept. of English Language Education"),               // 영어교육과
    JAPANESE_EDUCATION(College.EDUCATION, "Dept. of Japanese Language Education"),             // 일어교육과
    MATH_EDUCATION(College.EDUCATION, "Dept. of Mathematics Education"),                       // 수학교육과
    PHYSICAL_EDUCATION(College.EDUCATION, "Dept. of Physical Education"),                      // 체육교육과
    EARLY_CHILDHOOD_EDUCATION(College.EDUCATION, "Dept. of Early Childhood Education"),        // 유아교육과
    HISTORY_EDUCATION(College.EDUCATION, "Dept. of History Education"),                        // 역사교육과
    ETHICS_EDUCATION(College.EDUCATION, "Dept. of Ethics Education"),                          // 윤리교육과

    // 도시과학대학
    URBAN_ADMINISTRATION(College.URBAN_SCIENCE, "Dept. of Urban Policy and Administration"),       // 도시행정학과
    CIVIL_ENVIRONMENT_ENGINEERING(College.URBAN_SCIENCE, "Dept. of Civil and Environmental Engineering"), // 도시환경학부
    URBAN_ENGINEERING(College.URBAN_SCIENCE, "Dept. of Urban Engineering"),                        // 도시공학과
    URBAN_ARCHITECTURE(College.URBAN_SCIENCE, "Dept. of Architecture & Urban Design"),             // 도시건축학부

    // 생명과학기술대학
    LIFE_SCIENCE(College.LIFE_SCIENCES_BIOENGINEERING, "Dept. of Biological Sciences"),                           // 생명과학부
    BIOENGINEERING(College.LIFE_SCIENCES_BIOENGINEERING, "Dept. of Bioengineering"),                              // 생명공학부

    // 융합자유전공대학
    LIBERAL_ARTS(College.LIBERAL_ARTS_COLLEGE, "Dept. of Liberal Arts"),                                  // 자유전공학부
    INTERNATIONAL_LIBERAL_ARTS(College.LIBERAL_ARTS_COLLEGE, "Dept. of International Liberal Arts"),      // 국제자유전공학부
    CONVERGENCE(College.LIBERAL_ARTS_COLLEGE, "Dept. of Convergence Studies"),                            // 융합학부

    // 동북아국제통상학부
    NORTHEAST_ASIAN_TRADE(College.NORTHEAST_ASIAN_STUDIES, "Dept. of Northeast Asian Trade"),                // 동북아국제통상전공
    SMART_LOGISTICS_ENGINEERING(College.NORTHEAST_ASIAN_STUDIES, "Dept. of Smart Logistics Engineering"),    // 스마트물류공학전공
    IBE(College.NORTHEAST_ASIAN_STUDIES, "Dept. of International Business Economics"),                       // IBE전공

    // 법학부
    LAW(College.LAW, "Dept. of Law"),                                                    // 법학부

    // 일반 대학원
    KOREAN_EDUCATION_STUDIES(College.GRADUATE_SCHOOL, "Dept. of Korean Language Education Studies"), // 한국어교육학과
    EDUCATION(College.GRADUATE_SCHOOL, "Dept. of Education"),                                        // 교육학과
    ETHICS(College.GRADUATE_SCHOOL, "Dept. of Ethics"),                                              // 윤리학과
    URBAN_POLICY_PLANNING(College.GRADUATE_SCHOOL, "Dept. of Urban Planning & Policy (Joint Program)"),       // 도시계획·정책학과(협동과정)
    NATURE_EARLY_EDUCATION(College.GRADUATE_SCHOOL, "Dept. of Nature & Early Childhood Education (Joint Program)"), // 유아·숲·자연교육학과(협동과정)
    TOURISM_CONVENTION_ENTERTAINMENT(College.GRADUATE_SCHOOL, "Dept. of Tourism, Convention & Entertainment"), // 관광컨벤션엔터테인먼트학과

    CLOTHING_TEXTILES(College.GRADUATE_SCHOOL, "Dept. of Clothing & Textiles"),                     // 의류학과
    BEAUTY_INDUSTRY(College.GRADUATE_SCHOOL, "Dept. of Beauty Industry"),                           // 뷰티산업학과

    BIO_NANO_ENGINEERING(College.GRADUATE_SCHOOL, "Dept. of Bio & Nano Biotechnology"),             // 생명·나노바이오공학과
    CLIMATE_INTERNATIONAL_COOPERATION(College.GRADUATE_SCHOOL, "Dept. of Climate & Intl. Cooperation"), // 기후국제협력학과(협동과정)
    URBAN_CONVERGENCE(College.GRADUATE_SCHOOL, "Dept. of Urban Convergence Studies"),              // 도시융ㆍ복합학과(협동과정)
    INTELLIGENT_SEMICONDUCTOR(College.GRADUATE_SCHOOL, "Dept. of Intelligent Semiconductor Engineering"), // 지능형반도체공학과(협동과정)
    ARTIFICIAL_INTELLIGENCE(College.GRADUATE_SCHOOL, "Dept. of Artificial Intelligence"),          // 인공지능학과(협동과정)
    FUTURE_MOBILITY(College.GRADUATE_SCHOOL, "Dept. of Future Mobility"),                          // 미래모빌리티학과(협동과정)
    BIO_HEALTH_CONVERGENCE(College.GRADUATE_SCHOOL, "Dept. of Bio Health Convergence"),            // 바이오헬스융합학과(협동과정)
    ENVIRONMENTAL_ENERGY_ENGINEERING(College.GRADUATE_SCHOOL, "Dept. of Environmental & Energy Engineering"), // 환경에너지공학과
    URBAN_CONSTRUCTION_ENGINEERING(College.GRADUATE_SCHOOL, "Dept. of Urban Construction Engineering"),       // 도시건설공학과
    ARCHITECTURE(College.GRADUATE_SCHOOL, "Dept. of Architecture"),                                 // 건축학과

    FINE_ART(College.GRADUATE_SCHOOL, "Dept. of Fine Arts"),                                        // 미술학과
    DESIGN_ART(College.GRADUATE_SCHOOL, "Dept. of Design Art"),                                     // 디자인학과
    PHYSICAL(College.GRADUATE_SCHOOL, "Dept. of Physical"),                                         // 체육학과

    // 동북아물류 대학원
    LOGISTICS_MANAGEMENT(College.GRADUATE_SCHOOL_LOGISTICS, "Dept. of Logistics Management"),                 // 물류경영학과
    LOGISTICS_SYSTEM(College.GRADUATE_SCHOOL_LOGISTICS, "Dept. of Logistics System"),                         // 융합물류시스템학과

    // 교육 대학원
    EDUCATION_ADMINISTRATION_LEADERSHIP(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Education Administration & Leadership"), // 교육행정·리더십전공
    INSTRUCTIONAL_DESIGN_CONSULTING(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Instructional Design & Consulting"),         // 수업설계·수업컨설팅 전공
    LIFELONG_VOCATIONAL_EDUCATION(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Lifelong and Vocational Education"),          // 평생·직업교육전공
    COUNSELING_PSYCHOLOGY(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Counseling Psychology"),                              // 상담심리전공
    CREATIVITY_GIFTED_EDUCATION(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Creativity and Gifted Education"),              // 창의성·영재교육전공
    CHILD_ART_THERAPY(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Child Art Therapy"),                                      // 아동 예술심리치료전공
    MEDIA_EDUCATION(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Media Education"),                                          // 미디어교육전공
    MECHANICAL_EDUCATION(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Mechanical Education"),                                // 기계교육전공
    ART_EDUCATION(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Art Education"),                                              // 미술교육전공
    SPORTS_CULTURE_ADMINISTRATION(College.GRADUATE_SCHOOL_EDUCATION, "Dept. of Sports Culture Administration"),              // 스포츠문화행정전공

    // 정책 대학원
    GENERAL_PUBLIC_ADMINISTRATION(College.GRADUATE_SCHOOL_PUBLIC_ADMINISTRATION, "Dept. of Public Administration"),                      // 일반행정학과
    JUDICIAL_ADMINISTRATION(College.GRADUATE_SCHOOL_PUBLIC_ADMINISTRATION, "Dept. of Judicial Administration"),                          // 사법행정학과
    CRISIS_MANAGEMENT(College.GRADUATE_SCHOOL_PUBLIC_ADMINISTRATION, "Dept. of Crisis Management"),                                      // 위기관리학과
    LEGISLATIVE_SECURITY_STUDIES(College.GRADUATE_SCHOOL_PUBLIC_ADMINISTRATION, "Dept. of Legislative & Security Studies"),              // 의회정치·안보정책학과

    // 정보기술 대학원

    // 경영 대학원

    // 공학 대학원
    CONVERGENCE_DESIGN(College.GRADUATE_SCHOOL_ENGINEERING, "Major of Convergence Design"),                                    // 융합디자인전공

    // 문화 대학원
    LOCAL_CULTURE(College.GRADUATE_SCHOOL_CULTURE, "Dept. of Local Culture");                                               // 지역문화학과

    private final College college;
    private final String fullName;
}