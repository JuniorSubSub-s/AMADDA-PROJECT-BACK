package amadda_back.amadda_back.calendarpage.domain;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EventRequestDTO {
    private int         calId ;
    private LocalDate  day ;
    private String title ;
    private String content ;
    private String color ;
    private String address ;


}
