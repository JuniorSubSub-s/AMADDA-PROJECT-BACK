package amadda_back.amadda_back.calendarpage.openapi.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.calendarpage.openapi.domain.HistoryDaysDTO;
import amadda_back.amadda_back.calendarpage.openapi.service.HistoryService;
import amadda_back.amadda_back.calendarpage.service.EventService;



@RestController
@RequestMapping("/api")
public class historyController {

    @Autowired
    private HistoryService historyService ;

    @Autowired
    private EventService eventService ;
    

    @Value("${openApi.serviceKey}")
    private String serviceKey ;

    @Value("${openApi.callBackUrl}")
    private String callBackUrl ;

    @Value("${openApi.dataType}")
    private String dataType ;

    @GetMapping("/history")
    public ResponseEntity<Object>  callHistoryApi(
        @RequestParam(value = "solYear") String solYear,
        @RequestParam(value = "solMonth") String solMonth
    ) {
        System.out.println("client end point : /api/history");
        System.out.println("servicekey  = " + serviceKey);
        System.out.println("callBackUrl = " + callBackUrl);
        System.out.println("dataType    = " + dataType);
        System.out.println("params = " + solYear + solMonth);

        String requestURL = callBackUrl+
                            "?serviceKey="+serviceKey+
                            "&solYear="+solYear+
                            "&solMonth="+solMonth;

        System.out.println("url check = " +requestURL);

        
        HttpURLConnection http   = null ;
        InputStream       stream = null ;
        String            result = null ;   // 자바입장에서는 변수, 함수 이런게 아니라 다 문자열로 인식함

        List<HistoryDaysDTO> list = null ;

        try {
            URL url = new URL(requestURL);
            http = (HttpURLConnection)url.openConnection(); // HTTP 연결 설정
            System.out.println("http connection = " + http); // 전부 합친 url이 보임
            int code = http.getResponseCode();              // HTTP 응답 코드 확인
            System.out.println("http response code = " + code); // 200이 나와야 정상
            if (code == 200) {                                 // 응답 코드가 200(정상)일 경우
                stream = http.getInputStream();                 // 응답 데이터를 InputStream으로 읽음
                result = readString(stream);    // readString은 내가 만든 메서드, InputStream을 문자열로 변환
                System.out.println("result = " + result ); // ex) 추석이 들어있으면 추석이 나옴(xml 트리형식으로)
                list = historyService.parseXml(result);    // XML 데이터를 파싱하여 리스트로 변환
            
                // list가 비어있지 않으면 저장
                if (!list.isEmpty()) {
                    eventService.savehistory(list);            // 파싱한 데이터를 DB에 저장
                } else {
                    System.out.println("No data to save. List is empty.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        if (list.size() == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("info", "저장된 데이터가 존재하지 않습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            System.out.println("client list size " + list.size());
            System.out.println(list.get(0).getDateName());
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        

    }

        // InputStream을 문자열로 변환하는 메서드
        public String readString(InputStream stream) throws IOException {
        BufferedReader br    = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String         input         = null ;
        StringBuilder  result = new StringBuilder();
        // 얼마나 있는지 모르니 while문 돌리기
        while( (input = br.readLine()) != null ) {
            result.append(input + "\n\r");
        }
        br.close();
        return result.toString() ;
    }
}
