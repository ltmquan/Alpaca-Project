package project.alpacabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.alpacabe.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findByTaskId(Long taskId);
}
