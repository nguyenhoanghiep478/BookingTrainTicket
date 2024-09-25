package com.backend.store.core.domain.entity.schedule;

import com.backend.store.core.domain.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;

    public String toAddress(){
        return street + "," + state + "," + city + ",";
    }
}


