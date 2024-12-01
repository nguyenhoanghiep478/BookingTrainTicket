package com.backend.store.interfacelayer.service.train.impl;

import com.backend.store.application.model.TrainModel;
import com.backend.store.application.usecase.Train.ModifiedRailcarUseCase;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.state.ModifiedType;
import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;
import com.backend.store.interfacelayer.service.railcar.IFindRailcarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class UpdateTrainServiceTest {
    @Mock
    private ModifiedRailcarUseCase modifiedRailcarUseCase;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateTrainService updateTrainService;
    @InjectMocks
    private IFindRailcarService findRailcarService;

    private Train trainBefore;
    private Train trainAfterAdd;
    private Train trainAfterDetach;
    private TrainModel toAdd;
    private TrainModel toDetach;
    private Railcar railcar;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainBefore = new Train();
        trainBefore.setTotalRailCars(2);
        trainBefore.setCapacity(120);
        trainAfterAdd = new Train();
        trainAfterAdd.setTotalRailCars(3);
        trainAfterAdd.setCapacity(180);
        trainAfterDetach = new Train();
        trainAfterDetach.setTotalRailCars(1);
        trainAfterDetach.setCapacity(60);
        toAdd = new TrainModel();
        toDetach = new TrainModel();
        railcar = new Railcar();
        railcar.setCapacity(60);
    }

    @Test
    void testAddValidRailcarsToTrain() {
        ModifiedRailcarsToTrainRequest request = new ModifiedRailcarsToTrainRequest();
        request.setTrainId(2);
        request.setRailcarIds(Set.of(6));
        when(findRailcarService.getById(2)).thenReturn(railcar);
        when(modelMapper.map(request,TrainModel.class)).thenReturn(toAdd);
        when(modifiedRailcarUseCase.execute(toAdd, ModifiedType.ADD)).thenReturn(trainAfterAdd);

        Railcar addingRailcar = findRailcarService.getById(2);
        Train result = updateTrainService.addRailcarsToTrain(request);
        int oldTotalSeat = trainBefore.getCapacity();
        int oldTotalRailCars = trainBefore.getTotalRailCars();
        int railcarSize = addingRailcar.getCapacity();

        assertEquals(oldTotalSeat + railcarSize, result.getCapacity());
        assertEquals(oldTotalRailCars +1, result.getTotalRailCars());

        System.out.println(String.format("Test thêm toa tàu hợp lệ vào tàu (trước khi thêm có %s toa và %s ghế) Dự kiến %s toa và %s ghế , Thực tế %s toa và %s ghế",oldTotalRailCars,oldTotalSeat,oldTotalRailCars+1,oldTotalSeat+railcarSize,result.getTotalRailCars(),result.getCapacity()));
    }
    @Test
    void testAddNotExistRailcarsToTrain() {
        ModifiedRailcarsToTrainRequest request = new ModifiedRailcarsToTrainRequest();
        request.setTrainId(2);
        request.setRailcarIds(Set.of(7));
        when(modelMapper.map(request,TrainModel.class)).thenReturn(toAdd);
        when(modifiedRailcarUseCase.execute(toAdd, ModifiedType.ADD)).thenReturn(trainAfterAdd);
        Train result = updateTrainService.addRailcarsToTrain(request);

        int oldTotalSeat = trainBefore.getCapacity();
        int oldTotalRailCars = trainBefore.getTotalRailCars();



        assertEquals(oldTotalSeat + 60, result.getCapacity());
        assertEquals(oldTotalRailCars +1, result.getTotalRailCars());


        System.out.println(String.format("Test thêm toa tàu hợp lệ vào tàu (trước khi thêm có %s toa và %s ghế) Dự kiến %s toa và %s ghế , Thực tế %s toa và %s ghế",oldTotalRailCars,oldTotalSeat,oldTotalRailCars+1,oldTotalSeat+60,result.getTotalRailCars(),result.getCapacity()));
    }

    @Test
    void detachValidRailcars() {
        ModifiedRailcarsToTrainRequest request = new ModifiedRailcarsToTrainRequest();
        request.setTrainId(2);
        request.setRailcarIds(Set.of(6));

        when(modelMapper.map(request,TrainModel.class)).thenReturn(toDetach);
        when(modifiedRailcarUseCase.execute(toDetach, ModifiedType.DETACH)).thenReturn(trainAfterDetach);

        Train result = updateTrainService.detachRailcars(request);
        int oldTotalSeat = trainBefore.getCapacity();
        int oldTotalRailCars = trainBefore.getTotalRailCars();
        assertEquals(oldTotalSeat - 60, result.getCapacity());
        assertEquals(oldTotalRailCars - 1, result.getTotalRailCars());
        System.out.println(String.format("Test gỡ toa tàu hợp lệ khỏi tàu (trước khi thêm có %s toa và %s ghế) Dự kiến %s toa và %s ghế , Thực tế %s toa và %s ghế",oldTotalRailCars,oldTotalSeat,oldTotalRailCars-1,oldTotalSeat-60,result.getTotalRailCars(),result.getCapacity()));
    }

}