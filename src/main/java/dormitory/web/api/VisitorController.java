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

import dormitory.Visitor;
import dormitory.Visitor;
import dormitory.data.VisitorRepository;

@RestController
@RequestMapping(path = "/visitor", produces = "application/json")
@CrossOrigin(origins = "*")
public class VisitorController {
private VisitorRepository VisitorRepo;
	
	@Autowired
	public VisitorController( VisitorRepository VisitorRepo) {
		this.VisitorRepo = VisitorRepo;
	}
	
	// getAllVisitors
	@GetMapping
	public Iterable<Visitor> getAllVisitor() {
		return VisitorRepo.findAll();
	}
	
	//getVisitorByID
	@GetMapping("/{id}")
	public Visitor getVisitorById(@PathVariable("id") Long id) {
		Optional<Visitor> opt = VisitorRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	// findByName
			@GetMapping("/findByName/{name}")
			public Iterable<Visitor> getAllVisitorByName(@PathVariable("name") String name) {
				return VisitorRepo.findByFullnameContains(name);
			}
			
	//delete
	@DeleteMapping("/{id}")
	public void deleteVisitor(@PathVariable("id") Long id) {
		try {
			VisitorRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}
	
	//AddVisitor or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Visitor postVisitor(@RequestBody Visitor Visitor) {
		return VisitorRepo.save(Visitor);
	}
}
