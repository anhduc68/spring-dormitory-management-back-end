package dormitory.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dormitory.Room;

public interface RoomRepository extends CrudRepository<Room, Long>{
	public boolean  existsByRoomNumber( String roomNumber );
	public List<Room> findByUnitPriceBetween( int min, int max);
}