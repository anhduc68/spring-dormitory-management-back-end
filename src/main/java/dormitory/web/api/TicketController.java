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

import dormitory.Ticket;
import dormitory.data.TicketRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/ticket", produces = "application/json")
@CrossOrigin(origins = "*")
public class TicketController {
	private TicketRepository ticketRepo;

	@Autowired
	public TicketController(TicketRepository ticketRepo) {
		this.ticketRepo = ticketRepo;
	}

	// getAllTickets
	@GetMapping
	public Iterable<Ticket> getAllTicket() {
		return ticketRepo.findAll();
	}

	// getTicketByID
	@GetMapping("/{id}")
	public Ticket getTicketById(@PathVariable("id") Long id) {
		Optional<Ticket> opt = ticketRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	// delete
	@DeleteMapping("/{id}")
	public void deleteTicket(@PathVariable("id") Long id) {
		try {
			ticketRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}

	// findTicketByLicencePlate
	@GetMapping("/findTicketByLicencePlate/{licencePlate}")
	public Iterable<Ticket> findTicketByLicencePlate(@PathVariable("licencePlate") String licencePlate) {
		licencePlate = "%" + licencePlate + "%";
		return ticketRepo.findTicketByLicencePlate(licencePlate);
	}
	//isExistByMotorcycleIDAndTime
	@GetMapping("/isExistByMotorcycleIDAndTime/{motorcycle_id}/{time}")
	public String isExistByMotorcycleIDAndTime( @PathVariable("motorcycle_id") Long motorcycle_id, 
			@PathVariable("time") String time) {
		return ticketRepo.isExistByMotorcycleIDAndTime(motorcycle_id, time);
	}
	

//	
//	//getListTicketStatByTime
//	@GetMapping("/findListTicketStatByTime/{time}")
//	public Iterable<TicketStat> getListTicketStatByTime(@PathVariable("time") String time) {
//		return jdbcTicketRepo.getListTicketStatByTime(time);
//	}
//	
	// addTicket or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Ticket postTicket(@RequestBody Ticket ticket) {
		return ticketRepo.save(ticket);
	}

	// countTicketOfStudentByTime
	@GetMapping("/countTicketOfStudentByTime/{student_id}/{time}")
	public String countTicketOfStudentByTime(@PathVariable("student_id") Long student_id,
			@PathVariable("time") String time) {
//		if( ticketRepo.countTicketOfStudentByTime(student_id, time) == null)
//			log.info("null");
		return ticketRepo.countTicketOfStudentByTime(student_id, time);
	}
	
	//findTicketByStudentIDAndTime
	@GetMapping("/findTicketByStudentIDAndTime/{student_id}/{time}")
	public List<Ticket> findTicketByStudentIDAndTime(@PathVariable("student_id") Long student_id,
			@PathVariable("time") String time){
		return ticketRepo.findTicketByStudentIDAndTime(student_id, time);
	} 
}
