package com.orakuma.servitus.fake;

import com.orakuma.servitus.contact.Contact;
import com.orakuma.servitus.contact.EntityContact;

public class EntityContactTestFactory {

    public static EntityContact createFakeEntityContact() {
        return new EntityContact()
                .id(1L)
                .entityType("CUSTOMER")
                .entityId(1001L)
                .contact(ContactTestFactory.createFakeContact());
    }

    public static EntityContact createFakeEntityContact(Long id, String entityType, Long entityId, Contact contact) {
        return new EntityContact()
                .id(id)
                .entityType(entityType)
                .entityId(entityId)
                .contact(contact);
    }
}

