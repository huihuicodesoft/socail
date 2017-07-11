package cn.com.wh.ring.app.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * Created by Hui on 2017/7/10.
 */
public class MessageResourceHelper {
    private static final Logger logger = LoggerFactory.getLogger(MessageResourceHelper.class);
    private static MessageResourceHelper ourInstance = new MessageResourceHelper();
    private ResourceBundleMessageSource messageSource;

    public static MessageResourceHelper getInstance() {
        return ourInstance;
    }

    private MessageResourceHelper() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/message");
        messageSource.setDefaultEncoding("UTF-8");
    }

    String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String result = "";
        try {
            if (messageSource == null) {
                throw new Exception("MessageSource init fail");
            } else {
                result = messageSource.getMessage(code, args, defaultMessage, locale);
                return result;
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        return result;
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        String result = "";
        try {
            if (messageSource == null) {
                throw new Exception("MessageSource init fail");
            } else {
                result = messageSource.getMessage(code, args, locale);
                return result;
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        return result;
    }

    public String getMessage(String code, Locale locale) {
       return getMessage(code, null, locale);
    }
}
