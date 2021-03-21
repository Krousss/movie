package com.rihua.common.service;

import com.rihua.common.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * baseservice整合公用方法
 *
 * @param <T>
 * @param <ID>
 */
public class BaseService<T, ID extends Serializable> {


    @Autowired
    private BaseDao<T, ID> baseDao;

    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public T findById(ID id) {
        Optional<T> optionalT = baseDao.findById(id);
        return optionalT.isPresent() ? optionalT.get(): null;
        //return baseDao.findById(id).get();
    }




    /**
     * 根据ID删除
     *
     * @param id
     */
    @Transactional
    public void deleteById(ID id) {
        baseDao.deleteById(id);
    }

    /**
     * 软删除
     * @param tableName
     * @param idName
     * @param id
     */
    @Transactional
    @Modifying
    public  void updateToDelete(String tableName,String idName ,String id){
        String sql= " update " +tableName+" set status = 0 WHERE "+idName+" ='"+id+"';";
        entityManager.createNativeQuery(sql).executeUpdate();
    }




    /**
     * 分页查询status=1的数据
     *
     * @param page
     * @param limit
     * @return
     */
    public Page<T> findAllPageByStatus(Integer page, Integer limit) {
        Specification<T> spec = new Specification<T>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> status = root.get("status");
                Predicate p1 = cb.equal(status, "1");
                Predicate p = cb.and(p1);
                return p;
            }
        };
        return baseDao.findAll(spec, PageRequest.of(page, limit));
    }


    /**
     * 查询所有status=1的数据
     *
     * @return
     */
    public List<T> findAllByStatus(String orgId) {
        Specification<T> spec = new Specification<T>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                Predicate p1 = cb.equal(root.get("status"), "1");
                list.add(p1);

                Predicate pOrgId = cb.equal(root.get("orgId"), orgId);
                list.add(pOrgId);

                Predicate endPredicate = cb.and(list.toArray(new Predicate[list.size()]));
                return endPredicate;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return baseDao.findAll(spec,sort);
    }




    public void saveNotNull(T entity) {
        baseDao.saveNotNull(entity);
    }






















    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    public List<T> findAll() {
        return baseDao.findAll();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
     */
    public List<T> findAll(Sort sort) {
        return baseDao.findAll(sort);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)
     */
    public List<T> findAllById(Iterable<ID> ids) {
        return baseDao.findAllById(ids);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return baseDao.saveAll(entities);
    }

    /**
     * Flushes all pending changes to the database.
     */
    public void flush() {
        baseDao.flush();
    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    @Transactional
    public <S extends T> S saveAndFlush(S entity) {
        return baseDao.saveAndFlush(entity);
    }

    /**
     * Deletes the given entities in a batch which means it will create a single {@link Query}. Assume that we will clear
     * the {@link EntityManager} after the call.
     *
     * @param entities
     */
    @Transactional
    public void deleteInBatch(Iterable<T> entities) {
        baseDao.deleteInBatch(entities);
    }

    /**
     * Deletes all entities in a batch call.
     */
    @Transactional
    public void deleteAllInBatch() {
        baseDao.deleteAllInBatch();
    }

    /**
     * Returns a reference to the entity with the given identifier.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @throws javax.persistence.EntityNotFoundException if no entity exists for given {@code id}.
     * @see EntityManager#getReference(Class, Object)
     */
    @Transactional
    public T getOne(ID id) {
        return baseDao.getOne(id);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.query.QueryByExampleExecutor#findAll(org.springframework.data.domain.Example)
     */
    public <S extends T> List<S> findAll(Example<S> example) {
        return baseDao.findAll(example);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.query.QueryByExampleExecutor#findAll(org.springframework.data.domain.Example, org.springframework.data.domain.Sort)
     */
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return baseDao.findAll(example, sort);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    public Page<T> findPage(Integer page, Integer limit) {
        return baseDao.findAll(PageRequest.of(page, limit));
    }

    /**
     * 按关键字升序查询
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    public Page<T> findPageBySortASC(Integer page, Integer limit, String param) {
        return baseDao.findAll(PageRequest.of(page, limit, Sort.Direction.ASC, param));
    }

    /**
     * 按关键字降序查询
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    public Page<T> findPageBySortDESC(Integer page, Integer limit, String param) {
        return baseDao.findAll(PageRequest.of(page, limit, Sort.Direction.DESC, param));
    }

    /**
     * 带条件分页查询
     *
     * @param spec
     * @param page
     * @param limit
     * @return
     */
    public Page<T> findAll(Specification<T> spec, Integer page, Integer limit) {
        return baseDao.findAll(spec, PageRequest.of(page, limit));
    }

    /**
     * 保存实体
     *
     * @param entity
     */
    @Transactional
    public T save(T entity) {
        return baseDao.save(entity);
    }










    public Object findStationInfo(String tableName, Class clz, Long id) {
        String sql = "select * from " + tableName + " where point_id=:id";
        Object obj = entityManager.createNativeQuery(sql, clz).setParameter("id", id).getSingleResult();

        return obj;
    }

    @Transactional
    public void persist(T entity) {
        entityManager.persist(entity);
    }


}
