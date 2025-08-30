package fr.kainovaii.portfolio.repository;

import fr.kainovaii.portfolio.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long>
{
}