package com.orakuma.servitus.fake;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrganFK {
    private OrganFK() {
    }

    public static Organ getEntity() {

        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("color", "red");
        Organ organ = new Organ();
        organ.setId(1L);
        organ.setName("Heart");
        organ.setDescription("A vital organ");
        organ.setActive(true);
        organ.setCreated(LocalDate.now());
        organ.setModified(null);
        organ.setAttributes(attributes);

        return organ;
    }

    public static OrganDto getDto() {
        return new OrganDto(1L, "Heart", "description", new HashMap<>(), "content");
    }
}
