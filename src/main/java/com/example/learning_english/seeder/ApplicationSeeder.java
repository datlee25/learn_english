package com.example.learning_english.seeder;

import com.example.learning_english.entity.*;
import com.example.learning_english.entity.enums.EAnswerKey;
import com.example.learning_english.entity.enums.ERole;
import com.example.learning_english.service.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class  ApplicationSeeder implements CommandLineRunner {
    private final Faker faker = new Faker();
    @Autowired
    private CourseService courseService;

    @Autowired
    private AnswerService answerService;
    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private QuestionService questionService;



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

    public void seedQuestion(){
        List<Exercise> exerciseList = exerciseService.findAll();
        List<Question> questionList = new ArrayList<>();
        int numberSeed = 50;

        for (Exercise value: exerciseList){
            for (int i = 0; i <= numberSeed; i++){
                Question question = new Question();
                question.setQuestion(faker.name().title());
                question.setExercise_id(value.getId());
                question.setExercise(value);
                questionList.add(question);
            }
        }

        questionService.saveAll(questionList);

    }

    public void seedAnswer(){
        List<Answer> answerList = new ArrayList<>();
        List<Question> questionList = questionService.findAll();

        for (Question value: questionList){
            for (EAnswerKey answerKey : EAnswerKey.values()){
                Answer answer = new Answer();
                answer.setAnswer_key(answerKey);
                answer.setAnswer_value(faker.lorem().sentence());
                answer.setCorrect_answer(new Random().nextBoolean());
                answer.setQuestion_id(value.getId());
                answer.setQuestion(value);
                answerList.add(answer);
            }
        }
        answerService.saveAll(new HashSet<>(answerList));
    }

    public void seedExercise(){
        List<Exercise> exerciseList = new ArrayList<>();
        List<Course> courseList = courseService.findAll();

        for (Course value : courseList) {
            int numberOfExercise = faker.number().numberBetween(1, 5);

            for (int i = 0; i < numberOfExercise; i++) {
                Exercise exercise = new Exercise();
                exercise.setCourse(value);
                exercise.setName(faker.name().title());
                exercise.setDescription(faker.lorem().sentence());
                exercise.setCourse_id(value.getId());
                exerciseList.add(exercise);
            }
        }
        exerciseService.saveAll(exerciseList);
    }

    public void seedRole(){
        Role user_role = new Role(ERole.ROLE_USER);
        Role admin_role = new Role(ERole.ROLE_ADMIN);
        roleService.save(user_role);
        roleService.save(admin_role);
    }

    @Override
    public void run(String... args) throws Exception {
//        roleService.deleteAll();
//        answerService.deleteAll();
//        questionService.deleteAll();
//        exerciseService.deleteAll();
//        courseService.deleteAll();
//
//        seedCourse();
//        seedExercise();
//        seedQuestion();
//        seedAnswer();

//        seedRole();
    }
}
