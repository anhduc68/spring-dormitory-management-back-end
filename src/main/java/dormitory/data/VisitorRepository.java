package dormitory.data;

import org.springframework.data.repository.CrudRepository;

import dormitory.Visitor;

public interface VisitorRepository extends CrudRepository<Visitor, Long>{
	public Iterable<Visitor> findByFullnameContains( String name );
}
