package amadda_back.amadda_back.calendarpage.openapi.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HistoryDaysDTO {

    private String dateName;

    private String locdate;

}
