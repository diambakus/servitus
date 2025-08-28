package com.orakuma.servitus.contact;

import java.util.Map;
import java.util.Optional;

public class ContactServiceImplTest implements ContactService {
    @Override
    public Iterable<Contact> getContacts() {
        return null;
    }

    @Override
    public Iterable<ContactDto> getContactsByEntityIdAndType(Long entityId, String entityType) {
        return null;
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactDto> addContact(Long entityId, String entityType, ContactDto contactDto) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactDto> update(Long contactId, Map<String, Object> updatingPropertiesMap) {
        return Optional.empty();
    }
}
