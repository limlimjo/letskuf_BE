package com.letskuf.repository;

import com.letskuf.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    /* 사용할 서비스 주입 */
    private final SqlSessionTemplate sql;

    /* 사용자 조회 */
    public Optional<UserDTO> findByUsername(String username) {
        UserDTO user = sql.selectOne("User.findByUsername", username);
        return Optional.ofNullable(user);
    }

    /* 사용자 등록 */
    public void saveUser(UserDTO userDTO) {
        sql.insert("User.insertUser", userDTO);
    }
}
