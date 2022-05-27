package dormitory.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dormitory.ServiceStat;

@Repository
public class JdbcServiceStatRepository implements ServiceStatRepository{
	private JdbcTemplate jdbc;
	@Autowired
	public JdbcServiceStatRepository( JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	@Override
	public Iterable<ServiceStat> getListServiceStatByTime(String time) {
		return jdbc.query("select a.id,a.name,a.unit_price, total_usage, total_amount\r\n"
				+ "from service a,(select service_id, sum(quantity) as 'total_usage' , sum(total_amount) as 'total_amount'\r\n"
				+ "from usedservice\r\n"
				+ "where service_usage_time >=? and service_usage_time < DATE_ADD( ?, INTERVAL 1 MONTH)\r\n"
				+ "group by service_id ) as b\r\n"
				+ "where a.id = b.service_id\r\n"
				+ "order by total_amount desc", this::mapRowToServiceStat, time, time);
		
	}
	
	private ServiceStat mapRowToServiceStat(ResultSet rs, int rowNum) throws SQLException {
		return new ServiceStat(rs.getLong("id"), rs.getString("name"), rs.getInt("unit_price"), rs.getInt("total_usage"), rs.getInt("total_amount"));
	}
	
}
