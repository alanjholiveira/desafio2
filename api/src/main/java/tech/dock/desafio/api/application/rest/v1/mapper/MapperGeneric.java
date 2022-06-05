package tech.dock.desafio.api.application.rest.v1.mapper;

import org.modelmapper.ModelMapper;

public abstract class MapperGeneric {

    public static final ModelMapper mapper;

    static {
        mapper = new ModelMapper();
    }

}
