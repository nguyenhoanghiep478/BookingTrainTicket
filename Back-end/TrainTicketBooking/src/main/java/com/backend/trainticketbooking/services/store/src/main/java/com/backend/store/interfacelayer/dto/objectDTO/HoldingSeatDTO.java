package com.backend.store.interfacelayer.dto.objectDTO;

import com.backend.store.core.domain.state.StaticVar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoldingSeatDTO {
    private int id;
    private Boolean isAvailable;
    private LocalDateTime holdingTime;

    public boolean isHolding(){
        if(holdingTime == null){
            return false;
        }
        return holdingTime.isBefore(LocalDateTime.now().minusMinutes(StaticVar.HOLDING_SEAT_MINUTES));
    }
}
