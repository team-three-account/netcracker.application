package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.utilites.Utilities;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class VerificationTokenDaoImp extends ModelDao implements VerificationTokenDao {
    @Value("${sql.token.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.token.create}")
    private String SQL_CREATE;
    @Value("${sql.token.find}")
    private String SQL_FIND;
    @Value("${sql.token.delete}")
    private String SQL_DELETE;

    private PasswordEncoder passwordEncoder;
    private RowMapper<VerificationToken> rowMapper;

    @Autowired
    protected VerificationTokenDaoImp(DataSource dataSource, RowMapper<VerificationToken> rowMapper,
                                      PasswordEncoder passwordEncoder) {
        super(dataSource);
        this.passwordEncoder = passwordEncoder;
        this.rowMapper = rowMapper;
    }

    @Transactional
    @Override
    public VerificationToken findByToken(String token) {
        return findEntity(SQL_FIND, rowMapper, token);
    }

    @Transactional
    @Override
    //TODO don't hardcode role
    public VerificationToken create(VerificationToken verificationToken) {
        insertEntity(SQL_CREATE, PK_COLUMN_NAME,
                verificationToken.getId(),
                verificationToken.getUser().getName(),
                verificationToken.getUser().getSurname(),
                verificationToken.getUser().getEmail(),
                passwordEncoder.encode(verificationToken.getUser().getPassword()),
                "ROLE_USER",
                Utilities.parseStringIntoDate(verificationToken.getUser().getBirthdayDate()),
                verificationToken.getUser().getPhone()
        );
        return verificationToken;
    }

    @Transactional
    @Override
    public void delete(VerificationToken verificationToken) {
        deleteEntity(SQL_DELETE, verificationToken.getId());
    }
}
