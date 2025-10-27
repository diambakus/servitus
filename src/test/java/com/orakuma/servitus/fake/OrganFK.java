package com.orakuma.servitus.fake;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;

import java.time.LocalDate;

public class OrganFK {
    private OrganFK() {
    }

    public static Organ getEntity() {
        Organ organ = new Organ();
        organ.setId(1L);
        organ.setName("Heart");
        organ.setDescription("A vital organ");
        organ.setActive(true);
        organ.setCreated(LocalDate.now());
        organ.setModified(null);

        return organ;
    }

    public static OrganDto getDto() {
        return new OrganDto(1L, "Heart", "description", "content");
    }
}
