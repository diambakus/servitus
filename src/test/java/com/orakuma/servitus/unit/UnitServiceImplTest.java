package com.orakuma.servitus.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.orakuma.servitus.fake.OrganFK;
import com.orakuma.servitus.fake.UnitFK;
import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;
import com.orakuma.servitus.organ.OrganMapper;
import com.orakuma.servitus.organ.OrganRepository;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UnitServiceImplTest {

    @Mock
    private UnitRepository repository;

    @Mock
    private UnitMapper mapper;

    @Mock
    private OrganRepository organRepository;

    @Mock
    private OrganMapper organMapper;

    @Mock
    private RepositoriesHandler repositoriesHandler;

    @InjectMocks
    private UnitServiceImpl unitService;

    private UnitDto unitDto;
    private Unit unit;
    private Organ organ;

    @BeforeEach
    void setUp() {
        organ = OrganFK.getEntity();

        unitDto = new UnitDto(
                1L,
                "Unit1",
                "Sample unit",
                OrganFK.getDto(),
                UnitFK.getEntity().getAttributes(),
                Set.of()
        );

        unit = UnitFK.getEntity();
        unit.setOrgan(organ);
    }

    @Test
    void testGets() {
        List<Unit> activeUnits = Collections.singletonList(unit);
        when(repository.findActiveUnits()).thenReturn(activeUnits);
        when(mapper.toUnitsDto(activeUnits)).thenReturn(Collections.singletonList(unitDto));

        List<UnitDto> result = unitService.gets();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findActiveUnits();
    }

    @Test
    void testGetAllInactive() {
        List<Unit> inactiveUnits = Collections.singletonList(unit);
        when(repository.findInactiveUnits()).thenReturn(inactiveUnits);
        when(mapper.toUnitsDto(inactiveUnits)).thenReturn(Collections.singletonList(unitDto));

        List<UnitDto> result = unitService.getAllInactive();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findInactiveUnits();
    }

    @Test
    void testCreate_Success() {
        when(mapper.toUnit(unitDto)).thenReturn(unit);
        when(repositoriesHandler.getOrganById(1L)).thenReturn(organ);
        when(repository.save(unit)).thenReturn(unit);
        when(mapper.toUnitDto(unit)).thenReturn(unitDto);

        Optional<UnitDto> result = unitService.create(unitDto);

        assertTrue(result.isPresent());
        assertEquals(unitDto, result.get());
        verify(repository, times(1)).save(unit);
    }

    @Test
    void testCreate_Failure_OrganIsNull() {
        unitDto = new UnitDto(1L, "Unit1", "Sample unit", null, Map.of(), Set.of());
        when(mapper.toUnit(unitDto)).thenReturn(unit);

        Exception exception = assertThrows(NullPointerException.class, () -> unitService.create(unitDto));

        assertTrue(exception.getMessage().contains("OrganDto") && exception.getMessage().contains("is null"));
        verify(repository, times(0)).save(unit);
    }

    @Test
    void testGetBy() {
        when(repositoriesHandler.getUnitById(1L)).thenReturn(unit);
        when(mapper.toUnitDto(unit)).thenReturn(unitDto);

        Optional<UnitDto> result = unitService.getBy(1L);

        assertTrue(result.isPresent());
        assertEquals(unitDto, result.get());
        verify(repositoriesHandler, times(1)).getUnitById(1L);
    }

    @Test
    void testGetByOrgan() {
        List<Unit> units = Collections.singletonList(unit);
        when(repository.findActiveUnitsByOrgan(1L)).thenReturn(units);
        when(mapper.toUnitsDto(units)).thenReturn(Collections.singletonList(unitDto));

        List<UnitDto> result = unitService.getByOrgan(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findActiveUnitsByOrgan(1L);
    }

    @Test
    void testGetOrgan() {
        when(repositoriesHandler.getUnitById(1L)).thenReturn(unit);
        when(organMapper.toOrganDto(organ)).thenReturn(OrganFK.getDto());

        OrganDto result = unitService.getOrgan(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(repositoriesHandler, times(1)).getUnitById(1L);
    }

    @Test
    void testInactivate_UnitDoesNotExist() {
        when(repository.existsById(1L)).thenReturn(false);

        int result = unitService.inactivate(1L);

        assertEquals(-1, result);
        verify(repository, times(0)).countByAssociatedServices(1L);
    }

    @Test
    void testInactivate_UnitHasAssociatedServices() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.countByAssociatedServices(1L)).thenReturn(1);

        int result = unitService.inactivate(1L);

        assertEquals(0, result);
        verify(repository, times(1)).countByAssociatedServices(1L);
    }

    @Test
    void testInactivate_UnitHasNoAssociatedServices() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.countByAssociatedServices(1L)).thenReturn(0);
        when(repositoriesHandler.getUnitById(1L)).thenReturn(unit);

        int result = unitService.inactivate(1L);

        assertEquals(1, result);
        verify(repository, times(1)).save(unit);
    }

    @Test
    void testUpdateUnitWithProperties() {
        Map<String, String> fieldsContentMap = new LinkedHashMap<>();
        fieldsContentMap.put("size", "large");
        when(repositoriesHandler.getUnitById(1L)).thenReturn(unit);
        when(repository.save(unit)).thenReturn(unit);
        when(mapper.toUnitDto(unit)).thenReturn(unitDto);

        UnitDto result = unitService.updateUnitWithProperties(1L, fieldsContentMap);

        assertNotNull(result);
        assertEquals(unitDto, result);
        verify(repository, times(1)).save(unit);
    }
}
