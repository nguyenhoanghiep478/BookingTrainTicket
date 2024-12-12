package com.backend.store.application.usecase.Station;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.exception.StationNotExistException;
import com.backend.store.core.domain.repository.IStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindStationUseCase {
    private final IStationRepository stationRepository;

    public List<Station> execute(List<Criteria> criteria){
        if(criteria == null || criteria.isEmpty()){
            return stationRepository.findAll();
        }
        return stationRepository.findBy(criteria);
    }


    public List<Station> getListStationInIds(List<Integer> ids){
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation("IN")
                .value(ids)
                .build();
        List<Station> stations = execute(List.of(criteria));

        if(stations.size() != ids.size()){
            List<Integer> stationIdsFound = stations.stream()
                    .map(Station::getId)
                    .toList();
            List<Integer> stationNotExist = ids.stream()
                    .filter(id -> !stationIdsFound.contains(id))
                    .toList();
            throw new StationNotExistException("The following station IDs do not exist: " + stationNotExist);
        }
        if(stations.get(0).getId() < ids.get(0)){
            stations.sort((o1, o2) -> o2.getId() - o1.getId());
        }else if (stations.get(0).getId() > ids.get(0)){
            stations.sort((o1, o2) -> o1.getId() - o2.getId());
        }

        return stations;
    }

    public Station getStationById(int id){
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation(":")
                .value(id)
                .build();

        return execute(List.of(criteria)).get(0);
    }

    public Station getStationByName(String departureStation) {
        Criteria criteria = Criteria.builder()
                .key("name")
                .operation("LIKE")
                .value(departureStation)
                .build();
        List<Station> stations = execute(List.of(criteria));
        return stations== null ? null : stations.get(0);
    }
}
