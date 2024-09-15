package com.bookms.order.infrastructure.repository;

import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Repository.IOrderItemRepository;
import com.bookms.order.core.domain.State.StaticVar;
import com.bookms.order.infrastructure.jpaRepository.OrderItemJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository implements IOrderItemRepository {
    private final OrderItemJpaRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Integer> findTopSales() {
        String jpql = "SELECT oi.bookId " +
                "FROM OrderItems oi " +
                "GROUP BY oi.bookId  " +
                "ORDER BY COUNT(oi) DESC ";
        return  em.createQuery(jpql, Integer.class).setMaxResults(StaticVar.AMOUNT_TOP_SALES).getResultList();
    }
}
