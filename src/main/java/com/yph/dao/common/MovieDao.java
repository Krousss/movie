package com.yph.dao.common;

import com.yph.entity.common.Movie;
import org.springframework.stereotype.Repository;

/**
 * Created by Yph on 2021/3/21.
 */
@Repository
public interface MovieDao extends BaseDao<Movie,Long> {
}
