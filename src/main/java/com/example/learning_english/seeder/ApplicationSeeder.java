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
        int numberOfCourse = 15;
        for (int i = 0; i< numberOfCourse; i++){
            Course course = new Course();
            course.setTitle(faker.name().title());
            course.setDetail(faker.lorem().sentence());
            course.setImage("data:image/png;base64,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");
            course.setParticipantAge(faker.number().numberBetween(5,30));
            course.setQualification(faker.number().numberBetween(1,10));
            course.setStars_rated(faker.number().numberBetween(1,5));
            course.setGroupsId(faker.number().numberBetween(0,4));
            courseList.add(course);
        }
        courseService.saveAll(courseList);
    }

    public void seedQuestion(){
        List<Exercise> exerciseList = exerciseService.findAll();
        List<Question> questionList = new ArrayList<>();
        int numberSeed = 15;

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
