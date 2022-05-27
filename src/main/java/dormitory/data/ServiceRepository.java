package dormitory.data;

import org.springframework.data.repository.CrudRepository;

import dormitory.Service;

public interface ServiceRepository extends CrudRepository<Service, Long>{
	public boolean  existsByName( String name );
	public Iterable<Service> findByNameContains(String Name);
}
