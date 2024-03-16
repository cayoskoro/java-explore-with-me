package ru.practicum.comment.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CommentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCommentFromNewCommentDto(NewCommentDto dto, @MappingTarget Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Comment convertNewCommentDtoToEntity(NewCommentDto dto);

    @Mapping(target = "eventId", source = "event.id")
    CommentDto convertToDto(Comment entity);

    Collection<CommentDto> convertToDtoCollection(Collection<Comment> entities);

    Comment clone(Comment entity);
}
