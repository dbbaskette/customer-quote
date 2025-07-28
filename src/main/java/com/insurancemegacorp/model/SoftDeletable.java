package com.insurancemegacorp.model;

/**
 * Interface for entities that support soft deletion.
 * Entities implementing this interface will have an 'active' flag
 * that can be toggled instead of being physically deleted.
 */
public interface SoftDeletable {
    
    /**
     * Check if the entity is active.
     * 
     * @return true if the entity is active, false otherwise
     */
    boolean isActive();
    
    /**
     * Set the active status of the entity.
     * 
     * @param active the new active status
     */
    void setActive(boolean active);
}
