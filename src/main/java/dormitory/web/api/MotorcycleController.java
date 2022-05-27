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

import dormitory.Motorcycle;
import dormitory.data.MotorcycleRepository;

@RestController
@RequestMapping(path = "/motorcycle", produces = "application/json")
@CrossOrigin(origins = "*")
public class MotorcycleController {
	private MotorcycleRepository motorcycleRepo;

	@Autowired
	public MotorcycleController(MotorcycleRepository motorcycleRepo) {
		this.motorcycleRepo = motorcycleRepo;
	}

	// getAllMotorcycles
	@GetMapping
	public Iterable<Motorcycle> getAllMotorcycle() {
		return motorcycleRepo.findAll();
	}

	// getMotorcycleByID
	@GetMapping("/{id}")
	public Motorcycle getMotorcycleById(@PathVariable("id") Long id) {
		Optional<Motorcycle> opt = motorcycleRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	// isExist
	@GetMapping("isExist/{motorcycleNumber}")
	public String isExistsByLicencePlate(@PathVariable("motorcycleNumber") String licencePlate) {
		if (motorcycleRepo.existsByLicencePlate(licencePlate))
			return "true";
		return "false";
	}

	// delete
	@DeleteMapping("/{id}")
	public void deleteMotorcycle(@PathVariable("id") Long id) {
		try {
			motorcycleRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}

	// findByUnitPrice
	@GetMapping("/findByType/{type}")
	public Iterable<Motorcycle> getAllMotorcycleByType(@PathVariable("type") String type) {
		return motorcycleRepo.findByTypeContains(type);

	}

	// findMotorcycleToBuyTicket
	@GetMapping("/findMotorcycleToBuyTicket/{time}")
	public List<Motorcycle> findMotorcycleToBuyTicket(@PathVariable("time") String time) {
		return motorcycleRepo.findMotorcycleToBuyTicket(time);
	}

	@GetMapping("/findMotorcycleToPark")
	public List<Motorcycle> findMotorcycleToPark() {
		return motorcycleRepo.findMotorcycleToPark();
	}
	
	@GetMapping("/findMotorcycleToParkByLicencePlate/{licencePlate}")
	public List<Motorcycle> findMotorcycleToParkByLicencePlate(@PathVariable("licencePlate") String licencePlate) {
		return motorcycleRepo.findMotorcycleToParkByLicencePlate("%"+licencePlate+"%");
	}


	// addRooom or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Motorcycle postMotorcycle(@RequestBody Motorcycle motorcycle) {
		return motorcycleRepo.save(motorcycle);
	}

}
