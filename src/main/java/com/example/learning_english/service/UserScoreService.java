package com.example.learning_english.service;

import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.UserScore;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.repository.UserScoreRepository;
import com.example.learning_english.specifications.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserScoreService implements BaseService<UserScore> {

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Override
    public Page<UserScore> findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return userScoreRepository.findAll(pageRequest);
    }

    @Override
    public Optional<UserScore> findById(int id) {
        return userScoreRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        userScoreRepository.deleteById(id);
    }

    @Override
    public UserScore save(UserScore data) {
        data.setCreateAt(LocalDateTime.now());
        data.setUpdateAt(LocalDateTime.now());
        return userScoreRepository.save(data);
    }

    public Page<UserScore> search(SearchRequest searchRequest) {
        SearchSpecification<UserScore> specification = new SearchSpecification<>(searchRequest);
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getLimit());
        return userScoreRepository.findAll(specification, pageRequest);
    }
}