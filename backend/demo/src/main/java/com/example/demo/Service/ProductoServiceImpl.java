package com.example.demo.Service;

import com.example.demo.Model.Producto;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository; // esta linea de codigo es el repositorio 
                                                         // para acceder a las base de datos
    
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto guardarProducto(Producto producto){
        // aqui el Spring ya esta validando los  "@NotBlank y @DecimalMin"
        // y  solo procede con la operación de guardar en la BD
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id){
        // orElseThrow() se utilizará en caso de que el producto no exista
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    @Override
    public Producto actualizarProducto(Long id, Producto detallesProducto){
        // aqui se verifica si el producto existe
        Producto productoExistente = obtenerPorId(id);
         
        // se aplica la actualización en los detalles del producto
        productoExistente.setNombre(detallesProducto.getNombre());
        productoExistente.setMarca(detallesProducto.getMarca());
        productoExistente.setPrecio(detallesProducto.getPrecio());
        productoExistente.setExistencias(detallesProducto.getExistencias());
        productoExistente.setActivo(detallesProducto.getActivo());

        // se guarda la actualización aqui y en la BD
        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }

    @Override
    public Producto buscarPorNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public Page<Producto> listarConPaginacion(Pageable pageable){
        return productoRepository.findAll(pageable);
    }

    @Override
    public Producto activarDesactivar(Long id, Boolean activo){
        Producto productoExistente = obtenerPorId(id);
        productoExistente.setActivo(activo);
        return productoRepository.save(productoExistente);
    }

    @Override
    public Producto ajustarInventario(Long id, Integer cantidad, String razon){
        Producto productoExistente = obtenerPorId(id);

        if(razon == null || razon.trim().isEmpty()){
            throw new RuntimeException("La razón del inventario es obligatoria.");
        }

    // Es la parte del ajuste 
    Integer nuevasExistencias = productoExistente.getExistencias() + cantidad;
    // Validar que la existencia de un producto no sea <0
    if(nuevasExistencias < 0){
        throw new RuntimeException("Sin producto existente.");
    }
    productoExistente.setExistencias(nuevasExistencias);

    return productoRepository.save(productoExistente);
    }
}
