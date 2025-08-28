package com.orakuma.servitus.address;

import com.orakuma.servitus.utils.EntityTypeConverter;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

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
    public Optional<AddressDto> addAddress(Long entityId, String entityType, AddressDto addressDto) {
        Address address = addressMapper.toAddress(addressDto).created(LocalDateTime.now());
        address = addressRepository.save(address);
        EntityAddress entityAddress = new EntityAddress()
                .entityType(entityType)
                .entityId(entityId)
                .address(address);
        entityAddressRepository.save(entityAddress);
        return Optional.of(addressMapper.toAddressDto(address));
    }

    @Override
    public Optional<AddressDto> update(Long addressId, Map<String, Object> updatingFieldsValue) {
        Address address = repositoriesHandler.getAddressById(addressId);
        updatingFieldsValue.forEach((key, value) -> {
           if (key.equalsIgnoreCase("street")) {
               address.street(value.toString());
           } else if (key.equalsIgnoreCase("city")) {
               address.city(value.toString());
           } else if (key.equalsIgnoreCase("state")) {
               address.state(value.toString());
           } else if (key.equalsIgnoreCase("country")) {
               address.country(value.toString());
           } else if (key.equalsIgnoreCase("postalCode")) {
               address.postalCode(value.toString());
           } else if (key.equalsIgnoreCase("addressType")) {
               address.addressType(EntityTypeConverter.toAddressType(value.toString()));
           } else {
               throw new IllegalArgumentException("Unknown property: " + key);
           }
        });

        Address persistedAddress = addressRepository.save(address);
        return Optional.of(addressMapper.toAddressDto(persistedAddress));
    }
}
