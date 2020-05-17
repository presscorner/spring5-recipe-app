package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureConverter;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureConverter unitOfMeasureConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureConverter unitOfMeasureConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureConverter::convertFromEntity)
                .collect(Collectors.toSet());
    }
}
