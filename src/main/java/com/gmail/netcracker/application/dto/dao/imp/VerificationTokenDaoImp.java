package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringToDate;
import static com.gmail.netcracker.application.utilites.Utilities.parseStringToTimestamp;

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

    @Value("${sql.user.role}")
    private String USER_ROLE;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RowMapper<VerificationToken> rowMapper;

    @Autowired
    public VerificationTokenDaoImp(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return findEntity(SQL_FIND, rowMapper, token);
    }

    @Override
    public VerificationToken create(VerificationToken verificationToken) {
        insertEntity(SQL_CREATE, PK_COLUMN_NAME,
                verificationToken.getId(),
                verificationToken.getUser().getName(),
                verificationToken.getUser().getSurname(),
                verificationToken.getUser().getEmail(),
                passwordEncoder.encode(verificationToken.getUser().getPassword()),
                USER_ROLE,
                parseStringToDate(verificationToken.getUser().getBirthdayDate()),
                verificationToken.getUser().getPhone(),
                verificationToken.getUser().getGender()
        );
        return verificationToken;
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        deleteEntity(SQL_DELETE, verificationToken.getId());
    }
}
