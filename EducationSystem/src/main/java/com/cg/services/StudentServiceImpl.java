package com.cg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.entities.Student;
import com.cg.exception.AuthenticationFailureException;
import com.cg.exception.ResourceAlreadyExistException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	public boolean requestRegistration(Student student) {
		boolean isRequestMade = false;
		if (studentRepository.findByEmailId(student.getEmailId()) != null) {
		throw new ResourceAlreadyExistException("Email already taken!! Please use other email");
		} else if (studentRepository.findByUserName(student.getUserName()) != null) {
		throw new ResourceAlreadyExistException("Username already taken!! Please use other username");
		} else if (!(student.getPassword().equals(student.getPassword()))) {
		throw new ResourceNotFoundException("Password and Confirm password doesn't matched");
		} else {
		studentRepository.saveAndFlush(student);
		isRequestMade = true;
		}
		return isRequestMade;



		}

	@Override
	public Student studentLogin(String username, String password) {

		Student student = studentRepository.findByUserNameAndPassword(username, password);

		if (student == null) {
			throw new AuthenticationFailureException("Username or password is incorrect");
		}

		return student;
	}

	@Override
	public List<Student> getAllStudents() {

		List<Student> students = studentRepository.findAll();

		return students;
	}

	@Override
	public Student saveStudent(Student student) {

		Student savedStudent = studentRepository.save(student);

		return savedStudent;
	}

	@Override
	public Student getStudentById(int studentId) {

		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent == null) {
			throw new ResourceNotFoundException("Student not exising with id: " + studentId);
		}

		Student student = optionalStudent.get();

		return student;
	}

	@Override
	public void deleteStudent(int studentId) {

		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent == null) {
			throw new ResourceNotFoundException("Student not exising with id: " + studentId);
		}

		Student student = optionalStudent.get();

		studentRepository.delete(student);

	}

	@Override
	public Student updateStudent(Student student) {

		Optional<Student> optionalStudent = studentRepository.findById(student.getStudentId());

		if (optionalStudent == null) {
			throw new ResourceNotFoundException("Student not exising with id: " + student.getStudentId());
		}

		Student updatedStudent = studentRepository.save(student);

		return updatedStudent;
	}

}
