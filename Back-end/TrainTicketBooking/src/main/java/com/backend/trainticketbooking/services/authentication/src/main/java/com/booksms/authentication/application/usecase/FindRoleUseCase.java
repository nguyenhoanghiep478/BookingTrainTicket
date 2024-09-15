package com.booksms.authentication.application.usecase;

import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.core.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindRoleUseCase {
    private final IRoleRepository repository;

    public List<Role> execute(List<SearchUserCriteria> criteriaList) {
        if(criteriaList == null || criteriaList.isEmpty()) {
            return repository.findAll();
        }
        return repository.findByCriteria(criteriaList);
    }
}
