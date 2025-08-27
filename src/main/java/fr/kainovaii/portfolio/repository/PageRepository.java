package fr.kainovaii.portfolio.repository;

import fr.kainovaii.portfolio.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long>
{
    Optional<Page> findByTitle(String title);
}