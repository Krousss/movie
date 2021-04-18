package com.yph.controller.admin;

import com.yph.bean.*;
import com.yph.entity.common.Movie;
import com.yph.service.common.MovieService;
import com.yph.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author yph
 *         Created by Yph on 2021/3/21.
 */
@RequestMapping(value = "/admin/movie")
@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    /**
     * 电影列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, Movie movie, PageBean<Movie> pageBean) {
        model.addAttribute("pageBean", movieService.findPage(movie, pageBean));
        model.addAttribute("name", movie.getName());
        return "admin/movie/list";
    }

    /**
     * 电影添加页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("movieAreaList", MovieArea.values());
        model.addAttribute("movieTypeList", MovieType.values());
        model.addAttribute("movieLangList", MovieLang.values());
        return "admin/movie/add";
    }

    /**
     * 添加电影表单提交
     *
     * @param movie
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Movie movie) {
        if (movie == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        CodeMsg validate = ValidateEntityUtil.validate(movie);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        //判断是否是编辑
        if (movie.getId() != null && movie.getId() > 0) {
            Movie findById = movieService.findById(movie.getId());
            movie.setCreateTime(findById.getCreateTime());
            movie.setRate(findById.getRate());
            movie.setRateCount(findById.getRateCount());
            movie.setTotalMoney(findById.getTotalMoney());
        }
        //表示数据合法，可以保存到数据库
        if (movieService.save(movie) == null) {
            return Result.error(CodeMsg.ADMIN_AREA_SAVE_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/del")
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Long id) {
        try {
            movieService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_MOVIE_DELETE_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 电影编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit(Model model, @RequestParam(name = "id", required = true) Long id) {
        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("movieAreaList", MovieArea.values());
        model.addAttribute("movieTypeList", MovieType.values());
        model.addAttribute("movieLangList", MovieLang.values());
        return "admin/movie/edit";
    }
}
