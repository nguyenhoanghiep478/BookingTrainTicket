package com.backend.store.interfacelayer.service.station;

import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.dto.objectDTO.StationDTO;

import java.util.List;

public interface IFindStationService {
    List<Station> getAll();
}
