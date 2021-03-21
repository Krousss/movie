package com.yph.controller.admin;

import com.yph.bean.CodeMsg;
import com.yph.bean.Result;
import com.yph.entity.common.Area;
import com.yph.service.common.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 地域管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/area")
@Controller
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	/**
	 * 跳转地域列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model){
		model.addAttribute("proviceList", areaService.getProvinces());
		model.addAttribute("cityList", areaService.getAllCity());
		model.addAttribute("districtList", areaService.getAllDistrict());
		return "admin/area/list";
	}

	/**
	 * 跳转添加地域页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("proviceList", areaService.getProvinces());
		return "admin/area/add";
	}

	/**
	 * 地域编辑页面
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping(value="/edit")
	public String edit(Model model,@RequestParam(name="id",required=true)Long id){
		model.addAttribute("proviceList", areaService.getProvinces());
		model.addAttribute("cityList", areaService.getAllCity());
		model.addAttribute("area", areaService.findById(id));
		return "admin/area/edit";
	}

	/**
	 * 添加地域
	 * @param area
	 * @return
	 */
	@PostMapping(value = "/add")
	@ResponseBody
	public Result<Boolean> add(Area area){
		//后台校验数据
		if (area==null){
			return Result.error(CodeMsg.DATA_ERROR);
		}
		if (StringUtils.isEmpty(area.getName())){
			return Result.error(CodeMsg.ADMIN_AREA_ADD_NAME_EMPTY);
		}
		//判断地域属于哪一级
		if (area.getProvinceId()==null){
			area.setLevel(1);
		}else if (area.getCityId()==null){
			area.setLevel(2);
		}else {
			area.setLevel(3);
		}
		//判断是否是编辑
		if(area.getId() != null && area.getId() > 0){
			Area findById = areaService.findById(area.getId());
			area.setCreateTime(findById.getCreateTime());
		}
		//数据通过校验进行保存
		if (areaService.saveAndFlush(area)==null){
			return Result.error(CodeMsg.ADMIN_AREA_ADD_SAVE_ERROR);
		}
		return Result.success(true);
	}


	/**
	 * 根据省份id获取城市列表
	 * @param pid
	 * @return
	 */
	@PostMapping(value="/get_citys")
	@ResponseBody
	public Result<List<Area>> getCitys(@RequestParam(name="pid",required=true)Long pid){
		return Result.success(areaService.getAllCity(pid));
	}

	/**
	 * 根据城市id获取城市区
	 * @param cid
	 * @return
	 */
	@PostMapping(value="/get_districts")
	@ResponseBody
	public Result<List<Area>> getDistricts(@RequestParam(name="cid",required=true)Long cid){
		return Result.success(areaService.getAllDistrict(cid));
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@PostMapping(value="/del")
	@ResponseBody
	public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
		try {
			List<Area> toDelete=new ArrayList<>();
			switch (areaService.getOne(id).getLevel()){
				//删除省时
				case 1:
					toDelete.add(areaService.getOne(id));
					List<Area> citys=areaService.getAllCity(id);
					if (citys!=null&&citys.size()>0){
						toDelete.addAll(citys);
					}
					List<Area> countys=areaService.getAllDistrictByProvinceId(id);
					if (countys!=null&&countys.size()>0){
						toDelete.addAll(countys);
					}
					areaService.delete(toDelete);
					break;
				//删除市时
				case 2:
					toDelete.add(areaService.getOne(id));
					List<Area> countys2=areaService.getAllDistrictByProvinceId(id);
					if (countys2!=null&&countys2.size()>0){
						toDelete.addAll(countys2);
					}
					areaService.delete(toDelete);
					break;
				//删除区县时
				case 3:
					toDelete.add(areaService.getOne(id));
					break;
			}
		} catch (Exception e) {
			return Result.error(CodeMsg.ADMIN_AREA_DELETE_ERROR);
		}
		return Result.success(true);
	}
}
