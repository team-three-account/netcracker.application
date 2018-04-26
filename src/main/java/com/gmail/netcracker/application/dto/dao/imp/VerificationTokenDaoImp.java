package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.utilites.Utilities;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class VerificationTokenDaoImp extends ModelDao implements VerificationTokenDao {
    private final String PK_COLUMN_NAME = "token_id";

    private final String SQL_CREATE = "verificationToken/create.sql";
    private final String SQL_FIND = "verificationToken/findByCreator.sql";
    private final String SQL_DELETE = "verificationToken/delete.sql";

    private PasswordEncoder passwordEncoder;
    private RowMapper<VerificationToken> rowMapper;

    @Autowired
    protected VerificationTokenDaoImp(DataSource dataSource, ResourceLoader resourceLoader,
                                      Environment environment, RowMapper<VerificationToken> rowMapper, PasswordEncoder passwordEncoder) {
        super(dataSource, resourceLoader, environment);
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
