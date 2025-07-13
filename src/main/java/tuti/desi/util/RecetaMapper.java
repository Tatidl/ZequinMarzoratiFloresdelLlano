package tuti.desi.util;

import tuti.desi.entidades.Ingrediente;
import tuti.desi.entidades.ItemReceta;
import tuti.desi.entidades.Receta;
import tuti.desi.presentacion.recetas.ItemRecetaForm;
import tuti.desi.presentacion.recetas.RecetaForm;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecetaMapper {

    public static Receta aEntidad(RecetaForm form, Function<Long, Ingrediente> buscardorIngredientes) {
        var receta = new Receta();
        receta.setId(form.getId());
        receta.setNombre(form.getNombre());
        receta.setDescripcion(form.getDescripcion());
        receta.setActiva(true);
        form.getIngredientes().forEach(ingrediente -> {
            var itemReceta = new ItemReceta();
            itemReceta.setId(ingrediente.getId());
            itemReceta.setReceta(receta);
            itemReceta.setIngrediente(buscardorIngredientes.apply(ingrediente.getIdIngrediente()));
            itemReceta.setCantidadKg(BigDecimal.valueOf(ingrediente.getCantidadKg()));
            itemReceta.setCalorias(ingrediente.getCalorias());
            itemReceta.setActivo(true);
            receta.getItems().add(itemReceta);
        });
        receta.setCaloriasTotales(receta.getItems().stream()
                .filter(ItemReceta::isActivo)
                .mapToInt(ItemReceta::getCalorias)
                .sum());
        return receta;
    }

    public static void actualizar(Receta recetaObjetivo, RecetaForm form, Function<Long, Ingrediente> buscardorIngredientes) {
        recetaObjetivo.setDescripcion(form.getDescripcion());

        // Índice de ítems actuales por ID
        Map<Long, ItemReceta> itemsPorId = recetaObjetivo.getItems().stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(ItemReceta::getId, item -> item));

        // Marcar como inactivos los ítems que no están en el formulario
        recetaObjetivo.getItems().forEach(item -> {
            if (item.getId() != null &&
                    form.getIngredientes().stream().noneMatch(i -> item.getId().equals(i.getId()))) {
                item.setActivo(false);
            }
        });

        // Procesar ítems del formulario
        for (ItemRecetaForm ingrediente : form.getIngredientes()) {
            ItemReceta itemReceta;
            if (ingrediente.getId() != null && itemsPorId.containsKey(ingrediente.getId())) {
                // Actualizar ítem existente
                itemReceta = itemsPorId.get(ingrediente.getId());
                itemReceta.setActivo(true); // Reactivar si estaba inactivo
            } else {
                // Nuevo ítem
                itemReceta = new ItemReceta();
                itemReceta.setReceta(recetaObjetivo);
                itemReceta.setActivo(true);
                recetaObjetivo.getItems().add(itemReceta);
            }
            // Actualizar campos del ítem
            itemReceta.setIngrediente(buscardorIngredientes.apply(ingrediente.getIdIngrediente()));
            itemReceta.setCantidadKg(BigDecimal.valueOf(ingrediente.getCantidadKg()));
            itemReceta.setCalorias(ingrediente.getCalorias());
        }

        // Actualizar calorías totales (solo ítems activos)
        recetaObjetivo.setCaloriasTotales(recetaObjetivo.getItems().stream()
                .filter(ItemReceta::isActivo)
                .mapToInt(ItemReceta::getCalorias)
                .sum());
    }
}