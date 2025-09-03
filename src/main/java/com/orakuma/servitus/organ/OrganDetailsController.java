package com.orakuma.servitus.organ;

import com.orakuma.servitus.address.AddressDto;
import com.orakuma.servitus.address.AddressService;
import com.orakuma.servitus.contact.ContactDto;
import com.orakuma.servitus.contact.ContactService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.orakuma.servitus.utils.EntityTypeConverter.ORGAN_ENTITY_TYPE;

@RestController
@RequestMapping(value = "organ")
public class OrganDetailsController {
    private final AddressService addressService;
    private final ContactService contactService;

    OrganDetailsController(
            AddressService addressService,
            ContactService contactService) {
        this.addressService = addressService;
        this.contactService = contactService;
    }

    @GetMapping("/address/{organId}")
    @ApiResponse(responseCode = "200", description = "Fetch addresses by the organisation")
    public ResponseEntity<Iterable<AddressDto>> getAddressesByOrganId(@PathVariable("organId") Long organId) {
        Iterable<AddressDto> addressesDto = addressService.getAddressesByEntityIdAndType(organId, ORGAN_ENTITY_TYPE);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressesDto);
    }

    @PostMapping("/address/{organId}")
    @ApiResponse(responseCode = "201", description = "Enhance addresses to the organisation")
    public ResponseEntity<Iterable<AddressDto>> addAddress(
            @PathVariable("organId") Long organId,
            @RequestBody Iterable<AddressDto> addressDtos) {
        Iterable<AddressDto> savedAddresses = addAddresses(organId, addressDtos);
        return new ResponseEntity<>(savedAddresses, HttpStatus.CREATED);
    }

    @PatchMapping("/address/{id}")
    @ApiResponse(responseCode = "200", description = "Update an address information")
    public ResponseEntity<Optional<AddressDto>> updateAddress(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> fieldsValues) {
        Optional<AddressDto> updatedAddress = addressService.update(id, fieldsValues);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedAddress);
    }

    @GetMapping("/contact/{organId}")
    @ApiResponse(responseCode = "200", description = "Fetch contacts by the organisation")
    public ResponseEntity<Iterable<ContactDto>> getContactsByOrganId(@PathVariable("organId") Long organId) {
        Iterable<ContactDto> contactsDto = contactService.getContactsByEntityIdAndType(organId, ORGAN_ENTITY_TYPE);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contactsDto);
    }

    @PostMapping("/contact/{organId}")
    @ApiResponse(responseCode = "201", description = "Enhance contacts to the organisation")
    public ResponseEntity<Iterable<ContactDto>> addContact(
            @PathVariable("organId") Long organId,
            @RequestBody Iterable<ContactDto> contacts) {
        Iterable<ContactDto> addedContacts = addContacts(organId, contacts);
        return new ResponseEntity<>(addedContacts, HttpStatus.CREATED);
    }

    @PatchMapping("/contact/{id}")
    @ApiResponse(responseCode = "200", description = "Update a contact information")
    public ResponseEntity<Optional<ContactDto>> updateContact(@PathVariable("id") Long id, @RequestBody Map<String, Object> fieldsValues) {
        Optional<ContactDto> updatedContact = contactService.update(id, fieldsValues);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedContact);
    }

    private Iterable<AddressDto> addAddresses(Long organId, Iterable<AddressDto> addressesDto) {
        List<AddressDto> resultAddressesDto = new LinkedList<>();
        Optional<AddressDto> addressDto;
        for (AddressDto addressDtoItem : addressesDto) {
            addressDto = addressService.addAddress(organId, ORGAN_ENTITY_TYPE, addressDtoItem);
            resultAddressesDto.add(addressDto.orElse(null));
        }
        return resultAddressesDto;
    }

    private Iterable<ContactDto> addContacts(Long organId, Iterable<ContactDto> contacts) {
        List<ContactDto> resultContactsDto = new LinkedList<>();
        Optional<ContactDto> contactDto;
        for (ContactDto contactDtoItem : contacts) {
            contactDto = contactService.addContact(organId, ORGAN_ENTITY_TYPE, contactDtoItem);
            resultContactsDto.add(contactDto.orElse(null));
        }
        return resultContactsDto;
    }
}
