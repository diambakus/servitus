package com.orakuma.servitus.contact;

import com.orakuma.servitus.utils.EntityTypeConverter;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactMapper           contactMapper;
    private final ContactRepository       contactRepository;
    private final EntityContactRepository entityContactRepository;
    private final RepositoriesHandler repositoriesHandler;

    public ContactServiceImpl(
            ContactRepository contactRepository,
            EntityContactRepository entityContactRepository,
            RepositoriesHandler repositoriesHandler) {
        this.contactMapper = Mappers.getMapper(ContactMapper.class);
        this.contactRepository = contactRepository;
        this.entityContactRepository = entityContactRepository;
        this.repositoriesHandler = repositoriesHandler;
    }

    @Override
    public Iterable<Contact> getContacts() {
        return null;
    }

    @Override
    public Iterable<ContactDto> getContactsByEntityIdAndType(Long entityId, String entityType) {
        Iterable<Long> ids = entityContactRepository.findContactIdsByContactIdAndContactType(entityId, entityType);
        Iterable<Contact> contacts = contactRepository.findAllById(ids);
        return contactMapper.toDto(contacts);
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactDto> addContact(Long entityId, String entityType, ContactDto contactDto) {
        Contact contact = contactMapper.toEntity(contactDto).created(LocalDateTime.now());
        contact = contactRepository.save(contact);
        EntityContact entityContact = new EntityContact()
                .entityType(entityType)
                .entityId(entityId)
                .contact(contact);
        entityContactRepository.save(entityContact);
        return Optional.of(contactMapper.toDto(contact));
    }

    @Override
    public Optional<ContactDto> update(Long contactId, Map<String, Object> updatingPropertiesMap) {
        Contact contact = repositoriesHandler.getContactById(contactId);
        updatingPropertiesMap.forEach((key, value) -> {
           if (key.equalsIgnoreCase("name")) {
               contact.name((String) value);
           } else if (key.equalsIgnoreCase("phone")) {
               contact.phone((String) value);
           } else if (key.equalsIgnoreCase("email")) {
               contact.email((String) value);
           } else if (key.equalsIgnoreCase("contactType")) {
               contact.contactType(EntityTypeConverter.toContactType((String) value));
           } else {
               throw new IllegalArgumentException("Unknown property: " + key);
           }
        });

        Contact persistedContact = contactRepository.save(contact);
        return Optional.of(contactMapper.toDto(persistedContact));
    }
}
