package com.senac.gerenciamentodecompras.entity;

import jakarta.persistence.*;
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")

}
