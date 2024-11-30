package com.backend.store.interfacelayer.service.schedule.impl;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.objectDTO.RailcarDTO;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.objectDTO.ShortSeatDTO;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;
import com.backend.store.interfacelayer.service.schedule.ICreateScheduleService;
import com.backend.store.interfacelayer.service.schedule.IFindScheduleService;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import com.backend.store.interfacelayer.service.station.impl.FindStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final ICreateScheduleService createScheduleService;
    private final IFindScheduleService findScheduleService;
    private final IFindSeatService findSeatService;
    private final FindStationService findStationService;
    private Integer totalAvailableSeats;
    @Override
    public CreateScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = createScheduleService.create(request);
        return CreateScheduleResponse.builder()
                .routeName(schedule.getRoute().getName())
                .trainName(schedule.getRoute().getName())
                .build();
    }

    @Override
    public List<ScheduleDTO> findScheduleById(Integer id) {
        Schedule schedule = findScheduleService.findById(id);
        List<AvailableSeatNumberForSchedule> totalAvailableSeat = findSeatService.findTotalAvailableSeatAtSchedule(id);
        return toSchedulesDTO(schedule,totalAvailableSeat);
    }

    @Override
    public ScheduleDTO toShortScheduleDTO(Schedule schedule) {
        ScheduleStation departureScheduleStation = schedule.getScheduleStations().get(0);
        ScheduleStation arrivalScheduleStation = schedule.getScheduleStations().get(schedule.getScheduleStations().size() - 1);
        if(departureScheduleStation.getStopSequence() > arrivalScheduleStation.getStopSequence()){
            ScheduleStation temp = departureScheduleStation;
            departureScheduleStation = arrivalScheduleStation;
            arrivalScheduleStation = temp;
        }
        return ScheduleDTO.builder()
                .departureStationName(departureScheduleStation.getStation().getName())
                .arrivalStationName(arrivalScheduleStation.getStation().getName())
                .arrivalTime(arrivalScheduleStation.getArrivalTime())
                .departureTime(departureScheduleStation.getDepartureTime())
                .build();
    }

    @Override
    public List<ScheduleDTO> findRoundTrip(Integer departureStationId, Integer arrivalStationId,Integer scheduleId) {
        // find round trip depend on arrival time at schedule id
        List<Schedule> schedules = findScheduleService.findRoundTrip(departureStationId,arrivalStationId,scheduleId);

        if(schedules == null){
            return null;
        }
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule : schedules){
            // swap arrival and departure because we want to move back from old trip
            scheduleDTOS.add(toScheduleDTOWithOnlyAt(schedule,arrivalStationId,departureStationId,schedule.getId()));
        }

        return scheduleDTOS;
    }

    @Override
    public List<ScheduleDTO> findScheduleByDepartAndArrival(Integer departureStationId, Integer arrivalStationId) {
        List<Schedule> schedules = findScheduleService.findByDepartAndArrival(departureStationId,arrivalStationId);
        if(schedules == null){
            return null;
        }
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule : schedules){
            scheduleDTOS.add(toScheduleDTOWithOnlyAt(schedule,departureStationId,arrivalStationId,schedule.getId()));
        }

        return scheduleDTOS;
    }

    @Override
    public List<ScheduleDTO> findScheduleByDepartAndArrivalAndDepartureTime(Integer departureStationId, Integer arrivalStationId, Timestamp departureTime) {
        List<ScheduleDTO> scheduleDTOS = findScheduleByDepartAndArrival(departureStationId,arrivalStationId);
        if(departureTime == null){
            return scheduleDTOS;
        }
        return scheduleDTOS.stream().filter(scheduleDTO -> scheduleDTO.getDepartureTime().after(departureTime)).toList();
    }

    @Override
    public List<ScheduleDTO> findScheduleByDepartAndArrivalNameAndDepartureTime(String departureStation, String arrivalStation, Timestamp sqlTimestamp) {
        Station departureStationObject = findStationService.findByName(departureStation);
        Station arrivalStationObject = findStationService.findByName(arrivalStation);

        return findScheduleByDepartAndArrivalAndDepartureTime(departureStationObject.getId(),arrivalStationObject.getId(),sqlTimestamp);
    }

    @Override
    public Map<Integer,List<ScheduleDTO>> findAllAvailableSchedules() {
        List<Schedule> schedules = findScheduleService.findAllAvailable();
        Map<Integer,List<ScheduleDTO>> scheduleDTOMap = new HashMap<>();
        for(Schedule schedule : schedules){
            schedule.getScheduleStations().sort(Comparator.comparingInt(ScheduleStation::getStopSequence));
            List<AvailableSeatNumberForSchedule> totalAvailableSeat = findSeatService.findTotalAvailableSeatAtSchedule(schedule.getId());
            List<ScheduleDTO> items = toSchedulesDTO(schedule,totalAvailableSeat);
            scheduleDTOMap.put(schedule.getId(),items);
        }
        return scheduleDTOMap;
    }

    private ScheduleDTO toScheduleDTOWithOnlyAt(Schedule schedule,Integer departureStationId,Integer arrivalStationId,Integer scheduleId
    ) {
        ScheduleStation departureScheduleStation = schedule.getScheduleStations().stream().filter(scheduleStation -> scheduleStation.getStation().getId().equals(departureStationId)).findFirst().get();
        ScheduleStation arrivalScheduleStation = schedule.getScheduleStations().stream().filter(scheduleStation -> scheduleStation.getStation().getId().equals(arrivalStationId)).findFirst().get();
        List<RailcarDTO> railcarDTOS = findSeatService.getListRailcarWithSeatAvailableIn(departureStationId, arrivalStationId, scheduleId);
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .departureStationId(departureStationId)
                .arrivalStationId(arrivalStationId)
                .arrivalStationName(arrivalScheduleStation.getStation().getName())
                .departureStationName(departureScheduleStation.getStation().getName())
                .trainName(schedule.getTrain().getTrainName())
                .departureTime(departureScheduleStation.getDepartureTime())
                .arrivalTime(arrivalScheduleStation.getArrivalTime())
                .railcars(railcarDTOS)
                .build();
    }

    private List<ScheduleDTO> toSchedulesDTO(Schedule schedule,List<AvailableSeatNumberForSchedule> totalAvailableSeat) {
        List<ScheduleDTO> response = new ArrayList<>();
        String trainName = schedule.getTrain().getTrainName();
        int totalRailcars = schedule.getTrain().getRailcars().size();
        int index = 0;
        for(int i= 0 ;i < schedule.getScheduleStations().size() - 1;i++) {
            ScheduleStation departureScheduleStation = schedule.getScheduleStations().get(i);
            ScheduleStation arrivalScheduleStation = schedule.getScheduleStations().get(i+1);

            List<RailcarDTO> railcars = new ArrayList<>();

            for(int j = index * totalRailcars; j < index * totalRailcars + totalRailcars; j++ ){

               List<ShortSeatDTO> seats = getSeatFromRailcarNameInTrain(schedule.getTrain(),totalAvailableSeat.get(j).getRailcarName(),totalAvailableSeat.get(j));

               RailcarDTO railcarDTO = RailcarDTO.builder()
                       .railcarName(totalAvailableSeat.get(j).getRailcarName())
                       .seats(seats)
                       .totalSeat(seats.size())
                       .totalSeatAvailable(totalAvailableSeats)
                       .build();
                railcars.add(railcarDTO);
           }
           index ++;

            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .departureStationId(departureScheduleStation.getStation().getId())
                    .arrivalStationId(arrivalScheduleStation.getStation().getId())
                    .departureStationName(departureScheduleStation.getStation().getName())
                    .arrivalStationName(arrivalScheduleStation.getStation().getName())
                    .departureTime(departureScheduleStation.getDepartureTime())
                    .arrivalTime(arrivalScheduleStation.getArrivalTime())
                    .trainName(trainName)
                    .railcars(railcars)
                    .build();
            response.add(scheduleDTO);
        }
        return response;



    }

    private List<ShortSeatDTO> getSeatFromRailcarNameInTrain(Train train,String railcarName,AvailableSeatNumberForSchedule availableSeat) {
        totalAvailableSeats = 0;
        Railcar railcar = train.getRailcars().stream().filter(currentRailcar -> currentRailcar.getName().equals(railcarName)).findFirst().get();
        List<ShortSeatDTO> shortSeatDTOS = new ArrayList<>();
        boolean isAvailable = true;
        List<String> currentAvailableSeats = availableSeat.getSeatIds();
        for(Seat seat : railcar.getSeats()){
            if(currentAvailableSeats.contains(String.valueOf(seat.getId()))){
                totalAvailableSeats++;
                isAvailable = true;
            }else{
                isAvailable = false;
            }

            ShortSeatDTO shortSeatDTO = ShortSeatDTO.builder()
                    .id(seat.getId())
                    .seatNumber(seat.getSeatNumber())
                    .isAvailable(isAvailable)
                    .build();
            shortSeatDTOS.add(shortSeatDTO);
        }
        shortSeatDTOS.sort((a,b) -> a.getId() -b.getId());
        return shortSeatDTOS;
    }


    private List<ShortSeatDTO> getShortSeatDTOFromAvailableSeatForSchedule(AvailableSeatNumberForSchedule availableSeatNumberForSchedule){
        List<ShortSeatDTO> seatAvailable = new ArrayList<>();
        Boolean isAvailable = true;
        for(int i = 0 ; i < availableSeatNumberForSchedule.getSeatNumbers().size() ; i++){
            ShortSeatDTO shortSeatDTO = ShortSeatDTO.builder()
                    .seatNumber(availableSeatNumberForSchedule.getSeatNumbers().get(i))
                    .id(Integer.parseInt(availableSeatNumberForSchedule.getSeatIds().get(i)))
                    .build();
            seatAvailable.add(shortSeatDTO);
        }
        return seatAvailable;
    }
}
