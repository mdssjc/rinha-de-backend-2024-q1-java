package dev.marcelomds.rinhadebackend2024q1.infrastructure.repositories;

import dev.marcelomds.rinhadebackend2024q1.domains.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClientDao {

    public static final String FIND_BY_ID = "SELECT * FROM clientes WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Cacheable("clients")
    public Optional<Client> findById(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID,
                    (result, rowNum) ->
                            new Client(
                                    result.getInt("id"),
                                    result.getString("nome"),
                                    result.getInt("limite"))
                    , id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
