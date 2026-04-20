package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.dto.TeacherResponseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository {
   public void save(TeacherDTO teacher);

   Optional<TeacherResponseDTO> find(Credentials cred);
}
