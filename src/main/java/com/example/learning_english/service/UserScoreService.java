package com.example.learning_english.service;

import com.example.learning_english.dto.UserScore.ResScoreBoard;
import com.example.learning_english.dto.UserScore.ResUserScoreDto;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.UserScore;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.repository.CourseRepository;
import com.example.learning_english.repository.UserScoreRepository;
import com.example.learning_english.specifications.SearchSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.learning_english.ultils.SortValueTreeMap.sortByValues;

@Service
public class UserScoreService implements BaseService<UserScore> {

    @Autowired
    private UserScoreRepository userScoreRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<UserScore> findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return userScoreRepository.findAll(pageRequest);
    }

    public ResScoreBoard findTop10ByOrderByScoreDesc(int id){
        List<UserScore> userScores = userScoreRepository.findAll();
        Map<String,BigDecimal> currentUserScore = countOccurrences(userScoreRepository.findByUserId(id));

        Map<String,BigDecimal> top10User= sortByValues(countOccurrences(userScores));

        int numElements = 2;
        Set<String> targetKeyset = new HashSet<>();

        for (String key: top10User.keySet()){
            if (numElements<=0){break;}
            targetKeyset.add(key);
            numElements--;
        }

        top10User.keySet().retainAll(targetKeyset);
        return new ResScoreBoard(top10User,currentUserScore);
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

        //TODO: UPDATE number of participants in course
        List<UserScore> userScores = userScoreRepository.findAll();

        Course course = data.getExercise().getCourse();
        if (userScores.size() == 0) {
            course.setNumberParticipants(1);
        }


//        ArrayUtils.contains()
//        for (UserScore userscore :
//                userScores) {
//            System.out.println("ten: " + userscore.getName());
//            System.out.println();
//            if ( userscore.getExercise().getCourse_id() != course.getId() || userscore.getId().getUserId() != data.getId().getUserId()) {
//                System.out.println(" entity.getExercise().getCourse_id(): " +  userscore.getExercise().getCourse_id());
//                System.out.println("course: " + course.getId());
//                System.out.println("getNumberParticipants(): " + course.getNumberParticipants());
//                course.setNumberParticipants(course.getNumberParticipants() + 1);
//
//                break;
//            }
//            break;
//        }
        UserScore result = userScoreRepository.save(data);
//        courseRepository.save(course);
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
    public static Map<String,BigDecimal> countOccurrences(List<UserScore> userScores){
        Map<String,BigDecimal> map = new TreeMap<>();
        for (UserScore userScore :
                userScores) {
            String name =userScore.getUser().getUsername();
            BigDecimal score =userScore.getScore();
            if (map.containsKey(name)){
                BigDecimal totalScore = map.get(name).add(score);
                map.put(name,totalScore);
            }else{
                map.put(name,score);
            }
        }
        return map;

    }
}