package uk.co.bluetangerine.delegate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import uk.co.bluetangerine.delegate.ParserUtils.DocumentHelper;
import uk.co.bluetangerine.delegate.ParserUtils.ParsingUtils;
import uk.co.bluetangerine.delegate.dto.ProductDto;
import uk.co.bluetangerine.delegate.dto.ResultsDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 15/11/2016.
 */


public class ParseHTMLFromUrl implements ParseHTML {
    DocumentHelper docHelper = new DocumentHelper();
    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String parse(String url) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        ResultsDto results = parseToDto(url);
        return gson.toJson(results);
    }


    /**
     * Parse the main parent page. Parses main parent page to
     * extract Title and unit price also keep a running total.
     * Calls the child page method to populate rest of dto fields.
     * @Param url Url for the parent page
     * @return populated ResultsDto object
     * @throws IOException
     */
    protected ResultsDto parseToDto(String url) throws IOException {
        //Create one resultsDto to contain all products found
        //and provide a running total
        ResultsDto resultsDto = new ResultsDto();
        List<ProductDto> responses = new ArrayList<ProductDto>();
        Document doc = docHelper.getDocumentHelper(url);
        BigDecimal runningTotal = new BigDecimal("0");
        ParsingUtils utils = new ParsingUtils();

        //Collate all the productInner elements that contains the product list
        Elements products = doc.getElementsByClass("productInner");

        //Iterate the product items to populate some of the Dto fields
        for (Element item : products) {
            ProductDto itemDto = new ProductDto();
            //ProductInner has only one productInfo so safe to assume 1 will exist and use it.
            Element productInfo = item.getElementsByClass("productInfo").get(0);
            itemDto.setTitle(StringEscapeUtils.unescapeHtml4(productInfo.select("a[href]").first().ownText()));
            itemDto.setUnitPrice(utils.extractPriceFromString(StringEscapeUtils.unescapeHtml4(item.getElementsByClass("pricePerUnit").text())));
            runningTotal = runningTotal.add(new BigDecimal(itemDto.getUnitPrice()));
            //Call the sub page function with the extracted URL and allow it to populate rest of Dto fields
            itemDto = parseSubPageUrl(productInfo.select("a").first().attr("href"), itemDto);
            responses.add(itemDto);
        }

        resultsDto.setProducts(responses);
        resultsDto.setTotal(runningTotal);
        return resultsDto;
    }

    /**
     * Parse the child page. Parses child page to
     * extract description and size and populate
     * remaining fields on the Dto.
     * Access modifier set to protected to allow unit testing
     * @Param url Url for the child page
     * @Param dto being populated
     * @Return new copy of product DTO with additional fields populated
     */
    protected ProductDto parseSubPageUrl(String childUrl, ProductDto item) throws IOException {
        //Avoid bad practice of modifying parameter references!
        ProductDto result = item;
        Document doc = docHelper.getDocumentHelper(childUrl);

        //For each productDataItemHeader ...
        Elements productDataItemHeaders = doc.getElementsByClass("productDataItemHeader");
        //... there is a corresponding product text
        Elements productTexts = doc.getElementsByClass("productText");

        //Seek out Description and Size to populate Dto using the Text of productDataItemHeader.
        //The corresponding element in productText holds the value
        for (int i = 0; i < productDataItemHeaders.size(); i++) {
            if ( productDataItemHeaders.get(i).text().equals("Description")) {
                result.setDescription(StringEscapeUtils.unescapeHtml4(productTexts.get(i).text()));
            }
            else if (productDataItemHeaders.get(i).text().equals("Size")) {
                result.setSize(StringEscapeUtils.unescapeHtml4(productTexts.get(i).text()));
            }
        }

        return result;
    }
}
