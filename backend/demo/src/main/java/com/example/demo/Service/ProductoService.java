package com.example.demo.Service;

import com.example.demo.Model.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

public interface ProductoService {
    
    Producto guardarProducto(Producto producto);
    List<Producto> obtenerTodos();
    Producto obtenerPorId(Long id);
    Producto actualizarProducto(Long id, Producto producto);
    void eliminarProducto(Long id);

// Aqui se busca por nombre el producto
    Producto buscarPorNombre(String nombre);

// en esta parte se enlistara con paginación, se activaran y desactivaran productos, 
// y se hará el ajuste de inventario.
    Page<Producto> listarConPaginacion(Pageable pageable);
    Producto ajustarInventario(Long id, Integer cantidad, String razon);
    Producto activarDesactivar(Long id, Boolean activo);
    
} 
