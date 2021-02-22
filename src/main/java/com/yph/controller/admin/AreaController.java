package com.yph.controller.admin;

import com.yph.bean.CodeMsg;
import com.yph.bean.Result;
import com.yph.entity.common.Area;
import com.yph.service.common.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
//		model.addAttribute("cityList", areaService.getAllCity());
//		model.addAttribute("districtList", areaService.getAllDistrict());
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
		model.addAttribute("PageBean","");
		model.addAttribute("name","");
		return "admin/area/add";
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
		//数据通过校验进行保存
		if (areaService.save(area)==null){
			return Result.error(CodeMsg.ADMIN_AREA_ADD_SAVE_ERROR);
		}
		return Result.success(true);
	}

}
