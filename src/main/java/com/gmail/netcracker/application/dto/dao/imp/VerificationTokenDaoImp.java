package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.Utilites;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VerificationTokenDaoImp extends ModelDao
        implements VerificationTokenDao {

    @Transactional
    @Override
    public VerificationToken findByToken(String token) {
        VerificationToken verificationToken = new VerificationToken();
        User user = new User();
        return jdbcTemplate.query("select * from verif_token where verif_token.token_id = " + "'" + token + "'", resultSet -> {
            while (resultSet.next()) {
                verificationToken.setId(resultSet.getString("token_id"));
                verificationToken.setToken(resultSet.getString("token_id"));
                user.setId(resultSet.getString("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setPhone(resultSet.getString("phone"));
                user.setBirthdayDate(Utilites.parseDateIntoString(resultSet.getDate("birthday")));
                verificationToken.setUser(user);
                return verificationToken;
            }
            return verificationToken;
        });
    }

    @Transactional
    @Override
    public VerificationToken create(VerificationToken verificationToken) {
        jdbcTemplate.update("INSERT into verif_token(token_id, user_id,name,surname,email,password,role,phone,birthday) values(?,?,?,?,?,?,?,?,?)",
                verificationToken.getId(),
                verificationToken.getUser().getId(),
                verificationToken.getUser().getName(),
                verificationToken.getUser().getSurname(),
                verificationToken.getUser().getEmail(),
                verificationToken.getUser().getPassword(),
                "ROLE_USER",
                verificationToken.getUser().getPhone(),
                Utilites.parseStringIntoDate(verificationToken.getUser().getBirthdayDate()));
        return verificationToken;
    }

    @Transactional
    @Override
    public void delete(VerificationToken verificationToken) {
        jdbcTemplate.update("delete from verif_token where token_id = ?", verificationToken.getId());
    }
}
