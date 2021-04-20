package es.code.urjc.periftech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.code.urjc.periftech.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
