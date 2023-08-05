package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {


    @Override
    public Number parse(String text, Locale locale) throws ParseException {

        /**
         * "1,000" 처럼 숫자 중간의 쉼표를 적용하려면 자바가 기본으로 제공하는 NumberFormat 객체를 사용하면 된다.
         * 이 객체는 Locale 정보를 활용해서 나라별로 다른 숫자 포맷을 만들어준다.
         **/

        log.info("text={}, locale={}", text, locale);
        // "1,000" -> 1000
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object);
    }



}
