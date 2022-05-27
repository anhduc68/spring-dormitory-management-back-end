package dormitory.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dormitory.Parking;

public interface ParkingRepository extends CrudRepository<Parking, Long>{
	@Query(value="select * from parking \r\n"
			+ "where checkin is not null \r\n"
			+ "and checkout is null", nativeQuery = true)
	public List<Parking> findParkingToCheckout();
	
	// count the number of parking of motorcycle on a day
		@Query(value="select count(motorcycle_id) \r\n"
				+ "from parking \r\n"
				+ "where motorcycle_id = ?1\r\n"
				+ "and  \r\n"
				+ "checkin >= ?2 and checkin <  DATE_ADD( ?2, INTERVAL 1 DAY )\r\n"
				+ "group by motorcycle_id", nativeQuery = true)
		public String countNumberParkingOfMotorcycleOnDay( Long motorcycle_id, String time);
}
