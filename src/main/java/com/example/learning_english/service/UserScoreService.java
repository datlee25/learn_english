package com.example.learning_english.service;

import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.User;
import com.example.learning_english.entity.UserScore;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.repository.CourseRepository;
import com.example.learning_english.repository.UserScoreRepository;
import com.example.learning_english.specifications.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserScoreService implements BaseService<UserScore> {

    @Autowired
    private UserScoreRepository userScoreRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<UserScore> findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return userScoreRepository.findAll(pageRequest);
    }

    public List<UserScore> findAll() {
        return userScoreRepository.findAll();
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
        UserScore result = userScoreRepository.save(data);

        //TODO: UPDATE number of participants in course
        List<UserScore> userScores = userScoreRepository.findAll();
        Course course = data.getExercise().getCourse();

        for (UserScore userscore :
                userScores) {
            if (userScores.size() == 1) {
                System.out.println("course size: " + 1);
                course.setNumberParticipants(1);
                break;
            }

            if (userscore.getId().getUserId() == data.getId().getUserId() || userscore.getExercise().getCourse_id() == course.getId()) {
                break;
            }
            System.out.println(" entity.getExercise().getCourse_id(): " +  userscore.getExercise().getCourse_id());
            System.out.println("course: " + course.getId());
            System.out.println("getNumberParticipants(): " + course.getNumberParticipants());
            course.setNumberParticipants(course.getNumberParticipants() + 1);
        }
        courseRepository.save(course);
        return result;
    }

    public Page<UserScore> search(SearchRequest searchRequest) {
        SearchSpecification<UserScore> specification = new SearchSpecification<>(searchRequest);
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getLimit());
        return userScoreRepository.findAll(specification, pageRequest);
    }

    public List<UserScore> findByUserId(int userId) {
        return userScoreRepository.findByUserId(userId);
    }
}