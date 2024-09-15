package com.bookms.order.interfaceLayer.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GenericMapper<Entity,Response,Request>{
    private final ModelMapper modelMapper ;
    public Entity toEntity(Request request,Class<Entity> entityClass){
        return modelMapper.map(request,entityClass);
    }
    public Response toResponse(Entity entity,Class<Response> responseClass){
        return modelMapper.map(entity,responseClass);
    }
    public List<Entity> toListEntity(List<Request> requests, Class<Entity> entityClass){
        return requests.stream().
                map(request -> toEntity(request,entityClass))
                .collect(Collectors.toList());
    }

    public List<Response> toListResponse(List<Entity> entities,Class<Response> responseClass){
        return entities.stream().
                map(entity -> toResponse(entity,responseClass))
                .collect(Collectors.toList());
    }
}
