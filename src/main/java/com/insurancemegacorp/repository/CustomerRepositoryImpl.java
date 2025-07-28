package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.SoftDeletable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl<T extends SoftDeletable, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomerRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void softDelete(T entity) {
        entity.setActive(false);
        save(entity);
    }

    @Override
    @Transactional
    public void softDeleteById(ID id) {
        findById(id).ifPresent(this::softDelete);
    }

    @Override
    @Transactional
    public void softDeleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::softDelete);
    }

    @Override
    @Transactional
    public void softDeleteAll() {
        findAll().forEach(this::softDelete);
    }

    @Override
    public List<T> findByActiveTrue() {
        return findAll();
    }

    @Override
    public Optional<T> findByIdAndActiveTrue(ID id) {
        return findById(id).filter(SoftDeletable::isActive);
    }

    @Override
    public long countByActiveTrue() {
        return findAll().stream().filter(SoftDeletable::isActive).count();
    }

    @Override
    public boolean existsByIdAndActiveTrue(ID id) {
        return findById(id).filter(SoftDeletable::isActive).isPresent();
    }
}
