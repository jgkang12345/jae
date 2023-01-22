package kang.jae.goo.setting.mapper;

import kang.jae.goo.register.dto.TransactionSlipDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Mapper
public interface SettingMapper {
    void InsertItem(Map<String, Object> map);
    void UpdateItem(Map<String, Object> map);
    int SelectDno(Map<String, Object> map);

    List<Map<String, Object>> doSearch(Map<String, Object> map);

    void doDelete(Map<String, Object> map);
}
