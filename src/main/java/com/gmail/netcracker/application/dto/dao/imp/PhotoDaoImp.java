package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.PhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;

@Repository
public class PhotoDaoImp extends ModelDao implements PhotoDao, Serializable {


    @Value("${sql.photo.update}")
    private String SQL_UPDATE;

    @Autowired
    public PhotoDaoImp(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insert(String fileName, Long id) {
        jdbcTemplate.update(SQL_UPDATE, fileName, id);
    }
}
