package dormitory.data;

import dormitory.ServiceStat;

public interface ServiceStatRepository {
	Iterable<ServiceStat> getListServiceStatByTime(String time);
}
