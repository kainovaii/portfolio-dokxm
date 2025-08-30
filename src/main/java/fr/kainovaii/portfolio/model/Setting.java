package fr.kainovaii.portfolio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "setting")
public class Setting
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteName;

    private String homePage;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getHomePage() { return homePage; }
    public void setHomePage(String homePage) { this.homePage = homePage; }
}
