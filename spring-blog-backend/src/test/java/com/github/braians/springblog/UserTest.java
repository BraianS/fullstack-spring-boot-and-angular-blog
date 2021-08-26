package com.github.braians.springblog;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import com.github.braians.springblog.model.Post;
import com.github.braians.springblog.model.Tag;
import com.github.braians.springblog.payload.PostRequest;
import com.github.braians.springblog.payload.TitleRequest;
import com.github.braians.springblog.repository.CategoryRepository;
import com.github.braians.springblog.repository.PostRepository;
import com.github.braians.springblog.repository.TagRepository;
import com.github.braians.springblog.service.CategoryService;
import com.github.braians.springblog.service.PostService;
import com.github.braians.springblog.service.TagService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserTest {

    @InjectMocks
    private PostService postService;

    @InjectMocks
    private TagService tagService;

    @InjectMocks
    private CategoryService categoryService;

    /*
     * @org.junit.Before public void init(){ MockitoAnnotations.openMocks(this); }
     */

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostRepository postRepository;

    public UserTest() {
    }

    @Test
    public void PersistPost() {
        PostRequest post = new PostRequest();

        PersistTags();
        PersistCategories();

        post.setTitle("Novo titulo");
        post.setContent("conteúdo excelente");
        post.setCategories(List.of("Terror", "Acao").stream().collect(Collectors.toSet()));
        post.setTags(List.of("java", "codigo").stream().collect(Collectors.toSet()));

        // Post postSaved = postService.save(post);

        Post post2 = new Post();
        post2.setId(1L);
        post2.setTitle("Novo titulo");

        when(postRepository.getById(1l)).thenReturn(post2);

        // Assertions.assertThat(postSaved.getTitle()).isEqualTo("Novo titulo");
        // Assertions.assertThat(postSaved.getCategories()).isEqualTo(2);
    }

    @Test
    public void PersistTags() {
        TitleRequest java = new TitleRequest();
        java.setTitle("java");

        TitleRequest codigo = new TitleRequest();
        codigo.setTitle("codigo");

        tagService.save(java);
        // tagService.save(codigo);

        // Mockito.verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    public void PersistCategories() {
        TitleRequest terror = new TitleRequest();
        terror.setTitle("Terror");

        TitleRequest acao = new TitleRequest();
        acao.setTitle("Acao");

        categoryService.save(terror);
        // categoryService.save(acao);
    }

    @Test
    public void testSubString() {
        String texto = "Hola mundo";
        String sub = texto.substring(0, 2);
        System.out.println(sub);
    }

    @Test
    public void save100Tag() {
        for (int index = 0; index < 100; index++) {
            Tag tag = new Tag();
            tag.setTitle("JAVA " + index);

            tagRepository.save(tag);
        }
    }

}
