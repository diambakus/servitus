package com.orakuma.servitus.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.orakuma.servitus.fake.ServisFK;
import com.orakuma.servitus.fake.UnitFK;
import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServisServiceImplTest {

    @Mock
    private ServisRepository servisRepository;

    @Mock
    private ServisMapper servisMapper;

    @Mock
    private RepositoriesHandler repositoriesHandler;

    @InjectMocks
    private ServisServiceImpl servisService;

    private ServisDto servisDto;
    private Servis servis;
    private Unit unit;

    @BeforeEach
    void setUp() {
        unit = UnitFK.getEntity();
        servisDto = new ServisDto(1L, "Service1", 100.0, ServisType.SERVICE, "Additional details", Set.of(UnitFK.getDto()));
        servis = ServisFK.getEntity();
        servis.addUnit(unit);
    }

    @Test
    void testGet() {
        when(repositoriesHandler.getServisById(1L)).thenReturn(servis);
        when(servisMapper.toServisDto(servis)).thenReturn(servisDto);

        ServisDto result = servisService.get(1L);

        assertNotNull(result);
        assertEquals(servisDto, result);
        verify(repositoriesHandler, times(1)).getServisById(1L);
    }

    @Test
    void testCreate() {
        when(servisMapper.toServis(servisDto)).thenReturn(servis);
        when(repositoriesHandler.getUnitById(1L)).thenReturn(unit);
        when(servisRepository.save(servis)).thenReturn(servis);
        when(servisMapper.toServisDto(servis)).thenReturn(servisDto);

        ServisDto result = servisService.create(servisDto);

        assertNotNull(result);
        assertEquals(servisDto, result);
        verify(servisRepository, times(1)).save(servis);
    }

    @Test
    void testCreate_NoUnits() {
        servisDto = ServisFK.getDto();
        when(servisMapper.toServis(servisDto)).thenReturn(servis);
        when(servisRepository.save(servis)).thenReturn(servis);
        when(servisMapper.toServisDto(servis)).thenReturn(servisDto);

        ServisDto result = servisService.create(servisDto);

        assertNotNull(result);
        assertEquals(servisDto, result);
        verify(servisRepository, times(1)).save(servis);
    }

    @Test
    void testGetByUnit() {
        List<Servis> servisList = Collections.singletonList(servis);
        List<ServisDto> servisDtoList = Collections.singletonList(servisDto);

        when(servisRepository.findAllActiveByUnit(1L)).thenReturn(servisList);
        when(servisMapper.toServisDtos(servisList)).thenReturn(servisDtoList);

        List<ServisDto> result = servisService.getByUnit(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(servisDto, result.get(0));
        verify(servisRepository, times(1)).findAllActiveByUnit(1L);
    }

    @Test
    void testGetAllActive() {
        List<Servis> servisList = Collections.singletonList(servis);
        List<ServisDto> servisDtoList = Collections.singletonList(servisDto);

        when(servisRepository.findAllActive()).thenReturn(servisList);
        when(servisMapper.toServisDtos(servisList)).thenReturn(servisDtoList);

        List<ServisDto> result = servisService.getAllActive();

        assertNotNull(result);
        assertEquals(1, ((Collection<?>) result).size());
        verify(servisRepository, times(1)).findAllActive();
    }
}