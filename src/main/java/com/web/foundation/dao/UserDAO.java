package com.web.foundation.dao;
import org.springframework.stereotype.Repository;


import com.web.core.base.GenericDAO;
import com.web.foundation.domain.User;

@Repository("userDAO")
public class UserDAO extends GenericDAO<User> {

}