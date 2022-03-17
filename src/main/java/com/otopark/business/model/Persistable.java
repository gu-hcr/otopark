package com.otopark.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otopark.business.constant.PersistanceConstant;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Persistable implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PersistanceConstant.DEFAULT_ID_COLUMN_NAME, unique = true, nullable = false, updatable = false)
    private long id = PersistanceConstant.DEFAULT_ID_UNSAVED_VALUE;

    @Version
    @Column(name = PersistanceConstant.DEFAULT_VERSION_COLUMN_NAME)
    private long version = 0;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = PersistanceConstant.DEFAULT_ACTIVE_COLUMN_NAME, nullable = false)
    private boolean active = true;

    @CreatedBy
    @Column(name = PersistanceConstant.DEFAULT_CREATED_BY_COLUMN_NAME, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = PersistanceConstant.DEFAULT_LAST_UPDATED_BY_COLUMN_NAME)
    private String lastUpdatedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @CreatedDate
    @Column(name = PersistanceConstant.DEFAULT_CREATION_DATE_COLUMN_NAME, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @LastModifiedDate
    @Column(name = PersistanceConstant.DEFAULT_LAST_UPDATED_DATE_COLUMN_NAME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    public Persistable(){
    }

    public Persistable(long id){
        this.id = id;
    }

    public int hashCode() {
        return Long.valueOf(getId()).hashCode() ;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object another) {
        if (another == null)
            return false ;

        if (this == another)
            return true ;

        if (!(another instanceof Persistable))
            return false ;
        else {
            Persistable that = (Persistable) another ;

            if (this.getId() == 0 || that.getId() == 0 )
                return false ;

            if (this.getId() == that.getId())
                return true ;
        }

        return false ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}

