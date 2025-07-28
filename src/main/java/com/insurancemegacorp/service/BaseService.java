package com.insurancemegacorp.service;

import com.insurancemegacorp.model.SoftDeletable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base service interface with common CRUD operations.
 *
 * @param <T>  the entity type
 * @param <ID> the type of the entity's identifier
 */
public interface BaseService<T extends SoftDeletable, ID extends Serializable> {

    /**
     * Saves a given entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Saves all given entities.
     *
     * @param entities the entities to save
     * @return the saved entities
     */
    List<T> saveAll(Iterable<T> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id the id of the entity to retrieve
     * @return the entity with the given id or {@link Optional#empty()} if none found
     */
    Optional<T> findById(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id the id to check
     * @return {@code true} if an entity with the given id exists, {@code false} otherwise
     */
    boolean existsById(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();

    /**
     * Returns all instances of the type with pagination.
     *
     * @param pageable the pagination information
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Returns all instances with the given IDs.
     *
     * @param ids the IDs of the entities to retrieve
     * @return the entities with the given IDs
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Deletes the entity with the given id.
     * Performs a soft delete by default.
     *
     * @param id the id of the entity to delete
     */
    void deleteById(ID id);

    /**
     * Deletes a given entity.
     * Performs a soft delete by default.
     *
     * @param entity the entity to delete
     */
    void delete(T entity);

    /**
     * Deletes all given entities.
     * Performs a soft delete by default.
     *
     * @param entities the entities to delete
     */
    void deleteAll(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     * Performs a soft delete by default.
     */
    void deleteAll();

    /**
     * Permanently deletes the entity with the given id.
     *
     * @param id the id of the entity to delete
     */
    void hardDeleteById(ID id);

    /**
     * Permanently deletes a given entity.
     *
     * @param entity the entity to delete
     */
    void hardDelete(T entity);

    /**
     * Permanently deletes all given entities.
     *
     * @param entities the entities to delete
     */
    void hardDeleteAll(Iterable<? extends T> entities);

    /**
     * Permanently deletes all entities managed by the repository.
     */
    void hardDeleteAll();

    /**
     * Restores a soft-deleted entity by its id.
     *
     * @param id the id of the entity to restore
     * @return the restored entity, or empty if not found
     */
    Optional<T> restore(ID id);
}
