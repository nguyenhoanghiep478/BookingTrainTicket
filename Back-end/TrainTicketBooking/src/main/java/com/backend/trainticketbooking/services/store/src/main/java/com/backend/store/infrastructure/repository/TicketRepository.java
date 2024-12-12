package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.repository.ITicketRepository;
import com.backend.store.infrastructure.jpaRepository.TicketJpaRepository;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TicketRepository extends AbstractRepository<Ticket> implements ITicketRepository {
    private final TicketJpaRepository ticketJpaRepository;
    TicketRepository(Class<Ticket> entityClass, TicketJpaRepository ticketJpaRepository) {
        super(entityClass);
        this.ticketJpaRepository = ticketJpaRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketJpaRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketJpaRepository.findAll();
    }

    @Override
    public List<Ticket> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }

    @Override
    public List<Ticket> findByScheduleId(Schedule schedule) {
        return ticketJpaRepository.findBySchedule(schedule);
    }

    @Override
    public List<Object[]> findTicketBy1Hour() {
        return ticketJpaRepository.findTicketByOneHour();
    }

    @Override
    public List<Ticket> findByDepartureIdAndArrivalIdAndDepartureTime(Integer id, Integer id1, Timestamp departureTime) {
        return ticketJpaRepository.findTickets(id,id1,departureTime);
    }

    @Override
    public List<Object[]> findHourlyRevenueByDate(int year,int month,int day) {
        return ticketJpaRepository.findHourlyRevenueByDate(year,month,day);
    }

    @Override
    public List<Object[]> findDatelyRevenueInMonth(int year, int month) {
        return ticketJpaRepository.findDailyRevenueByMonth(year,month);
    }

    @Override
    public List<Object[]> findDatelyRevenueInYear(int year) {
        return ticketJpaRepository.findMonthlyRevenueByYear(year);
    }


}
