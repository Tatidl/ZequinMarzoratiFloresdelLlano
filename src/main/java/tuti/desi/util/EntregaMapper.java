package tuti.desi.util;

import tuti.desi.entidades.EntregaAsistencia;
import tuti.desi.presentacion.entregas.EntregaResumenDTO;

public class EntregaMapper {
    public static EntregaResumenDTO aDTO(EntregaAsistencia entrega) {
        return new EntregaResumenDTO(
                entrega.getId(),
                entrega.getFamilia().getNroFamilia(),
                entrega.getFamilia().getNombre(),
                entrega.getPreparacion().getReceta().getNombre(),
                entrega.getCantidadRaciones(),
                entrega.getFecha()
        );
    }
}