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
import dormitory.RoomStat;
import dormitory.data.JdbcRoomStatRepository;
import dormitory.data.RoomRepository;

@RestController
@RequestMapping(path = "/room", produces = "application/json")
@CrossOrigin(origins = "*")
public class RoomController {
	private RoomRepository roomRepo;
	private JdbcRoomStatRepository jdbcRoomRepo;
	@Autowired
	public RoomController( RoomRepository roomRepo, JdbcRoomStatRepository jdbcRoomRepo) {
		this.roomRepo = roomRepo;
		this.jdbcRoomRepo = jdbcRoomRepo;
	}
	
	// getAllRooms
	@GetMapping
	public Iterable<Room> getAllRoom() {
		return roomRepo.findAll();
	}
	
	//getRoomByID
	@GetMapping("/{id}")
	public Room getRoomById(@PathVariable("id") Long id) {
		Optional<Room> opt = roomRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	//isExist
	@GetMapping("isExist/{roomNumber}")
	public String isExitByTen(@PathVariable("roomNumber") String roomNumber) {
		if(  roomRepo.existsByRoomNumber(roomNumber) )
		return "true";
	    return "false";
	}
	
	//delete
	@DeleteMapping("/{id}")
	public void deleteRoom(@PathVariable("id") Long id) {
		try {
			roomRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}
	
	//findByUnitPrice
	@GetMapping("/findByUnitPrice/{unitPrice}")
	public Iterable<Room> getAllRoomByUnitPrice(@PathVariable("unitPrice") int unitPrice) {
		return roomRepo.findByUnitPriceBetween(0, unitPrice);

	}
	
	//getListRoomStatByTime
	@GetMapping("/findListRoomStatByTime/{time}")
	public Iterable<RoomStat> getListRoomStatByTime(@PathVariable("time") String time) {
		return jdbcRoomRepo.getListRoomStatByTime(time);
	}
	
	//addRooom or Update
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Room postRoom(@RequestBody Room room) {
		return roomRepo.save(room);
	}
	
	
}
