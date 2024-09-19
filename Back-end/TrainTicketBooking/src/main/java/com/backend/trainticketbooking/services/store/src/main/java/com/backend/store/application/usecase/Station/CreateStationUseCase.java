package com.backend.store.application.usecase.Station;

import com.backend.store.application.model.AddressModel;
import com.backend.store.application.model.StationModel;
import com.backend.store.core.domain.entity.Address;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.exception.StationExistedException;
import com.backend.store.core.domain.repository.IStationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CreateStationUseCase {
    private final IStationRepository stationRepository;

    public Station execute(StationModel stationModel) {
        Optional<Station> station = stationRepository.findByName(stationModel.getName());
        if(station.isPresent()){
            throw new StationExistedException(String.format("Station %s already existed", stationModel.getName()));
        }

        Station newStation = map(stationModel);

        return stationRepository.save(newStation);
    }

    private Station map(StationModel stationModel) {
        Station station = new Station();
        station.setName(stationModel.getName());

        AddressModel addressModel = stationModel.getAddress();
        Address address = new Address();
        address.setState(addressModel.getState());
        address.setCity(addressModel.getCity());
        address.setStreet(addressModel.getStreet());

        station.setAddress(address);

        return station;
    }

}
