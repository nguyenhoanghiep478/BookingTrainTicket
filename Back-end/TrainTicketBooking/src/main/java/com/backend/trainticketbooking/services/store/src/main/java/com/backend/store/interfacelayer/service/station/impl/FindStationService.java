package com.backend.store.interfacelayer.service.station.impl;

import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.service.station.IFindStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindStationService implements IFindStationService {
    private final FindStationUseCase findStationUseCase;

    @Override
    public List<Station> getAll() {
        return findStationUseCase.execute(null);
    }
}
