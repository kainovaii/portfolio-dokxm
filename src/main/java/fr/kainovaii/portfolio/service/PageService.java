package fr.kainovaii.portfolio.service;


import fr.kainovaii.portfolio.model.Page;
import fr.kainovaii.portfolio.model.Post;
import fr.kainovaii.portfolio.repository.PageRepository;
import fr.kainovaii.portfolio.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService
{
    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Optional<Page> findById(Long id) {
        return pageRepository.findById(id);
    }

    public Optional<Page> findBySlug(String title) {
        return pageRepository.findByTitle(title);
    }

    public Page save(Page page) {
        return pageRepository.save(page);
    }

    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }

    public long count() {
        return pageRepository.count();
    }
}