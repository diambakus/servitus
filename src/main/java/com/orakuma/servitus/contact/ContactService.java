package com.orakuma.servitus.contact;

import java.util.Map;
import java.util.Optional;

public interface ContactService {
    Iterable<Contact> getContacts();
    Iterable<ContactDto> getContactsByEntityIdAndType(Long entityId, String entityType);
    Optional<Contact> getContactById(Long id);
    Optional<ContactDto> addContact(Long entityId, String entityType, ContactDto contactDto);
    Optional<ContactDto> update(Long contactId, Map<String, Object> updatingPropertiesMap);
}
