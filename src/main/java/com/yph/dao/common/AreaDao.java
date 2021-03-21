package com.yph.dao.common;

import com.yph.entity.common.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yph on 2021/2/21.
 */
@Repository
public interface AreaDao extends BaseDao<Area,Long> {
    /**
     * 获取所有省
     * @return
     */
    public List<Area> findAllByProvinceIdIsNull();

    /**
     * 获取一个省下的所有城市
     * @param pid
     * @return
     */
    @Query(value = "select u from Area u where u.provinceId=:pid and u.cityId is null ")
    List<Area> getAllCity(@Param("pid") Long pid);

    /**
     * 获取一个城市下的所有区县
     * @param cid
     * @return
     */
    @Query(value = "select u from Area u where u.cityId=:cid")
    List<Area> getAllDistrict(Long cid);

    /**
     * 获取所有城市
     * @return
     */
    List<Area> findAllByProvinceIdIsNotNullAndCityIdIsNull();

    /**
     * 获取所有区县
     * @return
     */
    List<Area> findAllByProvinceIdIsNotNullAndCityIdIsNotNull();

    /**
     * 获取省份下所有区县
     * @param pid
     * @return
     */
    @Query(value = "select u from Area u where u.provinceId=:id and u.level=3")
    List<Area> findAllByProvinceId(@Param("id") Long pid);
}
