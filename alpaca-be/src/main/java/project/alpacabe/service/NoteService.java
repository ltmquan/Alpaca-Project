package project.alpacabe.service;

import java.util.List;

import project.alpacabe.model.dto.NoteDTO;

public interface NoteService {

	public NoteDTO findById(Long userId, Long id);

	public List<NoteDTO> findByTaskId(Long userId, Long taskId);

	public NoteDTO create(Long userId, NoteDTO noteDTO);

	public NoteDTO update(Long userId, NoteDTO noteDTO);

	public NoteDTO deleteById(Long userId, Long id);
}
