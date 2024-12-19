package br.edu.ifg.numbers.produtoapi.repository;

import br.edu.ifg.numbers.produtoapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
}