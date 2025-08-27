package fr.kainovaii.portfolio.repository;

import fr.kainovaii.portfolio.model.Page;
import fr.kainovaii.portfolio.model.PageBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<PageBlock, Long>
{
    Optional<PageBlock> findByPage(String title);
}