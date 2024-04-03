package com.bgr.sqlsampleapplication.types;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HexFormat;
import java.util.Objects;

@Slf4j
public class HashedStringType implements UserType<String> {
    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<String> returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(String s, String j1) {
        return Objects.equals(s, j1);
    }

    @Override
    public int hashCode(String s) {
        return s.hashCode();
    }

    @Override
    public String nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        log.info("Implementing nullSafeGet");
        return resultSet.getString(i);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, String s, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        log.info("Implementing nullSafeSet");
        if (Objects.isNull(s)) {
            preparedStatement.setNull(i, Types.VARCHAR);
        } else {
            preparedStatement.setString(i, HexFormat.of().formatHex(hashString(s)));
        }
    }

    @Override
    public String deepCopy(String s) {
        return s;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(String s) {
        return s;
    }

    @Override
    public String assemble(Serializable serializable, Object o) {
        return (String) o;
    }

    private byte[] hashString(String clearValue) {
        if (clearValue == null) {
            return null;
        } else {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                digest.reset();
                return digest.digest(clearValue.getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException var3) {
                log.error("Unexpected exception while hashing data: ", var3);
                throw new RuntimeException("Cannot hash data");
            }
        }
    }

}
