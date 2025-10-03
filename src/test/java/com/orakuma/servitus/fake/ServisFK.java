package com.orakuma.servitus.fake;

import com.orakuma.servitus.servis.Servis;
import com.orakuma.servitus.servis.ServisDto;
import com.orakuma.servitus.servis.ServisType;

import java.time.LocalDate;
import java.util.Collections;

public class ServisFK {

    private ServisFK() {
    }

    public static Servis getEntity() {
        Servis servis = new Servis();
        servis.setId(1L);
        servis.setName("Service1");
        servis.setPrice(100.0);
        servis.setServisType(ServisType.SERVICE);
        servis.setDescription("description details");
        servis.setCreated(LocalDate.now());
        servis.setActive(true);
        return servis;
    }

    public static ServisDto getDto() {
        return new ServisDto(
                1L,
                "Service1",
                100.0, ServisType.SERVICE.name(),
                "Additional details",
                Collections.emptySet(),
                Collections.emptyMap()
        );
    }
}
