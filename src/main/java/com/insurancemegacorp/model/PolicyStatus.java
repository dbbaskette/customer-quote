package com.insurancemegacorp.model;

/**
 * Represents the possible statuses of an insurance policy.
 */
public enum PolicyStatus {
    /** Policy has been quoted but not yet purchased. */
    QUOTED,
    
    /** Policy is active and in force. */
    ACTIVE,
    
    /** Policy has expired. */
    EXPIRED,
    
    /** Policy has been cancelled. */
    CANCELLED,
    
    /** Policy is in grace period. */
    GRACE_PERIOD,
    
    /** Policy has lapsed due to non-payment. */
    LAPSED,
    
    /** Policy is pending underwriting approval. */
    PENDING,
    
    /** Policy has been declined by underwriting. */
    DECLINED,
    
    /** Policy was not renewed. */
    NON_RENEWED,
    
    /** Policy is pending reinstatement. */
    REINSTATEMENT_PENDING,
    
    /** Policy has been reinstated. */
    REINSTATED,
    
    /** Policy is suspended. */
    SUSPENDED,
    
    /** Policy has been converted to another type. */
    CONVERTED,
    
    /** Policy is pending issuance. */
    ISSUANCE_PENDING,
    
    /** Policy is bound but not yet issued. */
    BOUND_UNISSUED,
    
    /** Policy is issued but not yet effective. */
    ISSUED_NOT_EFFECTIVE,
    
    /** Policy is pending cancellation. */
    CANCELLATION_PENDING,
    
    /** Policy has been rescinded. */
    RESCINDED,
    
    /** Policy is pending renewal. */
    RENEWAL_PENDING,
    
    /** Policy has been renewed. */
    RENEWED,
    
    /** Policy is pending transfer. */
    TRANSFER_PENDING,
    
    /** Policy has been transferred. */
    TRANSFERRED,
    
    /** Policy is pending rewrite. */
    REWRITE_PENDING,
    
    /** Policy has been rewritten. */
    REWRITTEN,
    
    /** Policy is pending endorsement. */
    ENDORSEMENT_PENDING,
    
    /** Policy has been endorsed. */
    ENDORSED,
    
    /** Policy is pending audit. */
    AUDIT_PENDING,
    
    /** Policy has been audited. */
    AUDITED
}
