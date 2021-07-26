package ru.securities.mapper;

public interface SecuritiesMapper<Model, DTO> {
    DTO toDTO(Model model);
    Model toModel(DTO dto);
}
