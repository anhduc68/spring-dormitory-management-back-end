package dormitory.data;

import dormitory.RoomStat;
public interface RoomStatRepository {
	Iterable<RoomStat> getListRoomStatByTime(String time);
}
