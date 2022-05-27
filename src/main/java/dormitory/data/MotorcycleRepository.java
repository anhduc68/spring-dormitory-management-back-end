package dormitory.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dormitory.Motorcycle;

public interface MotorcycleRepository extends CrudRepository<Motorcycle, Long>{
	public boolean  existsByLicencePlate( String licencePlate );
	public List<Motorcycle> findByTypeContains( String type);
	
	@Query(value="select * from motorcycle\r\n"
			+ "where id \r\n"
			+ "NOT IN\r\n"
			+ "(select motorcycle_id\r\n"
			+ "from ticket\r\n"
			+ "where usage_time >= ?1 and usage_time <  DATE_ADD( ?1, INTERVAL 1 MONTH )\r\n"
			+ ")", nativeQuery = true)
	public List<Motorcycle> findMotorcycleToBuyTicket( String time);
	
	@Query(value="select * from motorcycle \r\n"
			+ "where id NOT IN\r\n"
			+ "(select motorcycle_id from parking\r\n"
			+ "where checkout is null)", nativeQuery = true)
	public List<Motorcycle> findMotorcycleToPark();
	
	@Query(value="select * from motorcycle \r\n"
			+ "where id NOT IN\r\n"
			+ "(select motorcycle_id from parking\r\n"
			+ "where checkout is null)\r\n"
			+ "and licence_plate like ?1", nativeQuery = true)
	public List<Motorcycle> findMotorcycleToParkByLicencePlate(String licencePlate);
}
