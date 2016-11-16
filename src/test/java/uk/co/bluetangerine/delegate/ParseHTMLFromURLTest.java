package uk.co.bluetangerine.delegate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import uk.co.bluetangerine.delegate.ParserUtils.DocumentHelper;
import uk.co.bluetangerine.delegate.dto.ProductDto;
import uk.co.bluetangerine.delegate.dto.ResultsDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tony on 15/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class ParseHTMLFromURLTest {
    @Mock
    Jsoup jsoupMock;

    @Mock
    DocumentHelper docHelperMock;

    ParseHTMLFromUrl underTest = new ParseHTMLFromUrl();

    @Before
    public void setUp() throws Exception {
        jsoupMock = Mockito.mock(Jsoup.class);
        docHelperMock = Mockito.mock(DocumentHelper.class);
    }

    @Test
    public void givenHtmlDocumentThenReturnJson() throws Exception {
        ResultsDto results = new ResultsDto();
        ProductDto product1 = new ProductDto();
        ProductDto product2 = new ProductDto();
        product1.setUnitPrice("123.45");
        product1.setSize("35");
        product1.setDescription("Description");
        product1.setTitle("Title1");
        product2.setUnitPrice("321.45");
        product2.setSize("352");
        product2.setDescription("Description2");
        product2.setTitle("Title2");
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        productDtos.add(product1);
        productDtos.add(product2);
        results.setProducts(productDtos);
        results.setTotal(new BigDecimal("444.9"));


        Document doc = mock(Document.class);
        when(docHelperMock.getDocumentHelper("someUrl")).thenReturn(doc);

        //given(underTest.parseToDto("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html")).willReturn(results);
        String response = underTest.parse("someUrl");
        assertNotNull(response);
    }
}