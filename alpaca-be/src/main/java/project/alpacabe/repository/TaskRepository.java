package project.alpacabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.alpacabe.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByUserIdAndParentId(Long userId, Long parentId);

	List<Task> findByParentId(Long parentId);
}
