package com.example.demo.Controller;

import com.example.demo.Model.Producto;
import com.example.demo.Service.ProductoService;
import com.example.demo.Model.AjusteInventarioRequest;
import com.example.demo.Service.ProductoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController // con este controlador se manejan las peticiones REST
@RequestMapping("/api/productos") //aqui se define la ruta para la base de datos
public class ProductoController {
    
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }
    // ENDPOINTS DE LA API REST

    // POST 
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // POST: Ajuste del inventario
    @PostMapping("/{id}/ajustar")
    public ResponseEntity<Producto> ajustarInventario(@PathVariable Long id, @RequestBody AjusteInventarioRequest request) {
        Producto productoAjustado = productoService.ajustarInventario(id, request.getCantidad(), request.getRazon());
        return ResponseEntity.ok(productoAjustado);
    }

    // GET: obtener productos por el ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // GET: Se enlista los productos con paginación
    @GetMapping
    public ResponseEntity<Page<Producto>> listarProductos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Producto> paginaProductos = productoService.listarConPaginacion(pageable);
        return ResponseEntity.ok(paginaProductos);
    }
    
    // PUT: Aquí se actualiza un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Producto actualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE: Para eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PATCH: ACTIVAR/DESACTIVAR
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Producto> activarDesactivar(@PathVariable Long id, @RequestParam("activo") Boolean activo){

        Producto productoModificado = productoService.activarDesactivar(id, activo);
        return ResponseEntity.ok(productoModificado);
    }
    
}
