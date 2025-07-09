package tuti.desi.util;

import tuti.desi.entidades.Ingrediente;
import tuti.desi.entidades.ItemReceta;
import tuti.desi.entidades.Receta;
import tuti.desi.presentacion.recetas.RecetaForm;

import java.math.BigDecimal;
import java.util.function.Function;

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
            receta.getItems().add(itemReceta);
        });
        receta.setCaloriasTotales(receta.getItems().stream().mapToInt(ItemReceta::getCalorias).sum());
        return receta;
    }

    public static void actualizar(Receta recetaObjetivo, RecetaForm form, Function<Long, Ingrediente> buscardorIngredientes) {
        recetaObjetivo.setDescripcion(form.getDescripcion());
        recetaObjetivo.getItems().clear();
        form.getIngredientes().forEach(ingrediente -> {
            var itemReceta = new ItemReceta();
            itemReceta.setId(ingrediente.getId());
            itemReceta.setReceta(recetaObjetivo);
            itemReceta.setIngrediente(buscardorIngredientes.apply(ingrediente.getIdIngrediente()));
            itemReceta.setCantidadKg(BigDecimal.valueOf(ingrediente.getCantidadKg()));
            itemReceta.setCalorias(ingrediente.getCalorias());
            recetaObjetivo.getItems().add(itemReceta);
        });
        recetaObjetivo.setCaloriasTotales(recetaObjetivo.getItems().stream().mapToInt(ItemReceta::getCalorias).sum());
    }
}
