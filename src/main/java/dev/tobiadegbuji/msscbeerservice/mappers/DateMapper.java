package dev.tobiadegbuji.msscbeerservice.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

//Custom Mapping between to date conversions

@Component
public class DateMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp timestamp){
        if(timestamp != null){
            LocalDateTime tsDateTime = timestamp.toLocalDateTime();
            return OffsetDateTime.of(tsDateTime.getYear(), tsDateTime.getMonthValue(), tsDateTime.getDayOfMonth(),
                    tsDateTime.getHour(), tsDateTime.getMinute(), tsDateTime.getSecond(), tsDateTime.getNano(), ZoneOffset.UTC);
        }
        else{
            return null;
        }
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if(offsetDateTime != null)
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        else
            return null;
    }

}
