package com.backend.store.infrastructure.servicegateway.impl;

import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StationDTO;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import com.backend.store.interfacelayer.service.station.IStationService;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ToolExcutor {
    private final IScheduleService scheduleService;
    private final IStationService stationService;


    @Tool("""
            get schedule with information :
            departure station name, arrival station name, departure time
            """)
    public List<ScheduleDTO> getSchedule(String departure, String arrival, Timestamp departureTime) {
        return scheduleService.findScheduleByDepartAndArrivalNameAndDepartureTime(departure,arrival,departureTime);
    }

    @Tool("""
            Check station exist in my station with information:
            station name from user input
            """)
    public boolean CheckStationExist(String stationName){
        return stationService.isValidStation(stationName);
    }

    @Tool("""
            Fetch all the stations in system
            """)
    public List<StationDTO> getAllStations() {
        return stationService.getAll();
    }
}
