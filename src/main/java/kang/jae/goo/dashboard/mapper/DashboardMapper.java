package kang.jae.goo.dashboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {
    List<Map<String, Object>> ds_getChartData(Map<String, Object> map);

    List<Map<String, Object>> ds_getAlldata(Map<String, Object> map);
}
