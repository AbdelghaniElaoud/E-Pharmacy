package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import com.epharmacy.app.model.Prescription;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionMapper INSTANCE = Mappers.getMapper(PrescriptionMapper.class);

    PrescriptionDTO toDTO(Prescription source);

    @AfterMapping
    default void afterToDTO(Prescription source, @MappingTarget PrescriptionDTO target) {
        target.setLink(source.getMedia().getLink());
    }
}
