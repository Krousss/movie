package com.yph.service.common;

import com.yph.dao.common.AreaDao;
import com.yph.entity.common.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yph on 2021/2/21.
 */
@Service
public class AreaService {
    @Autowired
    private AreaDao areaDao;

    /**
     * 保存地域
     * @param area
     * @return
     */
    public Area save(Area area){
        return areaDao.save(area);
    }

    /**
     * 获取所有省份列表
     * @return
     */
    public List<Area> getProvinces(){
        return areaDao.findAllByProvinceIdIsNull();
    }
}
