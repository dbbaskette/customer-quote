package com.insurancemegacorp.service;

import com.insurancemegacorp.model.SoftDeletable;
import com.insurancemegacorp.repository.BaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base service implementation with common CRUD operations.
 *
 * @param <T>  the entity type
 * @param <ID> the type of the entity's identifier
 */
public abstract class BaseServiceImpl<T extends SoftDeletable, ID extends Serializable> 
        implements BaseService<T, ID> {

    protected final BaseRepository<T, ID> repository;

    protected BaseServiceImpl(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public T save(T entity) {
        Assert.notNull(entity, "Entity must not be null");
        return repository.save(entity);
    }

    @Override
    @Transactional
    public List<T> saveAll(Iterable<T> entities) {
        Assert.notNull(entities, "Entities must not be null");
        return repository.saveAll(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        Assert.notNull(id, "Id must not be null");
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        Assert.notNull(id, "Id must not be null");
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllById(Iterable<ID> ids) {
        Assert.notNull(ids, "Ids must not be null");
        return repository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        Assert.notNull(id, "Id must not be null");
        repository.softDeleteById(id);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        Assert.notNull(entity, "Entity must not be null");
        repository.softDelete(entity);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "Entities must not be null");
        repository.softDeleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.softDeleteAll();
    }

    @Override
    @Transactional
    public Optional<T> restore(ID id) {
        Assert.notNull(id, "Id must not be null");
        Optional<T> entityOpt = repository.findById(id);
        entityOpt.ifPresent(entity -> {
            entity.setActive(true);
            repository.save(entity);
        });
        return entityOpt;
    }

    /**
     * Copies non-null properties from source to target.
     *
     * @param source the source object
     * @param target the target object
     */
    protected void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * Returns an array of null property names from the source object.
     *
     * @param source the source object
     * @return array of null property names
     */
    private String[] getNullPropertyNames(Object source) {
        return java.util.stream.Stream.of(BeanUtils.getPropertyDescriptors(source.getClass()))
                .filter(pd -> {
                    try {
                        return pd.getReadMethod() != null && pd.getReadMethod().invoke(source) == null;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .map(java.beans.PropertyDescriptor::getName)
                .toArray(String[]::new);
    }
}
