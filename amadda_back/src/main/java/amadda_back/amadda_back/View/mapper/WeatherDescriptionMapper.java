package amadda_back.amadda_back.View.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDescriptionMapper {

    private static final Map<Integer, List<String>> WEATHER_DESCRIPTION_MAP = new HashMap<>();

    static {
        WEATHER_DESCRIPTION_MAP.put(200, List.of("번개", "가벼운 비를 동반한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(201, List.of("번개", "비를 동반한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(202, List.of("번개", "폭우를 동반한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(210, List.of("번개", "약한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(211, List.of("번개", "천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(212, List.of("번개", "강한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(221, List.of("번개", "불규칙적 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(230, List.of("번개", "약한 연무를 동반한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(231, List.of("번개", "연무를 동반한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(232, List.of("번개", "강한 안개비를 동반한 천둥구름"));
        WEATHER_DESCRIPTION_MAP.put(300, List.of("비", "가벼운 안개비"));
        WEATHER_DESCRIPTION_MAP.put(301, List.of("비", "안개비"));
        WEATHER_DESCRIPTION_MAP.put(302, List.of("비", "강한 안개비"));
        WEATHER_DESCRIPTION_MAP.put(310, List.of("비", "가벼운 적은비"));
        WEATHER_DESCRIPTION_MAP.put(311, List.of("비", "적은비"));
        WEATHER_DESCRIPTION_MAP.put(312, List.of("비", "강한 적은비"));
        WEATHER_DESCRIPTION_MAP.put(313, List.of("비", "소나기와 안개비"));
        WEATHER_DESCRIPTION_MAP.put(314, List.of("비", "강한 소나기와 안개비"));
        WEATHER_DESCRIPTION_MAP.put(321, List.of("비", "소나기"));
        WEATHER_DESCRIPTION_MAP.put(500, List.of("비", "약한 비"));
        WEATHER_DESCRIPTION_MAP.put(501, List.of("비", "중간 비"));
        WEATHER_DESCRIPTION_MAP.put(502, List.of("비", "강한 비"));
        WEATHER_DESCRIPTION_MAP.put(503, List.of("비", "매우 강한 비"));
        WEATHER_DESCRIPTION_MAP.put(504, List.of("비", "극심한 비"));
        WEATHER_DESCRIPTION_MAP.put(511, List.of("비", "우박"));
        WEATHER_DESCRIPTION_MAP.put(520, List.of("비", "약한 소나기 비"));
        WEATHER_DESCRIPTION_MAP.put(521, List.of("비", "소나기 비"));
        WEATHER_DESCRIPTION_MAP.put(522, List.of("비", "강한 소나기 비"));
        WEATHER_DESCRIPTION_MAP.put(531, List.of("비", "불규칙적 소나기 비"));
        WEATHER_DESCRIPTION_MAP.put(600, List.of("눈", "가벼운 눈"));
        WEATHER_DESCRIPTION_MAP.put(601, List.of("눈", "눈"));
        WEATHER_DESCRIPTION_MAP.put(602, List.of("눈", "강한 눈"));
        WEATHER_DESCRIPTION_MAP.put(611, List.of("눈", "진눈깨비"));
        WEATHER_DESCRIPTION_MAP.put(612, List.of("눈", "소나기 진눈깨비"));
        WEATHER_DESCRIPTION_MAP.put(615, List.of("눈", "약한 비와 눈"));
        WEATHER_DESCRIPTION_MAP.put(616, List.of("눈", "비와 눈"));
        WEATHER_DESCRIPTION_MAP.put(620, List.of("눈", "약한 소나기 눈"));
        WEATHER_DESCRIPTION_MAP.put(621, List.of("눈", "소나기 눈"));
        WEATHER_DESCRIPTION_MAP.put(622, List.of("눈", "강한 소나기 눈"));
        WEATHER_DESCRIPTION_MAP.put(701, List.of("흐림", "박무"));
        WEATHER_DESCRIPTION_MAP.put(711, List.of("흐림", "연기"));
        WEATHER_DESCRIPTION_MAP.put(721, List.of("흐림", "연무"));
        WEATHER_DESCRIPTION_MAP.put(731, List.of("흐림", "모래 먼지"));
        WEATHER_DESCRIPTION_MAP.put(741, List.of("흐림", "안개"));
        WEATHER_DESCRIPTION_MAP.put(751, List.of("흐림", "모래"));
        WEATHER_DESCRIPTION_MAP.put(761, List.of("흐림", "먼지"));
        WEATHER_DESCRIPTION_MAP.put(762, List.of("흐림", "화산재"));
        WEATHER_DESCRIPTION_MAP.put(771, List.of("흐림", "돌풍"));
        WEATHER_DESCRIPTION_MAP.put(781, List.of("흐림", "토네이도"));
        WEATHER_DESCRIPTION_MAP.put(800, List.of("맑음", "구름 한 점 없는 맑은 하늘"));
        WEATHER_DESCRIPTION_MAP.put(801, List.of("맑음", "약간의 구름이 낀 하늘"));
        WEATHER_DESCRIPTION_MAP.put(802, List.of("흐림", "드문드문 구름이 낀 하늘"));
        WEATHER_DESCRIPTION_MAP.put(803, List.of("흐림", "구름이 거의 없는 하늘"));
        WEATHER_DESCRIPTION_MAP.put(804, List.of("흐림", "구름으로 뒤덮인 흐린 하늘"));
        WEATHER_DESCRIPTION_MAP.put(900, List.of("흐림", "토네이도"));
        WEATHER_DESCRIPTION_MAP.put(901, List.of("흐림", "태풍"));
        WEATHER_DESCRIPTION_MAP.put(902, List.of("흐림", "허리케인"));
        WEATHER_DESCRIPTION_MAP.put(903, List.of("흐림", "한랭"));
        WEATHER_DESCRIPTION_MAP.put(904, List.of("맑음", "고온"));
        WEATHER_DESCRIPTION_MAP.put(905, List.of("흐림", "바람부는"));
        WEATHER_DESCRIPTION_MAP.put(906, List.of("비", "우박"));
        WEATHER_DESCRIPTION_MAP.put(951, List.of("맑음", "바람이 거의 없는"));
        WEATHER_DESCRIPTION_MAP.put(952, List.of("맑음", "약한 바람"));
        WEATHER_DESCRIPTION_MAP.put(953, List.of("맑음", "부드러운 바람"));
        WEATHER_DESCRIPTION_MAP.put(954, List.of("맑음", "중간 세기 바람"));
        WEATHER_DESCRIPTION_MAP.put(955, List.of("맑음", "신선한 바람"));
        WEATHER_DESCRIPTION_MAP.put(956, List.of("흐림", "센 바람"));
        WEATHER_DESCRIPTION_MAP.put(957, List.of("흐림", "돌풍에 가까운 센 바람"));
        WEATHER_DESCRIPTION_MAP.put(958, List.of("흐림", "돌풍"));
        WEATHER_DESCRIPTION_MAP.put(959, List.of("흐림", "심각한 돌풍"));
        WEATHER_DESCRIPTION_MAP.put(960, List.of("흐림", "폭풍"));
        WEATHER_DESCRIPTION_MAP.put(961, List.of("흐림", "강한 폭풍"));
        WEATHER_DESCRIPTION_MAP.put(962, List.of("흐림", "허리케인"));

    }

    public static List<String> getWeatherDescription(int id) {
        return WEATHER_DESCRIPTION_MAP.getOrDefault(id, List.of("설명 없음")); // ID가 없을 경우 기본 값
    }

}
