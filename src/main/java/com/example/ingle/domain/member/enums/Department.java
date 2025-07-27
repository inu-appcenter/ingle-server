package com.example.ingle.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {

    // 인문대학
    KOREAN_LITERATURE("Dept. of Korean Language & Literature"),              // 국어국문학과
    ENGLISH_LITERATURE("Dept. of English Language & Literature"),            // 영어영문학과
    GERMAN_STUDIES("Dept. of German Language & Literature"),                 // 독어독문학과
    FRENCH_STUDIES("Dept. of French Language & Literature"),                 // 불어불문학과
    JAPANESE_LITERATURE("Dept. of Japanese Language & Literature"),          // 일본지역문화학과
    CHINESE_STUDIES("Dept. of Chinese Language & Cultural Studies"),         // 중어중국학과

    // 자연과학대학
    MATHEMATICS("Dept. of Mathematics"),                                     // 수학과
    PHYSICS("Dept. of Physics"),                                             // 물리학과
    CHEMISTRY("Dept. of Chemistry"),                                         // 화학과
    FASHION_INDUSTRY("Dept. of Fashion Industry"),                           // 패션산업학과
    MARINE_SCIENCE("Dept. of Marine Science"),                               // 해양학과

    // 사회과학 학
    SOCIAL_WELFARE("Dept. of Social Welfare"),                               // 사회복지학과
    MEDIA_COMMUNICATION("Dept. of Mass Communication"),                      // 미디어커뮤니케이션학과
    LIBRARY_INFO("Dept. of Library and Information Science"),                // 문헌정보학과
    CREATIVE_HRD("Dept. of Creative Human Resource Development"),            // 창의인재개발학과

    // 글로벌정경대학
    PUBLIC_ADMINISTRATION("Dept. of Public Administration"),                 // 행정학과
    POLITICS_DIPLOMACY("Dept. of Political Science & International Studies"),// 정치외교학과
    ECONOMICS("Dept. of Economics"),                                         // 경제학과
    TRADE("Dept. of International Trade"),                                   // 무역학부
    CONSUMER_SCIENCE("Dept. of Consumer Science"),                           // 소비자학과

    // 공과대학
    ENERGY_CHEMICAL("Dept. of Energy and Chemical Engineering"),             // 에너지화학공학과
    ELECTRICAL_ENGINEERING("Dept. of Electrical Engineering"),               // 전기공학과
    ELECTRONICS_ENGINEERING("Dept. of Electronics Engineering"),             // 전자공학과
    INDUSTRIAL_MANAGEMENT("Dept. of Industrial and Management Engineering"), // 산업경영공학과
    MATERIAL_SCIENCE("Dept. of Materials Science and Engineering"),          // 신소재공학과
    MECHANICAL_ENGINEERING("Dept. of Mechanical Engineering and Robotics"),  // 기계공학과
    BIO_ROBOTICS_ENGINEERING("Dept. of Biomedical & Robotics Engineering"),  // 바이오-로봇 시스템 공학과
    SAFETY_ENGINEERING("Dept. of Safety Engineering"),                       // 안전공학과

    // 정보기술대학
    COMPUTER_ENGINEERING("Dept. of Computer Science & Engineering"),                 // 컴퓨터공학부
    INFORMATION_COMMUNICATION_ENGINEERING("Dept. of Information Telecommunication Engineering"), // 정보통신공학과
    EMBEDDED_SYSTEM("Dept. of Embedded-Systems Engineering"),                        // 임베디드시스템공학과

    // 경영대학
    BUSINESS_ADMINISTRATION("Dept. of Business Administration"),            // 경영학부
    DATA_SCIENCE("Dept. of Data Science"),                                  // 데이터과학과
    TAX_ACCOUNTING("Dept. of Tax & Accounting"),                            // 세무회계학과
    TECHNO_MANAGEMENT("Dept. of Technology Management"),                    // 테크노경영학과

    // 예술체육대학
    FINE_ARTS("Korean Painting Major, Western Painting Major"),             // 조형예술학부
    DESIGN("Division of Design"),                                           // 디자인학부
    PERFORMING_ART("Dept. of Performing Arts"),                             // 공연예술학과
    SPORTS_SCIENCE("Dept. of Sport Science"),                               // 스포츠과학부
    HEALTH_EXERCISE("Dept. of Health & Kinesiology"),                       // 운동건강학부

    // 사범대학
    KOREAN_EDUCATION("Dept. of Korean Language Education"),                 // 국어교육과
    ENGLISH_EDUCATION("Dept. of English Language Education"),               // 영어교육과
    JAPANESE_EDUCATION("Dept. of Japanese Language Education"),             // 일어교육과
    MATH_EDUCATION("Dept. of Mathematics Education"),                       // 수학교육과
    PHYSICAL_EDUCATION("Dept. of Physical Education"),                      // 체육교육과
    EARLY_CHILDHOOD_EDUCATION("Dept. of Early Childhood Education"),        // 유아교육과
    HISTORY_EDUCATION("Dept. of History Education"),                        // 역사교육과
    ETHICS_EDUCATION("Dept. of Ethics Education"),                          // 윤리교육과

    // 도시과학대학
    URBAN_ADMINISTRATION("Dept. of Urban Policy and Administration"),       // 도시행정학과
    CIVIL_ENVIRONMENT_ENGINEERING("Dept. of Civil and Environmental Engineering"), // 도시환경학부
    URBAN_ENGINEERING("Dept. of Urban Engineering"),                        // 도시공학과
    URBAN_ARCHITECTURE("Dept. of Architecture & Urban Design"),             // 도시건축학부

    // 생명과학기술대학
    LIFE_SCIENCE("Dept. of Biological Sciences"),                           // 생명과학부
    BIOENGINEERING("Dept. of Bioengineering"),                              // 생명공학부

    // 융합자유전공대학
    LIBERAL_ARTS("Dept. of Liberal Arts"),                                  // 자유전공학부
    INTERNATIONAL_LIBERAL_ARTS("Dept. of International Liberal Arts"),      // 국제자유전공학부
    CONVERGENCE("Dept. of Convergence Studies"),                            // 융합학부

    // 동북아국제통상학부
    NORTHEAST_ASIAN_TRADE("Dept. of Northeast Asian Trade"),                // 동북아국제통상전공
    SMART_LOGISTICS_ENGINEERING("Dept. of Smart Logistics Engineering"),    // 스마트물류공학전공
    IBE("Dept. of International Business Economics"),                       // IBE전공

    // 법학부
    LAW("Dept. of Law"),                                                    // 법학부

    // 일반 대학원
    KOREAN_EDUCATION_STUDIES("Dept. of Korean Language Education Studies"), // 한국어교육학과
    EDUCATION("Dept. of Education"),                                        // 교육학과
    ETHICS("Dept. of Ethics"),                                              // 윤리학과
    URBAN_POLICY_PLANNING("Dept. of Urban Planning & Policy (Joint Program)"),       // 도시계획·정책학과(협동과정)
    NATURE_EARLY_EDUCATION("Dept. of Nature & Early Childhood Education (Joint Program)"), // 유아·숲·자연교육학과(협동과정)
    TOURISM_CONVENTION_ENTERTAINMENT("Dept. of Tourism, Convention & Entertainment"), // 관광컨벤션엔터테인먼트학과

    CLOTHING_TEXTILES("Dept. of Clothing & Textiles"),                     // 의류학과
    BEAUTY_INDUSTRY("Dept. of Beauty Industry"),                           // 뷰티산업학과

    BIO_NANO_ENGINEERING("Dept. of Bio & Nano Biotechnology"),             // 생명·나노바이오공학과
    CLIMATE_INTERNATIONAL_COOPERATION("Dept. of Climate & Intl. Cooperation"), // 기후국제협력학과(협동과정)
    URBAN_CONVERGENCE("Dept. of Urban Convergence Studies"),              // 도시융ㆍ복합학과(협동과정)
    INTELLIGENT_SEMICONDUCTOR("Dept. of Intelligent Semiconductor Engineering"), // 지능형반도체공학과(협동과정)
    ARTIFICIAL_INTELLIGENCE("Dept. of Artificial Intelligence"),          // 인공지능학과(협동과정)
    FUTURE_MOBILITY("Dept. of Future Mobility"),                          // 미래모빌리티학과(협동과정)
    BIO_HEALTH_CONVERGENCE("Dept. of Bio Health Convergence"),            // 바이오헬스융합학과(협동과정)
    ENVIRONMENTAL_ENERGY_ENGINEERING("Dept. of Environmental & Energy Engineering"), // 환경에너지공학과
    URBAN_CONSTRUCTION_ENGINEERING("Dept. of Urban Construction Engineering"),       // 도시건설공학과
    ARCHITECTURE("Dept. of Architecture"),                                 // 건축학과

    FINE_ART("Dept. of Fine Arts"),                                        // 미술학과
    DESIGN_ART("Dept. of Design Art"),                                     // 디자인학과

    // 동북아물류 대학원
    LOGISTICS_MANAGEMENT("Dept. of Logistics Management"),                 // 물류경영학과
    LOGISTICS_SYSTEM("Dept. of Logistics System"),                         // 융합물류시스템학과

    // 교육 대학원
    EDUCATION_ADMINISTRATION_LEADERSHIP("Dept. of Education Administration & Leadership"), // 교육행정·리더십전공
    INSTRUCTIONAL_DESIGN_CONSULTING("Dept. of Instructional Design & Consulting"),         // 수업설계·수업컨설팅 전공
    LIFELONG_VOCATIONAL_EDUCATION("Dept. of Lifelong and Vocational Education"),          // 평생·직업교육전공
    COUNSELING_PSYCHOLOGY("Dept. of Counseling Psychology"),                              // 상담심리전공
    CREATIVITY_GIFTED_EDUCATION("Dept. of Creativity and Gifted Education"),              // 창의성·영재교육전공
    CHILD_ART_THERAPY("Dept. of Child Art Therapy"),                                      // 아동 예술심리치료전공
    MEDIA_EDUCATION("Dept. of Media Education"),                                          // 미디어교육전공
    MECHANICAL_EDUCATION("Dept. of Mechanical Education"),                                // 기계교육전공
    ART_EDUCATION("Dept. of Art Education"),                                              // 미술교육전공
    SPORTS_CULTURE_ADMINISTRATION("Dept. of Sports Culture Administration"),              // 스포츠문화행정전공

    // 정책 대학원
    GENERAL_PUBLIC_ADMINISTRATION("Dept. of Public Administration"),                      // 일반행정학과
    JUDICIAL_ADMINISTRATION("Dept. of Judicial Administration"),                          // 사법행정학과
    CRISIS_MANAGEMENT("Dept. of Crisis Management"),                                      // 위기관리학과
    LEGISLATIVE_SECURITY_STUDIES("Dept. of Legislative & Security Studies"),              // 의회정치·안보정책학과

    // 정보기술 대학원

    // 경영 대학원

    // 공학 대학원
    CONVERGENCE_DESIGN("Major of Convergence Design"),                                    // 융합디자인전공

    // 문화 대학원
    LOCAL_CULTURE("Dept. of Local Culture");                                               // 지역문화학과

    private final String fullName;
}