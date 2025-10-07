package com.example.demo.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*; 

@Entity // ESTA PARTE ES UNA TABLA DE LA BASE DE DATOS
@Table(name = "productos") // nombre de tabla
@Data // Genera get, set, toString, equals
@NoArgsConstructor // este se creo para las instancias de la entidad cuando recupera datos de la bd.
@AllArgsConstructor // aqui si genera los constructores con argumentos

public class Producto {

// id (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AQUI SE AUTOINCREMENTA EN MAYSQL CON AYUDA DEL ID
    private Long id;

// nombre (unico, obligatorio)
    @Column(unique = true, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

// marca, categoria
    private String marca;
    private String categoria;

// precio (> 0)
    @DecimalMin(value = "0.01", message = "El precio debe se mayor a cero ")
    private Double precio;

// existencias (>= 0)
    @Min(value = 0, message = "La existencia no puede ser negativa vv")
    private Integer existencias;

// activo (booleano)
    private Boolean activo = true;
    
}
