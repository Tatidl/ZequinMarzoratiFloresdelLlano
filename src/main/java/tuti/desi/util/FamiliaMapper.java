package tuti.desi.util;

import tuti.desi.entidades.Asistido;
import tuti.desi.entidades.Familia;
import tuti.desi.presentacion.familias.FamiliaForm;
import tuti.desi.presentacion.familias.IntegranteForm;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FamiliaMapper {

    // -------- entidad ← form (alta) ---------------
    public static Familia aEntidadFamilia(FamiliaForm form) {
        Familia familia = new Familia();
        familia.setId(form.getId());
        familia.setNombre(form.getNombre());
        familia.setNroFamilia(form.getNroFamilia());

        form.getIntegrantes().forEach(i -> familia.getIntegrantes().add(aEntidadAsistido(i, familia)));
        return familia;
    }

    // -------- entidad existente ← form (edición) ---
    public static void actualizar(Familia familia, FamiliaForm form) {
        // índice de integrantes actuales
        Map<Long, Asistido> porId = familia.getIntegrantes().stream()
                .filter(a -> a.getId() != null)
                .collect(Collectors.toMap(Asistido::getId, a -> a));

        // eliminamos los que ya no vienen en el formulario
        familia.getIntegrantes().removeIf(a ->
                form.getIntegrantes().stream().noneMatch(i -> a.getId() != null && a.getId().equals(i.getId())));

        // recorremos datos del form
        for (IntegranteForm iForm : form.getIntegrantes()) {
            Asistido asistido;
            if (iForm.getId() != null && porId.containsKey(iForm.getId())) {
                // si existe entonces actualizamos campos en la misma instancia
                asistido = porId.get(iForm.getId());
            } else {
                // si no, es un nuevo integrante
                asistido = new Asistido();
                asistido.setFamilia(familia);
                familia.getIntegrantes().add(asistido);
            }
            // campos comunes
            asistido.setDni(iForm.getDni());
            asistido.setApellido(iForm.getApellido());
            asistido.setNombre(iForm.getNombre());
            asistido.setFechaNacimiento(iForm.getFechaNacimiento());
            asistido.setOcupacion(iForm.getOcupacion());
        }

        // datos de la familia
        familia.setNombre(form.getNombre());
    }


    // -------- form ← entidad (para edición) --------
    public static FamiliaForm aForm(Familia entidad) {
        FamiliaForm f = new FamiliaForm();
        f.setId(entidad.getId());
        f.setNroFamilia(entidad.getNroFamilia());
        f.setNombre(entidad.getNombre());

        List<IntegranteForm> ints = entidad.getIntegrantes().stream()
                .filter(Asistido::isActivo)
                .map(FamiliaMapper::aIntegranteForm)
                .collect(Collectors.toList());

        f.setIntegrantes(ints);
        return f;
    }

    // -------- métodos auxiliares ------------------------------
    private static Asistido aEntidadAsistido(IntegranteForm i, Familia familia) {
        Asistido a = new Asistido();
        a.setId(i.getId());
        a.setDni(i.getDni());
        a.setApellido(i.getApellido());
        a.setNombre(i.getNombre());
        a.setFechaNacimiento(i.getFechaNacimiento());
        a.setOcupacion(i.getOcupacion());
        a.setFamilia(familia);
        return a;
    }

    private static IntegranteForm aIntegranteForm(Asistido a) {
        IntegranteForm f = new IntegranteForm();
        f.setId(a.getId());
        f.setDni(a.getDni());
        f.setApellido(a.getApellido());
        f.setNombre(a.getNombre());
        f.setFechaNacimiento(a.getFechaNacimiento());
        f.setOcupacion(a.getOcupacion());
        return f;
    }
}
