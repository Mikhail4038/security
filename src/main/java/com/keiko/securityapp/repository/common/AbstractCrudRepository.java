package com.keiko.securityapp.repository.common;

import com.keiko.securityapp.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractCrudRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
