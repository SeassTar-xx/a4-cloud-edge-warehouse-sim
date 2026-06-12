package com.algorithm.algorithm.mapper;

import com.algorithm.algorithm.entity.MPS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MPSMapper {
    /**
     * 根据ID查询MPS表
     *
     * @param id
     * @return MPS
     */
    @Select(" select * from MPS where id = #{id} ")
    MPS selectByID(String id);

    /**
     * 根据itemCode查询MPS表
     *
     * @param itemCode
     * @return MPS
     */
    @Select(" select * from MPS where itemCode = #{itemCode} ")
    List<MPS> selectByitemCode(String itemCode);

}
