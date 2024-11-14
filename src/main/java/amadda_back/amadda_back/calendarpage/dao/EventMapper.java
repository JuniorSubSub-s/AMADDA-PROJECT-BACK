package amadda_back.amadda_back.calendarpage.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import amadda_back.amadda_back.calendarpage.domain.EventRequestDTO;
import amadda_back.amadda_back.calendarpage.domain.EventResponseDTO;
import amadda_back.amadda_back.calendarpage.openapi.domain.HistoryDaysDTO;




@Mapper
public interface EventMapper {

    public List<EventResponseDTO> findAllRow(Map<String, String> map) ;

    public List<EventResponseDTO> findlist(Map<String, String> map) ;

    public EventResponseDTO findRow(Map<String, Integer> map) ;

    public void insertRow(EventRequestDTO params) ;

    public void deleteRow(Map<String, Integer> map);

    public void updateRow(EventRequestDTO params);

    public void savehistoryAll(List<HistoryDaysDTO> params);

}
