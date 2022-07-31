package springboot.webapp.utils;

public interface ModelMapping<Entity,DTO> {

	Entity dtoToEntity(DTO dto);
	DTO entityToDTO(Entity entity);
	
}
