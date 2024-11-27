package amadda_back.amadda_back.calendarpage.ctrl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.calendarpage.domain.EventRequestDTO;
import amadda_back.amadda_back.calendarpage.domain.EventResponseDTO;
import amadda_back.amadda_back.calendarpage.domain.GetPostEntity;
import amadda_back.amadda_back.calendarpage.service.EventService;

@RestController
@RequestMapping("/events") // 엔트 컨트롤을 위한 메핑
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/index/{currentYearMonth}")
    public ResponseEntity<Object> landing(@PathVariable("currentYearMonth") String currentYearMonth,
            @RequestParam("userId") String userId) {
        System.out.println("client end point : /events/index/{currentYearMonth}" + eventService);
        System.out.println("오늘날짜 " + currentYearMonth);
        System.out.println("유저 아이디: " + userId);

        Map<String, String> map = new HashMap<>();
        map.put("currentYearMonth", currentYearMonth);
        map.put("userId", userId); // 유저 아이디 추가

        List<EventResponseDTO> list = eventService.findAll(map); // userId에 맞는 데이터 필터링
        System.out.println("이번달 데이터 : " + list);
        System.out.println("result size : " + list.size());

        if (list.size() == 0) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping("/viewday/{dateId}")
    public ResponseEntity<Object> view(@PathVariable("dateId") String dateId,
            @RequestParam("userId") String userId) {
        System.out.println("client end point : /events/view/{id}");
        System.out.println("params = " + dateId);
        Map<String, String> map = new HashMap<>();
        map.put("id", dateId);
        map.put("userId", userId); // 유저 아이디 추가

        List<EventResponseDTO> list = eventService.findlist(map);
        System.out.println("client list data : " + list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody EventRequestDTO params) {
        System.out.println("client end point : /events/save ");
        System.out.println(params);
        eventService.save(params);
        return new ResponseEntity<>("Event saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/gettodo/{eventid}")
    public ResponseEntity<Object> gettodo(@PathVariable("eventid") Integer eventid) {
        System.out.println("client end point : /events/gettodo/{eventid}");
        System.out.println("params = " + eventid);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", eventid);
        EventResponseDTO reslut = eventService.findRow(map);
        System.out.println("client list data : " + reslut);
        return new ResponseEntity<>(reslut, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        System.out.println("client end poin : /events/delete/{id}");
        System.out.println("params = " + id);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        eventService.delete(map);
        return new ResponseEntity<>(id + "번 데이터 삭제완료", HttpStatus.OK);
    }

    // update
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody EventRequestDTO params) {
        System.out.println("client endpoint : /events/update ");
        System.out.println("params : " + params);
        eventService.update(params);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 유저의 오래된 이벤트 4개를 가져오는 API
    @GetMapping("/alarmData/{userId}")
    public ResponseEntity<Object> getUserAlarmData(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("id", userId);
            map.put("offset", offset);

            System.out.println("map : " + map);

            List<EventResponseDTO> result = eventService.getOldestUserEvents(map);

            if (result == null || result.isEmpty()) {
                return new ResponseEntity<>("알람 데이터가 없습니다.", HttpStatus.NO_CONTENT);
            }

            System.out.println("가져온 알람 데이터 4개: " + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 원인 출력
            return new ResponseEntity<>("서버 내부 오류 발생.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post")
    public List<GetPostEntity> getPostsByUserIdAndDateRange(@RequestParam("userId") String userId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        System.out.println("아이디" + userId + "시작 날짜" + startDate + "끝 날짜" + endDate);
        return eventService.findPostsByUserIdAndDateRange(userId, startDate, endDate);
    }

}
