package com.orakuma.servitus.fake;

import com.orakuma.servitus.contact.Contact;
import com.orakuma.servitus.contact.ContactType;

import java.time.LocalDateTime;

public class ContactTestFactory {

    public static Contact createFakeContact() {
        return new Contact()
                .setId(1L)
                .setName("John Doe")
                .setPhone("+1-555-1234")
                .setEmail("john.doe@example.com")
                .setContactType(ContactType.MAIN)
                .setCreated(LocalDateTime.now());
    }

    public static Contact createFakeContact(Long id, String name, String phone, String email, ContactType type) {
        return new Contact()
                .setId(id)
                .setName(name)
                .setPhone(phone)
                .setEmail(email)
                .setContactType(type)
                .setCreated(LocalDateTime.now());
    }
}

