package tuti.desi.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.accesoDatos.IFamiliaRepo;
import tuti.desi.entidades.Asistido;
import tuti.desi.entidades.Familia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.familias.FamiliaForm;
import tuti.desi.presentacion.familias.FamiliaResumenDTO;
import tuti.desi.presentacion.familias.IntegranteDTO;
import tuti.desi.servicios.FamiliaService;
import tuti.desi.util.FamiliaMapper;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FamiliaServiceImpl implements FamiliaService {

    private final IFamiliaRepo repo;

    // Auxiliares
    private void validarDNIUnico(FamiliaForm form) {
        var repetidos = form.getIntegrantes().stream()
                .collect(Collectors.groupingBy(i -> i.getDni(), Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey).toList();
        if (!repetidos.isEmpty()) {
            throw new Excepcion("DNI repetido dentro de la familia: " + repetidos);
        }
    }

    private Integer generarNroFamilia() {
        return repo.findAll().stream()
                .map(Familia::getNroFamilia)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    // ------------------------------------------------

    @Override
    public FamiliaForm alta(FamiliaForm form) {
        validarDNIUnico(form);

        if (form.getNroFamilia() == null) {
            form.setNroFamilia(generarNroFamilia());
        }
        if (repo.existsByNroFamiliaAndActivaTrue(form.getNroFamilia())) {
            throw new Excepcion("Ya existe la familia " + form.getNroFamilia());
        }

        Familia entidad = FamiliaMapper.aEntidad(form);
        repo.save(entidad);
        form.setId(entidad.getId());
        return form;
    }

    @Override
    public FamiliaForm editar(Long id, FamiliaForm form) {
        validarDNIUnico(form);

        Familia familia = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Familia no encontrada"));

        FamiliaMapper.fusionar(familia, form);
        return form;
    }

    @Override
    public void baja(Long id) {
        Familia familia = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Familia no encontrada"));
        familia.setActiva(false);
    }

    @Override
    public Page<FamiliaResumenDTO> listar(String filtro, Pageable pageable) {

        var pagina = repo.findByActivaTrueAndNombreContainingIgnoreCase(
                filtro == null ? "" : filtro,
                pageable);

        return pagina.map(f -> {
            var integrantesActivos = f.getIntegrantes().stream()
                    .filter(Asistido::isActivo)
                    .toList();

            var integrantesDto = integrantesActivos.stream()
                    .map(a -> new IntegranteDTO(
                            a.getId(),
                            a.getDni(),
                            a.getApellido(),
                            a.getNombre(),
                            a.getFechaNacimiento(),
                            a.getOcupacion()))
                    .toList();

            return new FamiliaResumenDTO(
                    f.getId(),
                    f.getNroFamilia(),
                    f.getNombre(),
                    integrantesDto.size(),
                    f.getFechaRegistro(),
                    integrantesDto);
        });
    }

    // ---------- soporte para edición ---------------------------------------
    @Override
    @Transactional(readOnly = true)
    public FamiliaForm obtenerParaEdicion(Long id) {
        Familia familia = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Familia no encontrada"));
        return FamiliaMapper.aForm(familia);
    }
}