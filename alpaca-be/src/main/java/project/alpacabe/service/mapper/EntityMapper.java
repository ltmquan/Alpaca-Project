package project.alpacabe.service.mapper;

import java.util.List;

public interface EntityMapper<Data, DTO> {

	Data toEntity(DTO dto);

	DTO toDTO(Data data);

	List<Data> toEntity(List<DTO> dto);

	List<DTO> toDTO(List<Data> data);

}
