package kang.jae.goo.home.mapper;

import kang.jae.goo.config.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(Member member);

    Member findByMemberId(String username);
}
