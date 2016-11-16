package uk.co.bluetangerine.delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.*;
import uk.co.bluetangerine.delegate.ParserUtils.DocumentHelper;
import uk.co.bluetangerine.delegate.dto.ProductDto;
import uk.co.bluetangerine.delegate.dto.ResultsDto;

/**
 * Created by tony on 15/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class ParseHTMLFromURLTest {
    @Mock
    Jsoup jsoupMock;

    @Mock
    DocumentHelper docHelperMock;

    ParseHTMLFromUrl underTest;

    @Before
    public void setUp() throws Exception {
        jsoupMock = Mockito.mock(Jsoup.class);
        docHelperMock = Mockito.mock(DocumentHelper.class);
        underTest = new ParseHTMLFromUrl(docHelperMock);
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
        Iterator iterator = Mockito.mock(Iterator.class);
        when(docHelperMock.getDocumentHelper(anyString())).thenReturn(doc);
        Elements elementsMock = mock(Elements.class);
        Element element1 = mock(Element.class);
        Element element2 = mock(Element.class);
        Elements productInfo = mock(Elements.class);
        Elements childProductInfo = mock(Elements.class);
        Elements pricePerUnits = mock(Elements.class);
        Element productInfo1 = mock(Element.class);
        Element productInfo2 = mock(Element.class);
        Element pricePerUnit1 = mock(Element.class);
        Element pricePerUnit2 = mock(Element.class);

        when(doc.getElementsByClass("productInner")).thenReturn(elementsMock);
        when(elementsMock.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(element1).thenReturn(element2);
        when(element1.getElementsByClass("productInfo")).thenReturn(productInfo);
        when(element1.getElementsByClass("pricePerUnit")).thenReturn(pricePerUnits);
        when(element2.getElementsByClass("productInfo")).thenReturn(productInfo);
        when(element2.getElementsByClass("pricePerUnit")).thenReturn(pricePerUnits);
        when(productInfo.get(0)).thenReturn(productInfo1).thenReturn(productInfo2);
        when(productInfo1.select("a")).thenReturn(childProductInfo).thenReturn(childProductInfo);
        when(productInfo2.select("a")).thenReturn(childProductInfo).thenReturn(childProductInfo);
        when(productInfo1.select("a[href]")).thenReturn(childProductInfo).thenReturn(childProductInfo);
        when(productInfo2.select("a[href]")).thenReturn(childProductInfo).thenReturn(childProductInfo);
        when(childProductInfo.first()).thenReturn(productInfo1).thenReturn(productInfo2);
        when(childProductInfo.text()).thenReturn("123.45").thenReturn("321.54");
        when(productInfo1.ownText()).thenReturn("ownText1");
        when(productInfo2.ownText()).thenReturn("ownText2");
        when(pricePerUnits.text()).thenReturn("123.45").thenReturn("321.54");
        when(productInfo1.attr("href")).thenReturn("SubPageURL");
        when(productInfo2.attr("href")).thenReturn("SubPageURL2");
        when(pricePerUnits.get(0)).thenReturn(pricePerUnit1).thenReturn(pricePerUnit2);

        //SubPage
        Elements productDataItemHeadersMock = mock(Elements.class);
        Elements productTextsMock = mock(Elements.class);
        Element productDataItemHeaderMock = mock(Element.class);
        Element productTextMock = mock(Element.class);
        when(doc.getElementsByClass("productDataItemHeader")).thenReturn(productDataItemHeadersMock);
        when(doc.getElementsByClass("productText")).thenReturn(productTextsMock);
        when(productDataItemHeadersMock.size()).thenReturn(3);
        when(productTextsMock.get(anyInt())).thenReturn(productTextMock);
        when(productDataItemHeadersMock.get(anyInt())).thenReturn(productDataItemHeaderMock);
        when(productDataItemHeaderMock.text()).thenReturn("Description").thenReturn("Nutrition").thenReturn("Size");
        when(productTextMock.text()).thenReturn("the description").thenReturn("Some nutrition info").thenReturn("53Pieces");

        String response = underTest.parse("someUrl");
        assertNotNull(response);
    }

    private void setUpMockParentPageDocumentStructure() {

    }

    private void setUpMockChildPageDocumentStructures() {

    }
}
