package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-12-04
 */
public interface Wgs84Mapper extends BaseMapper<Wgs84> {

    @Select("SELECT id,longitude,latitude FROM map_markpoint")
    List<Wgs84> selectAll();

    @Update("UPDATE map_markpoint SET longitude =#{longitude},latitude =#{latitude} WHERE id=#{id}")
    void updateL(@Param("id") Integer id,@Param("longitude") Double longitude,@Param("latitude") Double latitude);
}
