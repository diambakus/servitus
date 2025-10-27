package com.orakuma.servitus.organ;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.orakuma.servitus.fake.OrganFK;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrganServiceImplTest {

    @Mock
    private OrganRepository organRepository;

    @Mock
    private OrganMapper organMapper;

    @Mock
    private RepositoriesHandler repositoriesHandler;

    @InjectMocks
    private OrganServiceImpl organService;

    private OrganDto organDto;
    private Organ organ;

    @BeforeEach
    void setUp() {
        organDto = OrganFK.getDto();
        organ = OrganFK.getEntity();
    }

    @Test
    void testGets() {
        List<Organ> activeOrgans = Collections.singletonList(organ);
        when(organRepository.findAllActive()).thenReturn(activeOrgans);
        when(organMapper.toOrgansDto(activeOrgans)).thenReturn(Collections.singletonList(organDto));

        Iterable<OrganDto> result = organService.gets();

        assertNotNull(result);
        assertEquals(1, ((Collection<?>) result).size());
        verify(organRepository, times(1)).findAllActive();
    }

    @Test
    void testGetAllInactive() {
        List<Organ> inactiveOrgans = Collections.singletonList(organ);
        when(organRepository.findAllInactive()).thenReturn(inactiveOrgans);
        when(organMapper.toOrgansDto(inactiveOrgans)).thenReturn(Collections.singletonList(organDto));

        Iterable<OrganDto> result = organService.getAllInactive();

        assertNotNull(result);
        assertEquals(1, ((Collection<?>) result).size());
        verify(organRepository, times(1)).findAllInactive();
    }

    @Test
    void testPersist_NewOrgan() {
        when(organMapper.toOrgan(organDto)).thenReturn(organ);
        when(organRepository.save(organ)).thenReturn(organ);
        when(organMapper.toOrganDto(organ)).thenReturn(organDto);

        Optional<OrganDto> result = organService.persist(organDto);

        assertTrue(result.isPresent());
        assertEquals(organDto, result.get());
        verify(organRepository, times(1)).save(organ);
    }

    @Test
    void testPersist_ExistingOrgan() {
        organDto = new OrganDto(1L, "Heart", "note", "content");
        when(organMapper.toOrgan(organDto)).thenReturn(organ);
        when(organRepository.existsById(1L)).thenReturn(true);
        when(organRepository.save(organ)).thenReturn(organ);
        when(organMapper.toOrganDto(organ)).thenReturn(organDto);

        Optional<OrganDto> result = organService.persist(organDto);

        assertTrue(result.isPresent());
        assertEquals(organDto, result.get());
        verify(organRepository, times(1)).save(organ);
    }

    @Test
    void testFindBy_OrganExists() {
        when(organRepository.existsById(1L)).thenReturn(true);
        when(organRepository.findById(1L)).thenReturn(Optional.of(organ));
        when(organMapper.toOrganDto(organ)).thenReturn(organDto);

        Optional<OrganDto> result = organService.findBy(1L);

        assertTrue(result.isPresent());
        assertEquals(organDto, result.get());
        verify(organRepository, times(1)).findById(1L);
    }

    @Test
    void testFindBy_OrganDoesNotExist() {
        when(organRepository.existsById(1L)).thenReturn(false);

        Optional<OrganDto> result = organService.findBy(1L);

        assertFalse(result.isPresent());
        verify(organRepository, times(0)).findById(1L);
    }

    @Test
    void testInactivate_OrganDoesNotExist() {
        when(organRepository.existsById(1L)).thenReturn(false);

        int result = organService.inactivate(1L);

        assertEquals(-1, result);
        verify(organRepository, times(0)).countByAssociatedUnits(1L);
    }

    @Test
    void testInactivate_OrganHasAssociatedUnits() {
        when(organRepository.existsById(1L)).thenReturn(true);
        when(organRepository.countByAssociatedUnits(1L)).thenReturn(1L);

        int result = organService.inactivate(1L);

        assertEquals(0, result);
        verify(organRepository, times(1)).countByAssociatedUnits(1L);
    }

    @Test
    void testInactivate_OrganHasNoAssociatedUnits() {
        when(organRepository.existsById(1L)).thenReturn(true);
        when(organRepository.countByAssociatedUnits(1L)).thenReturn(0L);
        when(repositoriesHandler.getOrganById(1L)).thenReturn(organ);

        int result = organService.inactivate(1L);

        assertEquals(1, result);
        verify(organRepository, times(1)).save(organ);
    }
}
