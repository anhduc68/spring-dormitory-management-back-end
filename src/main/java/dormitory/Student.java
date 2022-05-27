package dormitory;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column( name ="student_id")
	private String studentID;
	
	@Column( name ="id_number")
	private String idNumber;
	
	@Column( name ="fullname")
	private String fullname;
	private Date dob;
	
	@Column( name ="class_id")
	private String classID;
	
	@Column( name ="hometown")
	private String hometown;

}
