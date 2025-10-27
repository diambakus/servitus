package com.orakuma.servitus.address;

import com.orakuma.servitus.utils.EntityTypeConverter;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository       addressRepository;
    private final EntityAddressRepository entityAddressRepository;
    private final AddressMapper           addressMapper;
    private final RepositoriesHandler repositoriesHandler;

    public AddressServiceImpl(
            AddressRepository addressRepository,
            EntityAddressRepository entityAddressRepository,
            RepositoriesHandler repositoriesHandler) {
        this.addressRepository = addressRepository;
        this.entityAddressRepository = entityAddressRepository;
        this.addressMapper = Mappers.getMapper(AddressMapper.class);
        this.repositoriesHandler = repositoriesHandler;
    }

    @Override
    public Iterable<Address> getAddresses() {
        return null;
    }

    @Override
    public Iterable<AddressDto> getAddressesByEntityIdAndType(Long entityId, String entityType) {
        Iterable<Long> ids = entityAddressRepository.findAddressesByEntityId(entityId, entityType);
        Iterable<Address> addresses = addressRepository.findAllById(ids);
        return addressMapper.toAddressDto(addresses);
    }

    @Override
    @Transactional
    public Optional<AddressDto> addAddress(Long entityId, String entityType, AddressDto addressDto) {
        Address address = addressMapper.toAddress(addressDto).setCreated(LocalDateTime.now());
        address = addressRepository.save(address);
        EntityAddress entityAddress = new EntityAddress()
                .entityType(entityType)
                .entityId(entityId)
                .address(address);
        entityAddressRepository.save(entityAddress);
        return Optional.of(addressMapper.toAddressDto(address));
    }

    @Override
    @Transactional
    public Optional<AddressDto> update(Long addressId, Map<String, Object> updatingFieldsValue) {
        Address address = repositoriesHandler.getAddressById(addressId);
        updatingFieldsValue.forEach((key, value) -> {
           if (key.equalsIgnoreCase("street")) {
               address.setStreet(value.toString());
           } else if (key.equalsIgnoreCase("city")) {
               address.setCity(value.toString());
           } else if (key.equalsIgnoreCase("state")) {
               address.setState(value.toString());
           } else if (key.equalsIgnoreCase("country")) {
               address.setCountry(value.toString());
           } else if (key.equalsIgnoreCase("postalCode")) {
               address.setPostalCode(value.toString());
           } else if (key.equalsIgnoreCase("addressType")) {
               address.setAddressType(EntityTypeConverter.toAddressType(value.toString()));
           } else if (key.equalsIgnoreCase("addressNumber")) {
               address.setAddressNumber(value.toString());
           } else {
               throw new IllegalArgumentException("Unknown property: " + key);
           }
        });

        Address persistedAddress = addressRepository.save(address);
        return Optional.of(addressMapper.toAddressDto(persistedAddress));
    }
}
