package kang.jae.goo.management.mapper;

import kang.jae.goo.register.dto.TransactionSlipDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagementMapper {
    List<TransactionSlipDTO> doSearch(Map<String, Object> map);

    void deleteItem(Map<String, Object> map);

    void updateItem(Map<String, Object> map);
}
