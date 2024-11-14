package amadda_back.amadda_back.calendarpage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import amadda_back.amadda_back.calendarpage.dao.EventMapper;
import amadda_back.amadda_back.calendarpage.dao.getpostDao;
import amadda_back.amadda_back.calendarpage.domain.EventRequestDTO;
import amadda_back.amadda_back.calendarpage.domain.EventResponseDTO;
import amadda_back.amadda_back.calendarpage.domain.GetPostEntity;
import amadda_back.amadda_back.calendarpage.openapi.domain.HistoryDaysDTO;

@Service
public class EventService {

    // 의존관계 주입
    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private getpostDao getpostDao;

    public List<EventResponseDTO> findAll(Map<String, String> map) {
        // 의존관계 주입이 제대로 되었는지 확인
        System.out.println("debug >>> service findAll " + eventMapper);
        List<EventResponseDTO> reslut = eventMapper.findAllRow(map);
        System.out.println("service findALL reslut " + reslut.size());
        return reslut;
    }

    public List<EventResponseDTO> findlist(Map<String, String> map) {
        System.out.println("debug >>> service findlist " + eventMapper);
        return eventMapper.findlist(map);
    }

    public EventResponseDTO findRow(Map<String, Integer> map) {
        System.out.println("debug >>> service findRow " + eventMapper);
        return eventMapper.findRow(map);
    }

    public void save(EventRequestDTO params) {
        System.out.println("debug >>> service save " + eventMapper);
        System.out.println(params);
        eventMapper.insertRow(params);
    }

    public void delete(Map<String, Integer> map) {
        System.out.println("debug >>> service delete " + eventMapper);
        eventMapper.deleteRow(map);
    }

    public void update(EventRequestDTO params) {
        System.out.println("debug >>> service update " + eventMapper);
        eventMapper.updateRow(params);
    }

    public void savehistory(List<HistoryDaysDTO> params) {
        System.out.println("debug >>> service historysave " + eventMapper);
        eventMapper.savehistoryAll(params);
    }

    public List<EventResponseDTO> getOldestUserEvents(Map<String, Integer> map) {
        System.out.println("유저 오래된 데이터 가져오기 (서비스)");
        return eventMapper.getOldestUserEvents(map);
    }

    public List<GetPostEntity> findPostsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        System.out.println("findPostsByUserIdAndDateRange success");
        return getpostDao.findPostsByUserIdAndDateRange(userId, startDate, endDate);
    }

}
