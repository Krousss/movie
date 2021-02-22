package com.yph.dao.common;

import com.yph.entity.common.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yph on 2021/2/21.
 */
@Repository
public interface AreaDao extends JpaRepository<Area,Long> {
    public List<Area> findAllByProvinceIdIsNull();
}
