package fr.kainovaii.portfolio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pages")
public class PageBlock
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String page;

    private String type;

    @Lob
    private String htmlContent;

    public PageBlock() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getHtmlContent() { return htmlContent; }
    public void setHtmlContent(String htmlContent) { this.htmlContent = htmlContent; }
}
