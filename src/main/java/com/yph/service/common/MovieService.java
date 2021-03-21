package com.yph.service.common;

import com.yph.bean.PageBean;
import com.yph.dao.common.MovieDao;
import com.yph.entity.common.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * Created by Yph on 2021/3/21.
 */
@Service
public class MovieService extends BaseService<Movie,Long> {

    @Autowired
    private MovieDao movieDao;

    /**
     * 分页查找电影列表信息
     * @param movie
     * @param pageBean
     * @return
     */
    public PageBean<Movie> findPage(Movie movie,PageBean<Movie> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("isShow","totalMoney","rate","rateCount");
        Example<Movie> example = Example.of(movie, withMatcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
        Page<Movie> findAll = movieDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }
}
