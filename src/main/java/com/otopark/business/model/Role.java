package com.otopark.business.model;

import com.otopark.business.constant.PersistanceConstant;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = PersistanceConstant.DEFAULT_TABLE_NAME_ROLE)
public class Role extends Persistable {

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_privelege",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = PersistanceConstant.DEFAULT_ID_COLUMN_NAME),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = PersistanceConstant.DEFAULT_ID_COLUMN_NAME))
    private Collection<Privilege> privileges;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

}



