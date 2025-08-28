package com.orakuma.servitus.fake;

import com.orakuma.servitus.contact.Contact;
import com.orakuma.servitus.contact.ContactType;

import java.time.LocalDateTime;

public class ContactTestFactory {

    public static Contact createFakeContact() {
        return new Contact()
                .id(1L)
                .name("John Doe")
                .phone("+1-555-1234")
                .email("john.doe@example.com")
                .contactType(ContactType.MAIN)
                .created(LocalDateTime.now());
    }

    public static Contact createFakeContact(Long id, String name, String phone, String email, ContactType type) {
        return new Contact()
                .id(id)
                .name(name)
                .phone(phone)
                .email(email)
                .contactType(type)
                .created(LocalDateTime.now());
    }
}

