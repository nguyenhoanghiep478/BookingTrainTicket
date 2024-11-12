package com.backend.store.application.usecase.Seat;

import com.backend.store.application.model.SeatModel;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.SeatInUseException;
import com.backend.store.core.domain.exception.SeatNotExistException;
import com.backend.store.core.domain.repository.ISeatRepository;
import com.backend.store.interfacelayer.dto.objectDTO.HoldingSeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;
import com.backend.store.interfacelayer.redis.IRedisHoldSeatService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateSeatUseCase {
    private static final Logger log = LoggerFactory.getLogger(UpdateSeatUseCase.class);
    private final ISeatRepository seatRepository;
    private final IRedisHoldSeatService redisHoldSeatService;
    public Seat execute(SeatModel model){
        Seat seat =  seatRepository.findById(model.getId()).orElseThrow(
                () -> new SeatNotExistException(String.format("Seat with id %s not found", model.getId()))
        );
      try{
          Seat afterMerge = merge(seat, model);
          seat.setHoldTime(null);

          Seat completedSaved= seatRepository.save(afterMerge);

          redisHoldSeatService.remove(HoldingSeatDTO.builder()
                  .id(completedSaved.getId())
                  .isAvailable(completedSaved.getIsAvailable())
                  .holdingTime(completedSaved.getHoldTime())
                  .build());

          return completedSaved;
      }catch (OptimisticLockException e){
          throw new SeatInUseException(String.format("seat with name %s in transaction",seat.getSeatNumber()));
      }
    }

    public void changeStatus(int seatId, Boolean isAvailable){
        Seat seat = seatRepository.findById(seatId).orElseThrow(
                () -> new SeatNotExistException(String.format("seat with id %s not exist",seatId))
        );
        seat.setIsAvailable(isAvailable);
        seatRepository.save(seat);
    }

    private Seat merge(Seat seat,SeatModel model){
        if(model.getPrice() != null){
            seat.setPrice(model.getPrice());
        }
        if(model.getIsAvailable() != null){
            seat.setIsAvailable(model.getIsAvailable());
        }
        return seat;
    }

    @Transactional(rollbackFor = OptimisticLockException.class)
    public void holdSeat(List<Seat> seats){
        for (Seat seat : seats) {
            try{
                seat.setIsAvailable(false);
                seat.setHoldTime(LocalDateTime.now());
                seatRepository.save(seat);

                HoldingSeatDTO holdingSeatDTO = HoldingSeatDTO.builder()
                        .holdingTime(seat.getHoldTime())
                        .isAvailable(seat.getIsAvailable())
                        .id(seat.getId())
                        .build();
                redisHoldSeatService.add(holdingSeatDTO);
            }catch (OptimisticLockException e){
                throw new SeatInUseException(String.format("seat with name %s in transaction",seat.getSeatNumber()));
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void releaseExpiredSeats() {
        log.info("start check release expired seats");
        List<HoldingSeatDTO> seats = redisHoldSeatService.getALlHoldingSeat();
        if(seats == null){
            return ;
        }
        for (HoldingSeatDTO seat : seats) {
            if (!seat.getIsAvailable() && seat.isHolding()) {
                releaseSeatById(seat.getId());
                redisHoldSeatService.remove(seat);
            }
        }
    }

    private void releaseSeatById(int id){
        Seat seat = seatRepository.findById(id).orElseThrow(()-> new SeatNotExistException(String.format("seat with id %s not exist", id)));
        seat.setHoldTime(null);
        seat.setIsAvailable(true);

        seatRepository.save(seat);
    }
}
