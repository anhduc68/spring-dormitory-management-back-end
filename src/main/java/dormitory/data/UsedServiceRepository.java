package dormitory.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import dormitory.UsedService;

public interface UsedServiceRepository extends CrudRepository<UsedService, Long>{
	@Query(value="select * from usedService \r\n"
			+ "where service_id = ?1\r\n"
			+ "and service_usage_time >= ?2 and service_usage_time <  DATE_ADD( ?2, INTERVAL 1 MONTH )", nativeQuery = true)
	public List<UsedService> findUsedServiceByServiceIDAndTime(Long service_id, String time);
	
	@Query(value="select * from usedService \r\n"
			+ "where student_id = ?1\r\n"
			+ "and service_usage_time >= ?2 and service_usage_time <  DATE_ADD( ?2, INTERVAL 1 MONTH )", nativeQuery = true)
	public List<UsedService> findUsedServiceByStudentIDAndTime(Long student_id, String time);
}
