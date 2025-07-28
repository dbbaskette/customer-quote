package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.SoftDeletable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base repository interface with soft delete support.
 *
 * @param <T>  the entity type
 * @param <ID> the type of the entity's identifier
 */
@NoRepositoryBean
public interface BaseRepository<T extends SoftDeletable, ID extends Serializable> extends JpaRepository<T, ID> {
    
    /**
     * Find all active (non-deleted) entities.
     *
     * @return list of active entities
     */
    List<T> findByActiveTrue();
    
    /**
     * Find an entity by its ID and active status.
     * 
     * @param id the ID of the entity
     * @return an Optional containing the entity if found and active, empty otherwise
     */
    Optional<T> findByIdAndActiveTrue(ID id);
    
    /**
     * Soft delete an entity by setting active to false.
     * 
     * @param id the ID of the entity to delete
     */
    void softDeleteById(ID id);

    /**
     * Soft delete an entity by setting active to false.
     *
     * @param entity the entity to delete
     */
    void softDelete(T entity);

    /**
     * Soft delete all entities in the given iterable.
     *
     * @param entities the entities to delete
     */
    void softDeleteAll(Iterable<? extends T> entities);

    /**
     * Soft delete all entities managed by the repository.
     */
    void softDeleteAll();
    
    /**
     * Count all active entities.
     * 
     * @return the count of active entities
     */
    long countByActiveTrue();
    
    /**
     * Check if an active entity with the given ID exists.
     * 
     * @param id the ID to check
     * @return true if an active entity with the given ID exists, false otherwise
     */
    boolean existsByIdAndActiveTrue(ID id);
}
