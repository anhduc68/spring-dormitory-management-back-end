package dormitory.web.api;

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

import dormitory.Room;
import dormitory.Service;
import dormitory.ServiceStat;
import dormitory.data.JdbcServiceStatRepository;
import dormitory.data.ServiceRepository;

@RestController
@RequestMapping(path = "/service", produces = "application/json")
@CrossOrigin(origins = "*")
public class ServiceController {
	private ServiceRepository serviceRepo;
	private JdbcServiceStatRepository serviceStatRepo;
	@Autowired
	public ServiceController(ServiceRepository serviceRepo, JdbcServiceStatRepository serviceStatRepo) {
		this.serviceRepo = serviceRepo;
		this.serviceStatRepo = serviceStatRepo;
	}

	// getAllServices
	@GetMapping
	public Iterable<Service> getAllService() {
		return serviceRepo.findAll();
	}

	// getServiceByID
	@GetMapping("/{id}")
	public Service getServiceById(@PathVariable("id") Long id) {
		Optional<Service> opt = serviceRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	// isExist
	@GetMapping("isExist/{name}")
	public String isExitByName(@PathVariable("name") String name) {
		if (serviceRepo.existsByName(name))
			return "true";
		return "false";
	}

	// delete
	@DeleteMapping("/{id}")
	public void deleteService(@PathVariable("id") Long id) {
		try {
			serviceRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}

	// findByName
	@GetMapping("/findByName/{name}")
	public Iterable<Service> getAllServiceByName(@PathVariable("name") String name) {
		return serviceRepo.findByNameContains(name);
	}
	
	//getServiceStatByTime
	@GetMapping("/stat/{time}")
	public Iterable<ServiceStat> getServiceStatsByTime(@PathVariable("time") String time ){
		return serviceStatRepo.getListServiceStatByTime(time);
	}
	
	//addService or Update
		@PostMapping(consumes = "application/json")
		@ResponseStatus(HttpStatus.CREATED)
		public Service postService(@RequestBody Service service) {
			return serviceRepo.save(service);
		}

}
