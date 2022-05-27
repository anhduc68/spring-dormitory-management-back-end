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

import dormitory.UsedService;
import dormitory.data.UsedServiceRepository;

@RestController
@RequestMapping(path = "/usedService", produces = "application/json")
@CrossOrigin(origins = "*")
public class UsedServiceController {
	private UsedServiceRepository UsedServiceRepo;

	@Autowired
	public UsedServiceController(UsedServiceRepository UsedServiceRepo) {
		this.UsedServiceRepo = UsedServiceRepo;
	}

	// getAllUsedServices
	@GetMapping
	public Iterable<UsedService> getAllUsedService() {
		return UsedServiceRepo.findAll();
	}

	// getUsedServiceByID
	@GetMapping("/{id}")
	public UsedService getUsedServiceById(@PathVariable("id") Long id) {
		Optional<UsedService> opt = UsedServiceRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	// delete
	@DeleteMapping("/{id}")
	public void deleteUsedService(@PathVariable("id") Long id) {
		try {
			UsedServiceRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}

	// addUsedService or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UsedService postUsedService(@RequestBody UsedService UsedService) {
		return UsedServiceRepo.save(UsedService);
	}

	// findUsedServiceByServiceIDAndTime
	@GetMapping("/findUsedServiceByServiceIDAndTime/{service_id}/{time}")
	public List<UsedService> findUsedServiceByServiceIDAndTime(@PathVariable("service_id") Long service_id,
			@PathVariable("time") String time) {
		return UsedServiceRepo.findUsedServiceByServiceIDAndTime(service_id, time);
	}

	// findUsedServiceByStudentIDAndTime
	@GetMapping("/findUsedServiceByStudentIDAndTime/{student_id}/{time}")
	public List<UsedService> findUsedServiceByStudentIDAndTime(@PathVariable("student_id") Long student_id,
			@PathVariable("time") String time) {
		return UsedServiceRepo.findUsedServiceByStudentIDAndTime(student_id, time);
	}
}
