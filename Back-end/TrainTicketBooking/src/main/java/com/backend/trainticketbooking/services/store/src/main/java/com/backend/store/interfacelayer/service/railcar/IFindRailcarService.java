package com.backend.store.interfacelayer.service.railcar;

import com.backend.store.core.domain.entity.train.Railcar;

import java.util.List;

public interface IFindRailcarService {
    List<Railcar> getAll();
}
