package com.vmsd.sodeepai.repository;

import com.vmsd.sodeepai.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedisCacheRepository extends CrudRepository<Message, String> {
    List<Message> findAllBySenderName(String senderName);
}
