package dormitory.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dormitory.Rent;
import dormitory.UsedService;

public interface RentRepository extends CrudRepository<Rent, Long>{
	@Query(value="select * from rent \r\n"
			+ "where student_id = ?1\r\n"
			+ "and rent_date >= ?2 and rent_date <  DATE_ADD( ?2, INTERVAL 1 MONTH )", nativeQuery = true)
	public List<Rent> findRentByStudentIDAndTime(Long student_id, String time);
}
