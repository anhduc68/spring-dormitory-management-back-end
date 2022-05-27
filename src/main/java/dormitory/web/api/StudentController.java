package dormitory.web.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dormitory.Student;
import dormitory.data.StudentRepository;

@RestController
@RequestMapping(path = "/student", produces = "application/json")
@CrossOrigin(origins = "*")
public class StudentController {
	private StudentRepository studentRepo;

	@Autowired
	public StudentController(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}

	// getAllstudents
	@GetMapping
	public Iterable<Student> getAllstudent() {
		return studentRepo.findAll();
	}

	// getstudentByID
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable("id") Long id) {
		Optional<Student> opt = studentRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	// isExist
	@GetMapping("isExist/{studentID}")
	public String isExitByName(@PathVariable("studentID") String studentID) {
		if (studentRepo.existsByStudentID(studentID))
			return "true";
		return "false";
	}

	// delete
	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable("id") Long id) {
		try {
			studentRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}

	// findByName
	@GetMapping("/findByName/{name}")
	public Iterable<Student> getAllStudentByName(@PathVariable("name") String name) {
		return studentRepo.findByFullnameContains(name);
	}

	// addstudent or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Student postStudent(@RequestBody Student student) {
		return studentRepo.save(student);
	}
	
	//findStudentToRentByTime
	@GetMapping("findStudentToRentByTime/{time}")
	public List<Student> getStudentToRentByTime(@PathVariable("time") String time){
		return studentRepo.findStudentToRentByTime(time);
	}
	
	//findCurrentRentStudent
	@GetMapping("findCurrentRentStudent/{time}")
	public List<Student> getCurrentRentStudent(@PathVariable("time") String time){
		return studentRepo.findCurrentRentStudent(time);
	}
}
