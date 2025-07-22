package com.project1.shriganeshaynamah.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project1.shriganeshaynamah.user.Contact;
import com.project1.shriganeshaynamah.user.User;

@Repository
public interface contacc extends CrudRepository<Contact, Integer>{
    
   
    Page<Contact> findByUs(User us,Pageable pg);
    
}
