package com.chrisbaileydeveloper.bookshelf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.Matchers.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.chrisbaileydeveloper.bookshelf.Application;
import com.chrisbaileydeveloper.bookshelf.config.Constants;
import com.chrisbaileydeveloper.bookshelf.repository.BookRepository;
import com.chrisbaileydeveloper.bookshelf.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class BookControllerTest {
	private MockMvc mockMvc;

	@Inject
	private BookRepository bookRepository;

	@Before
	public void setup() {
		BookService bookService = new BookService();
		ReflectionTestUtils.setField(bookService, "bookRepository",	bookRepository);

		BookController bookController = new BookController();
		ReflectionTestUtils.setField(bookController, "bookService", bookService);

		this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void testList() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("books/list"))
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("books"))
			.andExpect(model().attribute("books", hasItem(
                        allOf(
                        	hasProperty("id", is("1")),
                            hasProperty("name", is("Effective Java")),
                            hasProperty("publisher", is("Addison-Wesley"))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                            hasProperty("id", is("2")),
                            hasProperty("name", is("Design Patterns:  Elements of Reusable Object-Oriented Software")),
                            hasProperty("publisher", is("Addison-Wesley Professional"))
                        )
                )));
	}

	@Test
	public void testShow() throws Exception {
		mockMvc.perform(get("/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("books/show"))
			.andExpect(model().size(1))
			.andExpect(model().attribute("book", 
					allOf(
						hasProperty("id", is("1")),
						hasProperty("name", is("Effective Java")),
                        hasProperty("publisher", is("Addison-Wesley"))
					)
			));
	}

	@Test
	public void testUpdateForm() throws Exception {
		mockMvc.perform(get("/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("book"))
			.andExpect(model().attribute("book", 
					allOf(
						hasProperty("id", is("1")),
						hasProperty("name", is("Effective Java")),
                        hasProperty("publisher", is("Addison-Wesley"))
					)
			));
	}
	
	@Test
	public void testCreateForm() throws Exception {
		mockMvc.perform(get("/create"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("books/create"))
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("book"))
			.andExpect(model().attribute("book", 
					allOf(
						hasProperty("name", equalTo(null)),
                        hasProperty("publisher", equalTo(null))
					)
			));;
	}
	
	@Test
	public void testDownloadPhoto() throws Exception {
		mockMvc.perform(get("/photo/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
				.andReturn();
	}
}
