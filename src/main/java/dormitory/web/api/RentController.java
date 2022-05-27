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

import dormitory.Rent;
import dormitory.UsedService;
import dormitory.data.RentRepository;

@RestController
@RequestMapping(path = "/rent", produces = "application/json")
@CrossOrigin(origins = "*")
public class RentController {
	private RentRepository RentRepo;
	@Autowired
	public RentController(RentRepository RentRepo) {
		this.RentRepo = RentRepo;
	}
	// getAllRents
		@GetMapping
		public Iterable<Rent> getAllRent() {
			return RentRepo.findAll();
		}

		// getRentByID
		@GetMapping("/{id}")
		public Rent getRentById(@PathVariable("id") Long id) {
			Optional<Rent> opt = RentRepo.findById(id);
			if (opt.isPresent()) {
				return opt.get();
			}
			return null;
		}
		// delete
		@DeleteMapping("/{id}")
		public void deleteRent(@PathVariable("id") Long id) {
			try {
				RentRepo.deleteById(id);
			} catch (EmptyResultDataAccessException e) {

			}
		}
		//addRent or Update
		@PostMapping(consumes = "application/json")
		@ResponseStatus(HttpStatus.CREATED)
		public Rent postRent(@RequestBody Rent Rent) {
				return RentRepo.save(Rent);
		}
		
		// findRentByStudentIDAndTime
		@GetMapping("/findRentByStudentIDAndTime/{student_id}/{time}")
		public List<Rent> findRentByStudentIDAndTime(@PathVariable("student_id") Long student_id,
				@PathVariable("time") String time) {
			return RentRepo.findRentByStudentIDAndTime(student_id, time);
		}
}
