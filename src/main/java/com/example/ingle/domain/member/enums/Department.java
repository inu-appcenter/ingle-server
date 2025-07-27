package com.example.ingle.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
    KOREAN_LITERATURE("Dept. of Korean Language & Literature"),            // 국어국문학과
    ENGLISH_LITERATURE("Dept. of English Language & Literature"),          // 영어영문학과
    GERMAN_STUDIES("Dept. of German Language & Literature"),               // 독어독문학과
    FRENCH_STUDIES("Dept. of French Language & Literature"),               // 불어불문학과
    JAPANESE_LITERATURE("Dept. of Japanese Language & Literature"),        // 일본지역문화학과
    CHINESE_STUDIES("Dept. of Chinese Language & Cultural Studies"),       // 중어중국학과

    MATHEMATICS("Dept. of Mathematics"),           // 수학과
    PHYSICS("Dept. of Physics"),                   // 물리학과
    CHEMISTRY("Dept. of Chemistry"),               // 화학과
    FASHION_INDUSTRY("Dept. of Fashion Industry"), // 패션산업학과
    MARINE_SCIENCE("Dept. of Marine Science"),     // 해양학과

    SOCIAL_WELFARE("Dept. of Social Welfare"),                             // 사회복지학과
    MEDIA_COMMUNICATION("Dept. of Mass Communication"),                    // 미디어커뮤니케이션학과
    LIBRARY_INFO("Dept. of Library and Information Science"),              // 문헌정보학과
    CREATIVE_HRD("Dept. of Creative Human Resource Development"),          // 창의인재개발학과

    PUBLIC_ADMINISTRATION("Dept. of Public Administration"),                    // 행정학과
    POLITICS_DIPLOMACY("Dept. of Political Science & International Studies"),   // 정치외교학과
    ECONOMICS("Dept. of Economics"),                                            // 경제학과
    TRADE("Dept. of International Trade"),                                     // 무역학부
    CONSUMER_SCIENCE("Dept. of Consumer Science"),                             // 소비자학과

    ENERGY_CHEMICAL("Energy and Chemical Engineering"),                 // 에너지화학공학과
    ELECTRICAL_ENGINEERING("Electrical Engineering"),                    // 전기공학과
    ELECTRONICS_ENGINEERING("Electronics Engineering"),                  // 전자공학과
    INDUSTRIAL_MANAGEMENT("Industrial and Management Engineering"),      // 산업경영공학과
    MATERIAL_SCIENCE("Materials Science and Engineering"),               // 신소재공학과
    MECHANICAL_ENGINEERING("Mechanical Engineering and Robotics"),       // 기계공학과
    BIO_ROBOTICS_ENGINEERING("Biomedical & Robotics Engineering"),       // 바이오-로봇 시스템 공학과
    SAFETY_ENGINEERING("Safety Engineering"),                            // 안전공학과

    COMPUTER_ENGINEERING("Dept. of Computer Science & Engineering"),               // 컴퓨터공학부
    INFORMATION_COMMUNICATION_ENGINEERING("Dept. of Information Telecommunication Engineering"), // 정보통신공학과
    EMBEDDED_SYSTEM("Dept. of Embedded-Systems Engineering"),                      // 임베디드시스템공학과

    BUSINESS_ADMINISTRATION("Dept. of Business Administration"),  // 경영학부
    DATA_SCIENCE("Dept. of Data Science"),                       // 데이터과학과
    TAX_ACCOUNTING("Dept. of Tax & Accounting"),                 // 세무회계학과
    TECHNO_MANAGEMENT("Dept. of Technology Management"),         // 테크노경영학과

    FINE_ARTS("Korean Painting Major, Western Painting Major"), // 조형예술학부
    DESIGN("Division of Design"),                              // 디자인학부
    PERFORMING_ART("Dept. of Performing Arts"),                // 공연예술학과
    SPORTS_SCIENCE("Dept. of Sport Science"),                  // 스포츠과학부
    HEALTH_EXERCISE("Dept. of Health & Kinesiology"),          // 운동건강학부

    KOREAN_EDUCATION("Korean Language Education"),          // 국어교육과
    ENGLISH_EDUCATION("English Language Education"),        // 영어교육과
    JAPANESE_EDUCATION("Japanese Language Education"),      // 일어교육과
    MATH_EDUCATION("Mathematics Education"),                 // 수학교육과
    PHYSICAL_EDUCATION("Physical Education"),                // 체육교육과
    EARLY_CHILDHOOD_EDUCATION("Early Childhood Education"),  // 유아교육과
    HISTORY_EDUCATION("History Education"),                  // 역사교육과
    ETHICS_EDUCATION("Ethics Education"),                    // 윤리교육과

    URBAN_ADMINISTRATION("Urban Policy and Administration"),            // 도시행정학과
    CIVIL_ENVIRONMENT_ENGINEERING("Civil and Environmental Engineering"),// 도시환경학부
    URBAN_ENGINEERING("Environmental Engineering"),                     // 도시공학과
    URBAN_ARCHITECTURE("Division of Architecture & Urban Design"),       // 도시건축학부

    LIFE_SCIENCE("Biological Sciences"),                 // 생명과학부
    BIOENGINEERING("Bioengineering"),                    // 생명공학부

    LIBERAL_ARTS("Division of Liberal Arts"),                   // 자유전공학부
    INTERNATIONAL_LIBERAL_ARTS("Division of International Liberal Arts"), // 국제자유전공학부
    CONVERGENCE("Division of Convergence Studies"),             // 융합학부

    NORTHEAST_ASIAN_TRADE("Northeast Asian Trade"),   // 동북아국제통상전공
    SMART_LOGISTICS_ENGINEERING("Smart Logistics Engineering"), // 스마트물류공학전공
    IBE("International Business Economics"),           // IBE전공

    LAW("School of Law"); // 법학부

    private final String fullName;
}