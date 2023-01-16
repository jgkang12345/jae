package kang.jae.goo.register.mapper;

import kang.jae.goo.register.dto.TransactionSlipDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface RegisterMapper {
    List<TransactionSlipDTO> selectAll();

    void InsertItem(Map<String, Object> map);
}
