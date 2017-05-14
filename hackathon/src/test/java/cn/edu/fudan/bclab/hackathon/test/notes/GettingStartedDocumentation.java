package cn.edu.fudan.bclab.hackathon.test.notes;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GettingStartedDocumentation {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{method-name}/{step}/"))
				.build();
	}

	@Test
	public void index() throws Exception {
		this.mockMvc.perform(get("/api").accept(MediaTypes.HAL_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("_links.news", is(notNullValue())))
			.andExpect(jsonPath("_links.sites", is(notNullValue())))
			.andExpect(jsonPath("_links.transactions", is(notNullValue())));

	}

	@Test
	public void creatingASite() throws JsonProcessingException, Exception {
		String siteLocation = createSite();
		MvcResult site = getSite(siteLocation);

		String newsLocation = createNews(siteLocation);
		getNews(newsLocation);
	}
	
	

	String createSite() throws Exception {
		Map<String, String> site = new HashMap<String, String>();
		site.put("name", "Name of the site");
		site.put("description", "Description of the site");
		site.put("callbackUrl", "Callback URL for INCHAIN annoncement");
		

		String siteLocation = this.mockMvc
				.perform(
						post("/api/sites").contentType(MediaTypes.HAL_JSON).content(
								objectMapper.writeValueAsString(site)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", notNullValue()))
				.andReturn().getResponse().getHeader("Location");
		return siteLocation;
	}

	MvcResult getSite(String siteLocation) throws Exception {
		return this.mockMvc.perform(get(siteLocation))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name", is(notNullValue())))
				.andExpect(jsonPath("description", is(notNullValue())))
				.andExpect(jsonPath("callbackUrl", is(notNullValue())))
				.andExpect(jsonPath("status", is(notNullValue())))
				.andExpect(jsonPath("_links.allNews", is(notNullValue())))
				.andReturn();
	}

	String createNews(String siteLocation) throws Exception, JsonProcessingException {
		Map<String, String> news = new HashMap<String, String>();
		news.put("title", "Title of the news");
		news.put("content", "Content of the news");
		news.put("author", "Author of the news");
		news.put("date", "1489300401352");
		news.put("site", siteLocation);

		String newsLocation = this.mockMvc
				.perform(
						post("/api/news").contentType(MediaTypes.HAL_JSON).content(
								objectMapper.writeValueAsString(news)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", notNullValue()))
				.andReturn().getResponse().getHeader("Location");
		return newsLocation;
	}

	void getNews(String newsLocation) throws Exception {
		this.mockMvc.perform(get(newsLocation)).andExpect(status().isOk())
			.andExpect(jsonPath("title", is(notNullValue())))
			.andExpect(jsonPath("content", is(notNullValue())))
			.andExpect(jsonPath("author", is(notNullValue())))
			.andExpect(jsonPath("date", is(notNullValue())))
			.andExpect(jsonPath("digest", is(notNullValue())))
			.andExpect(jsonPath("status", is(notNullValue())))
			.andExpect(jsonPath("_links.parent", is(notNullValue())))
			.andExpect(jsonPath("_links.site", is(notNullValue())))			
			.andExpect(jsonPath("_links.derivatives", is(notNullValue())));
	}


	private String getLink(MvcResult result, String rel)
			throws UnsupportedEncodingException {
		return JsonPath.parse(result.getResponse().getContentAsString()).read(
				"_links." + rel + ".href");
	}
}
