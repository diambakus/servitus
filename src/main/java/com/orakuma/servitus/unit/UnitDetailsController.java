package com.orakuma.servitus.unit;

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
import static com.orakuma.servitus.utils.EntityTypeConverter.UNIT_ENTITY_TYPE;

@RestController
@RequestMapping(value = "unit")
public class UnitDetailsController {
    private final AddressService addressService;
    private final ContactService contactService;

    UnitDetailsController(
            AddressService addressService,
            ContactService contactService) {
        this.addressService = addressService;
        this.contactService = contactService;
    }

    @GetMapping("/address/{unitId}")
    @ApiResponse(responseCode = "200", description = "Fetch addresses by unit")
    public ResponseEntity<Iterable<AddressDto>> getAddressesByOrganId(@PathVariable("unitId") Long unitId) {
        Iterable<AddressDto> addressesDto = addressService.getAddressesByEntityIdAndType(unitId, UNIT_ENTITY_TYPE);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressesDto);
    }

    @PostMapping("/address/{unitId}")
    @ApiResponse(responseCode = "201", description = "Enhance addresses to the unit")
    public ResponseEntity<Iterable<AddressDto>> addAddress(
            @PathVariable("unitId") Long unitId,
            @RequestBody Iterable<AddressDto> addressesDto) {
        Iterable<AddressDto> savedAddresses = addAddresses(unitId, addressesDto);
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

    @GetMapping("/contact/{unitId}")
    @ApiResponse(responseCode = "200", description = "Fetch contacts by unit")
    public ResponseEntity<Iterable<ContactDto>> getContactsByOrganId(@PathVariable("unitId") Long unitId) {
        Iterable<ContactDto> contactsDto = contactService.getContactsByEntityIdAndType(unitId, ORGAN_ENTITY_TYPE);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contactsDto);
    }

    @PostMapping("/contact/{unitId}")
    @ApiResponse(responseCode = "201", description = "Enhance contacts to the unit")
    public ResponseEntity<Iterable<ContactDto>> addContact(
            @PathVariable("unitId") Long unitId,
            @RequestBody Iterable<ContactDto> contacts) {
        Iterable<ContactDto> addedContacts = addContacts(unitId, contacts);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(addedContacts);
    }

    @PatchMapping("/contact/{id}")
    @ApiResponse(responseCode = "200", description = "Update a contact information")
    public ResponseEntity<Optional<ContactDto>> updateContact(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> fieldsValues) {
        Optional<ContactDto> updatedContact = contactService.update(id, fieldsValues);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedContact);
    }

    private Iterable<AddressDto> addAddresses(Long unitId, Iterable<AddressDto> addressesDto) {
        List<AddressDto> resultAddressesDto = new LinkedList<>();
        Optional<AddressDto> addressDto;
        for (AddressDto addressDtoItem : addressesDto) {
            addressDto = addressService.addAddress(unitId, UNIT_ENTITY_TYPE, addressDtoItem);
            resultAddressesDto.add(addressDto.orElse(null));
        }
        return resultAddressesDto;
    }

    private Iterable<ContactDto> addContacts(Long unitId, Iterable<ContactDto> contacts) {
        List<ContactDto> resultContactsDto = new LinkedList<>();
        Optional<ContactDto> contactDto;
        for (ContactDto contactDtoItem : contacts) {
            contactDto = contactService.addContact(unitId, UNIT_ENTITY_TYPE, contactDtoItem);
            resultContactsDto.add(contactDto.orElse(null));
        }
        return resultContactsDto;
    }
}