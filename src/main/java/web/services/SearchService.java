package web.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Service("SearchService")
@Transactional
public class SearchService {

    public String titleToTitleSearch(String title){
        String titleSearch = title;
        titleSearch = titleSearch.toLowerCase(); //Making it lowercase
        titleSearch = Normalizer.normalize(titleSearch, Normalizer.Form.NFKD);                       //
        Pattern pattern = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+"); //Replacing accented letters with latin analogs
        titleSearch = pattern.matcher(titleSearch).replaceAll("");                      //
        titleSearch = titleSearch.replaceAll("[^\\x00-\\x7F]", ""); // Removing letter, that are not in latin alphabet
        titleSearch = titleSearch.replaceAll("[^\\p{L}\\p{Z}]",""); // Removing special characters
        titleSearch = titleSearch.replaceAll("\\s+",""); // Removing whitespaces
        return titleSearch;
    }

}
