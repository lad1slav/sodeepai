package com.vmsd.sodeepai.repository;

import com.vmsd.sodeepai.model.Cache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisCacheRepository extends CrudRepository<Cache, String> {

}
