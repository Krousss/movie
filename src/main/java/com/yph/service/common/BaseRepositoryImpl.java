package com.rihua.common.service.impl;

import com.rihua.common.dao.BaseDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {


    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;



    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation,entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    @Override
    @Transactional
    public T saveNotNull(T entity) {

        //获取ID
        ID entityId = (ID) this.entityInformation.getId(entity);
        T managedEntity;
        T mergedEntity;
        if(entityId == null){
            em.persist(entity);
            mergedEntity = entity;
        }else{
            managedEntity = this.findById(entityId).get();
            if (managedEntity == null) {
                em.persist(entity);
                mergedEntity = entity;
            } else {
                BeanUtils.copyProperties(entity, managedEntity, getNullProperties(entity));
                em.merge(managedEntity);
                mergedEntity = managedEntity;
            }
        }
        return mergedEntity;
    }


    private static String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (propertyValue == ""||propertyValue ==null) {
                srcBean.setPropertyValue(propertyName, null);
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }
}
