package com.cg.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entities.Course;

import com.cg.services.CourseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/edu/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping("/courses")
	public List<Course> fetchAllCourses() {

		List<Course> courses = courseService.getAllCourses();
		return courses;
	}

	@PostMapping("/add")
	public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course) {

		Course newCourse = courseService.saveCourse(course);
		ResponseEntity<Course> responseEntity = new ResponseEntity<>(newCourse, HttpStatus.CREATED);
		return responseEntity;
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<?> fetchCourseById(@PathVariable("id") int courseId) {

		ResponseEntity<?> responseEntity = null;
		Course course = courseService.getCourseById(courseId);
		responseEntity = new ResponseEntity<>(course, HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCourseById(@PathVariable("id") int courseId) {

		ResponseEntity<String> responseEntity = null;
		courseService.deleteCourseById(courseId);
		responseEntity = new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
		return responseEntity;
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateCourse(@RequestBody Course course) {

		ResponseEntity<Object> responseEntity = null;
		Course updatedCourse = courseService.updateCourse(course);
		responseEntity = new ResponseEntity<>(updatedCourse, HttpStatus.OK);
		return responseEntity;
	}

}
