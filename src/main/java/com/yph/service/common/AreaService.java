package com.yph.service.common;

import com.yph.dao.common.AreaDao;
import com.yph.entity.common.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yph on 2021/2/21.
 */
@Service
public class AreaService extends BaseService<Area,Long> {
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

    /**
     * 获取省份下所有城市
     * @param pid
     * @return
     */
    public List<Area> getAllCity(Long pid) {
        return areaDao.getAllCity(pid);
    }

    /**
     * 获取城市下所有区县
     * @param cid
     * @return
     */
    public List<Area> getAllDistrict(Long cid) {
        return areaDao.getAllDistrict(cid);
    }

    /**
     * 获取所有城市
     * @return
     */
    public List<Area> getAllCity() {
        return areaDao.findAllByProvinceIdIsNotNullAndCityIdIsNull();
    }

    /**
     * 获取所有区县
     * @return
     */
    public List<Area> getAllDistrict() {
        return areaDao.findAllByProvinceIdIsNotNullAndCityIdIsNotNull();
    }

    /**
     * 获取省份下所有区县
     * @param provinceId
     * @return
     */
    public List<Area> getAllDistrictByProvinceId(Long provinceId){
        return areaDao.findAllByProvinceId(provinceId);
    }

    /**
     * 删除区域
     * @param toDelete
     */
    @Transactional
    public void delete(List<Area> toDelete) {
        for (Area area:toDelete){
            areaDao.deleteById(area.getId());
        }
    }
}
