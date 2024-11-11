package amadda_back.amadda_back.calendarpage.ctrl;


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
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.calendarpage.domain.EventRequestDTO;
import amadda_back.amadda_back.calendarpage.domain.EventResponseDTO;
import amadda_back.amadda_back.calendarpage.service.EventService;






@RestController
@RequestMapping("/events") // 엔트 컨트롤을 위한 메핑
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/index/{currentYearMonth}")
    public ResponseEntity<Object> landing(@PathVariable("currentYearMonth") String currentYearMonth) {
        System.out.println("client end point : /events/index/{currentYearMonth}" + eventService);
        System.out.println("오늘날짜 " + currentYearMonth);
        Map<String, String> map = new HashMap<>();
        map.put("currentYearMonth", currentYearMonth);
        List<EventResponseDTO> list = eventService.findAll(map);
        System.out.println("이번달 데이터 : " + list);
        System.out.println("result size : " + list.size());
        if (list.size() == 0) {
	            // Map<String, String> msg = new HashMap<>();
	            // msg.put("info", "저장된 데이터가 존재하지 않습니다.");
	            return new ResponseEntity<>(list, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping("/viewday/{dateId}")
    public ResponseEntity<Object> view(@PathVariable("dateId") String dateId) {
        System.out.println("client end point : /events/view/{id}");
        System.out.println("params = " + dateId);
        Map<String, String> map = new HashMap<>();
        map.put("id", dateId);
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
        return new ResponseEntity<>(id+"번 데이터 삭제완료",HttpStatus.OK);
    }

    // update
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody EventRequestDTO params) {
        System.out.println("client endpoint : /events/update ");
        System.out.println("params : " + params);
        eventService.update(params);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
