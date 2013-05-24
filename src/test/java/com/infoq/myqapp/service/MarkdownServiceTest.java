package com.infoq.myqapp.service;

import com.infoq.myqapp.domain.GitHubMarkdown;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring/spring-servlet.xml")
public class MarkdownServiceTest {

    @Autowired
    private MarkdownService markdownService;

    @Test
    public void testGeneration() {
        String markdown = "*Allo*";

        String html = markdownService.generateHtml(new GitHubMarkdown(markdown, "markdown", null));

        assertThat(html).isEqualTo("<p><em>Allo</em></p>");
    }
}