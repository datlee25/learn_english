package com.example.learning_english.seeder;

import com.example.learning_english.entity.Answer;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.enums.EAnswerKey;
import com.example.learning_english.service.AnswerService;
import com.example.learning_english.service.CourseService;
import com.example.learning_english.service.ExerciseService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    private final Faker faker = new Faker();
    @Autowired
    private CourseService courseService;

    @Autowired
    private AnswerService answerService;
    @Autowired
    private ExerciseService exerciseService;



    private void seedCourse(){
        List<Course> courseList = new ArrayList<>();
        int numberOfCourse = 50;
        for (int i = 0; i< numberOfCourse; i++){
            Course course = new Course();
            course.setTitle(faker.name().title());
            course.setDetail(faker.lorem().sentence());
            course.setImage(faker.avatar().image());
            course.setParticipantAge(faker.number().numberBetween(5,30));
            course.setQualification(faker.number().numberBetween(1,10));
            course.setNumber_of_participants(faker.number().numberBetween(100,200));
            course.setStars_rated(faker.number().numberBetween(1,5));
            courseList.add(course);
        }
        courseService.saveAll(courseList);
    }

    public void seedAnswer(){
        List<Answer> answerList = new ArrayList<>();
        List<Exercise> exerciseList = exerciseService.findAll();

        for (int i = 0;i<exerciseList.size();i++){
            for (EAnswerKey answerKey : EAnswerKey.values()){
                Answer answer = new Answer();
                answer.setAnswer_key(answerKey);
                answer.setAnswer_value(faker.lorem().sentence());
                answer.setCorrect_answer(i % 3 == 0);
                answer.setExercise(exerciseList.get(i));
                answerList.add(answer);
            }
        }
        answerService.saveAll(answerList);
    }

    public void seedExercise(){
        List<Exercise> exerciseList = new ArrayList<>();
        List<Course> courseList = courseService.findAll();

        for (Course value : courseList) {
            int numberOfExercise = faker.number().numberBetween(1, 5);
            for (int j = 0; j < numberOfExercise; j++) {
                Exercise exercise = new Exercise();
                exercise.setQuestion(faker.name().title());
                exercise.setCourse(value);
                exercise.setCourse_id(value.getId());
                exerciseList.add(exercise);
            }
        }
        exerciseService.saveAll(exerciseList);
    }

    @Override
    public void run(String... args) throws Exception {
//        answerService.deleteAll();
//        exerciseService.deleteAll();
//        courseService.deleteAll();
//
//        seedCourse();
//        seedExercise();
//        seedAnswer();
    }
}
