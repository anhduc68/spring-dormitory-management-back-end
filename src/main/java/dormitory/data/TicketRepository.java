package dormitory.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dormitory.Ticket;
import dormitory.UsedService;

public interface TicketRepository extends CrudRepository<Ticket, Long>{
	//findTicketByLicenPlate
	@Query(value="select * from ticket \r\n"
			+ "where motorcycle_id IN \r\n"
			+ "(select id \r\n"
			+ "from motorcycle \r\n"
			+ "where licence_plate like ?)", nativeQuery = true)
	public List<Ticket> findTicketByLicencePlate( String licencePlate);
	
	//countTicketOfStudentByTime
	@Query(value="select count(student_id) from ticket \r\n"
			+ "where student_id = ?1\r\n"
			+ "and usage_time >= ?2 and usage_time <  DATE_ADD( ?2, INTERVAL 1 MONTH )\r\n"
			+ "group by student_id", nativeQuery = true)
	public String countTicketOfStudentByTime(Long student_id, String time);
	
	//isExistByMotorcycleIDAndTime
	@Query(value="select motorcycle_id from ticket\r\n"
			+ "where motorcycle_id = ?1\r\n"
			+ "and  usage_time >= ?2 and usage_time <  DATE_ADD( ?2, INTERVAL 1 MONTH )", nativeQuery = true)
	public String isExistByMotorcycleIDAndTime( Long motorcycle_id, String time);
	
	
	//findTicketByStudentIDAndTime
	@Query(value="select * from ticket \r\n"
			+ "where student_id = ?1\r\n"
			+ "and  usage_time >= ?2 and usage_time <  DATE_ADD( ?2, INTERVAL 1 MONTH )", nativeQuery = true)
	public List<Ticket> findTicketByStudentIDAndTime(Long student_id, String time);
}
