package dormitory.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dormitory.RoomStat;
import dormitory.RoomStat;

@Repository
public class JdbcRoomStatRepository implements RoomStatRepository{
	private JdbcTemplate jdbc;
	@Autowired
	public JdbcRoomStatRepository( JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	@Override
	public Iterable<RoomStat> getListRoomStatByTime(String time) {
		return jdbc.query("(select a.id, a.room_number, a.type, a.unit_price, a.max_people, (a.max_people - count_rent ) as empty_slot\r\n"
				+ "from room a,\r\n"
				+ "(select room_id,  count(room_id) as count_rent\r\n"
				+ "from rent\r\n"
				+ "where rent_date >= ? and rent_date <  DATE_ADD( ?, INTERVAL 1 MONTH )\r\n"
				+ "group by room_id ) as b\r\n"
				+ "where a.id = b.room_id\r\n"
				+ "\r\n"
				+ "union\r\n"
				+ "\r\n"
				+ "select c.id, c.room_number, c.type, c.unit_price, c.max_people, (c.max_people) as empty_slot\r\n"
				+ "from room c\r\n"
				+ "where c.id NOT IN \r\n"
				+ "(select d.room_id\r\n"
				+ "from rent d \r\n"
				+ "where rent_date >= ? and rent_date <  DATE_ADD( ?, INTERVAL 1 MONTH ) ) \r\n"
				+ ")\r\n"
				+ "order by empty_slot desc", this::mapRowToRoomStat, time, time, time, time);
		
	}
	
	private RoomStat mapRowToRoomStat(ResultSet rs, int rowNum) throws SQLException {
		return new RoomStat(rs.getLong("id"), rs.getString("room_number"), rs.getString("type"), rs.getInt("unit_price"), rs.getInt("max_people"),  rs.getInt("empty_slot"));
	}
	
}
