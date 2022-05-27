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

import dormitory.Parking;
import dormitory.data.ParkingRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/parking", produces = "application/json")
@CrossOrigin(origins = "*")
public class ParkingController {
	private ParkingRepository parkingRepo;

	@Autowired
	public ParkingController(ParkingRepository parkingRepo) {
		this.parkingRepo = parkingRepo;
	}

	// getAllParkings
	@GetMapping
	public Iterable<Parking> getAllParking() {
		return parkingRepo.findAll();
	}

	// getParkingByID
	@GetMapping("/{id}")
	public Parking getParkingById(@PathVariable("id") Long id) {
		Optional<Parking> opt = parkingRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	// delete
	@DeleteMapping("/{id}")
	public void deleteParking(@PathVariable("id") Long id) {
		try {
			parkingRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}
	
	@GetMapping("/findParkingToCheckout")
	public List<Parking> findParkingToCheckout(){
		return parkingRepo.findParkingToCheckout();
	}
	
	@GetMapping("countNumberParkingOfMotorcycleOnDay/{motorcycle_id}/{time}")
	public String countNumberParkingOfMotorcycleOnDay(@PathVariable("motorcycle_id") Long motorcycle_id, 
			@PathVariable("time") String time) {
		return parkingRepo.countNumberParkingOfMotorcycleOnDay(motorcycle_id, time);
	}
	
	// addParking or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Parking postParking(@RequestBody Parking parking) {
		return parkingRepo.save(parking);
	}

}
