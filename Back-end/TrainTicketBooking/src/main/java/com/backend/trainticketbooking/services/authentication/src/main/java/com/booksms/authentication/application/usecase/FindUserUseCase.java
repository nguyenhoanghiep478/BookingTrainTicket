package com.booksms.authentication.application.usecase;

import com.booksms.authentication.application.BaseUsecase;
import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FindUserUseCase implements BaseUsecase<List<SearchUserCriteria>, List<UserCredential>> {
    private final IUserRepository userRepository;
    @Override
    public List<UserCredential> execute(List<SearchUserCriteria> criteriaList) {
        if(criteriaList == null || criteriaList.isEmpty()){
            return userRepository.findAll();
        }
        return userRepository.search(criteriaList);
    }
    public UserCredential findByUserName(String email) {
        SearchUserCriteria criteria = SearchUserCriteria.builder()
                .key("email")
                .operation(":")
                .value(email)
                .build();

        List<UserCredential> list = execute(List.of(criteria));
        return list.isEmpty() ? null : list.get(0);
    }
    public UserCredential findById(Integer deleteUserId) {
        SearchUserCriteria criteria = SearchUserCriteria.builder()
                .key("id")
                .operation(":")
                .value(deleteUserId)
                .build();

        List<UserCredential> list = execute(List.of(criteria));
        return list.isEmpty() ? null : list.get(0);
    }
}
