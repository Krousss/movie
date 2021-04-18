package com.yph.dao.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Yph on 2021/3/14.
 */
@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {


}
