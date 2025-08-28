package com.orakuma.servitus.contact;

public record ContactDto(
   Long   id,
   String name,
   String phone,
   String email,
   String contactType
) {}
