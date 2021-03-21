package com.rihua.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;


/**
 * 基类
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {


        /**
         * 保存但不覆盖原有数据
         * @param entity
         * @return
         */
        T saveNotNull(T entity);

}
