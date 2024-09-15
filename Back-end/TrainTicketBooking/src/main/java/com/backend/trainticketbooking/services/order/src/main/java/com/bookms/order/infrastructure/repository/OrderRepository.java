package com.bookms.order.infrastructure.repository;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import com.bookms.order.infrastructure.jpaRepository.OrderJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements IOrderRepository {
    private final OrderJpaRepository jpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Orders> findByOrderNumberAndCustomerIdAndOrderType(Long orderNumber, Integer customerId, OrderType orderType) {
        return Optional.empty();
    }

    @Override
    public Optional<Orders> findByOrderNumberAndCustomerId(Long orderNumber, Integer customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Orders> findByOrderNumber(Long orderNumber) {

        return orderJpaRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Optional<Orders> findByIdAndOrderNumberAndCustomerIdAndOrderType(Integer id, Long orderNumber, Integer customerId, OrderType orderType) {
        return Optional.empty();
    }

    @Override
    public Optional<Orders> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Orders> findAllByCustomerIdAndOrderTypeAndStatusAndTotalPriceAndPaymentMethod(Integer customerId, OrderType orderType, Status status, BigDecimal totalPrice, String paymentMethod) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByCustomerIdAndOrderTypeAndStatus(Integer customerId, OrderType orderType, Status status) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByCustomerIdAndOrderType(Integer customerId, OrderType orderType) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByCustomerId(Integer customerId) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByOrderType(OrderType orderType) {
        return List.of();
    }

    @Override
    public List<Orders> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Orders save(Orders orders) {
        return jpaRepository.save(orders);
    }

    @Override
    public List<Orders> findByCriteria(List<Criteria> criteria) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Orders> criteriaQuery = builder.createQuery(Orders.class);
        Root<Orders> root = criteriaQuery.from(Orders.class);
        final Integer[] maxResult = new Integer[1];
        final String[] orderByField = new String[1];
        final Boolean[] ascending = {true};
        AtomicReference<Criteria> groupByCondition = null;

        List<Predicate> predicates = new ArrayList<>();

        criteria.forEach(condition -> {
            switch (condition.getOperator()) {
                case "=": {
                    predicates.add(builder.equal(root.get(condition.getKey()), condition.getValue()));
                    return;
                }
                case "LIKE": {
                    predicates.add(builder.like(root.get(condition.getKey()), "%" + condition.getValue() + "%"));
                    return;
                }
                case "NOT LIKE": {
                    predicates.add(builder.notLike(root.get(condition.getKey()), "%" + condition.getValue() + "%"));
                    return;
                }
                case ">": {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(condition.getKey()), (Comparable) condition.getValue()));
                    return;
                }
                case "<": {
                    predicates.add(builder.lessThanOrEqualTo(root.get(condition.getKey()), (Comparable) condition.getValue()));
                    return;
                }

                case "GET": {
                    maxResult[0] = (Integer) condition.getValue();
                    return;
                }

                case "Order By": {
                    orderByField[0] = condition.getKey();
                    return;
                }

                case "DESC": {
                    ascending[0] = false;
                    return;
                }

            }
        });


        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        if (orderByField[0] != null && !orderByField[0].isEmpty()) {
            if (ascending[0]) {
                criteriaQuery.orderBy(builder.asc(root.get(orderByField[0])));
            } else {
                criteriaQuery.orderBy(builder.desc(root.get(orderByField[0])));
            }
        }

        TypedQuery<Orders> query = em.createQuery(criteriaQuery);

        if (maxResult[0] != null) {
            query.setMaxResults(maxResult[0]);

        }


        return query.getResultList();
    }

    @Override
    public Object findByNativeQuery(String sql) {
        Query query = em.createNativeQuery(sql);

        return (List<?>) query.getResultList();
    }
}
