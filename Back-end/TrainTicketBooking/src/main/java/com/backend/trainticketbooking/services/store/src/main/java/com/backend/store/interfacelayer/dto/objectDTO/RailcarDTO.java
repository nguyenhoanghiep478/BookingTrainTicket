package com.backend.store.interfacelayer.dto.objectDTO;

import com.backend.store.core.domain.state.RailcarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RailcarDTO {
    private String railcarName;
    private Integer totalSeat;
    private Integer totalSeatAvailable;
    private RailcarType railcarType;
    private List<ShortSeatDTO> seats;
}
