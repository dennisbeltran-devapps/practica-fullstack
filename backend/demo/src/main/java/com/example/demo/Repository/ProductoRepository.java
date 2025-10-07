package com.example.demo.Repository;

import com.example.demo.Model.Producto; // Importamos la Entidad
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//la siguiente linea despues de extends proporciona l¿toda la funcionalidad del CRUD de forma autoimatica.
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    Producto findByNombre(String nombre);
    
}
