package com.example.learning_english.controller;

import com.example.learning_english.dto.BlogDto;
import com.example.learning_english.entity.Blog;
import com.example.learning_english.service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.example.learning_english.util.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.util.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    @Autowired
    public BlogService blogService;
    @Autowired
    public ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<BlogDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int limit){
        Page<BlogDto> blogDtos = blogService.findAll(page,limit).map(blog->modelMapper.map(blog,BlogDto.class));
        return ResponseEntity.ok(blogDtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BlogDto> save(@Valid @RequestBody BlogDto blogDto){
        Blog blog = modelMapper.map(blogDto,Blog.class);
        blogService.save(blog);
        return ResponseEntity.ok(blogDto);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Blog> blogOptional = blogService.findById(id);
        if (!blogOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        Blog blog = modelMapper.map(blogOptional.get(),Blog.class);
        return ResponseEntity.ok(blog);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody BlogDto blogDto){
        Optional<Blog> blogOptional = blogService.findById(id);
        if (!blogOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Blog exitBlog = blogOptional.get();
        exitBlog.title = blogDto.getTitle();
        exitBlog.synopsis = blogDto.getSynopsis();
        exitBlog.detail = blogDto.getDetail();

        return ResponseEntity.ok(blogService.save(exitBlog));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Blog> blogOptional = blogService.findById(id);

        if (!blogOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        blogService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);
    }
}
