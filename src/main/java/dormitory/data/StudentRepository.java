package dormitory.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dormitory.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{
	public boolean  existsByStudentID( String studentID );
	public Iterable<Student> findByFullnameContains( String name );
	@Query(value="select * from student where\r\n"
			+ "id NOT IN \r\n"
			+ "( select student_id from rent\r\n"
			+ "where rent_date >= ?1 and rent_date <  DATE_ADD( ?1, INTERVAL 1 MONTH) )", nativeQuery = true)
	public List<Student> findStudentToRentByTime( String time);
	
	@Query(value="select * from student where\r\n"
			+ "id IN \r\n"
			+ "( select student_id from rent\r\n"
			+ "where rent_date >= ?1 and rent_date <  DATE_ADD( ?1, INTERVAL 1 MONTH) )", nativeQuery = true)
	public List<Student> findCurrentRentStudent( String time);
}
